package org.itech.ahb.lib.http.handling;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itech.ahb.lib.astm.communication.Communicator;
import org.itech.ahb.lib.astm.communication.Communicator.SendResult;
import org.itech.ahb.lib.astm.communication.GeneralASTMCommunicator;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.astm.concept.DefaultASTMMessage;
import org.itech.ahb.lib.astm.exception.ASTMCommunicationException;
import org.itech.ahb.lib.astm.exception.FrameParsingException;
import org.itech.ahb.lib.astm.handling.ASTMHandlerMarshaller;
import org.itech.ahb.lib.astm.handling.ASTMReceiveThread;
import org.itech.ahb.lib.astm.interpretation.ASTMInterpreterFactory;
import org.itech.ahb.lib.astm.servlet.ASTMServlet.ASTMVersion;
import org.itech.ahb.lib.common.handling.HandleStatus;

@Slf4j
public class DefaultForwardingHTTPToASTMHandler implements HTTPHandler {

  private final String defaultForwardingAddress;
  private final int defaultForwardingPort;
  private final ASTMVersion defaultForwardingProtocol;
  private final ASTMInterpreterFactory interpreterFactory;
  private final ASTMHandlerMarshaller astmHandlerMarshaller; // this is necessary in case of line contention

  private static final int MAX_FORWARD_RETRY_ATTEMPTS = 3; // this is not officially part of the astm standard
  private static final int SEND_ATTEMPTS_WAIT = 10; // in seconds the amount of time to wait before trying to submit again
  private static final int LINE_CONTENTION_REATTEMPT_TIMEOUT = 20; // in seconds

  public DefaultForwardingHTTPToASTMHandler(
    String forwardingAddress,
    int forwardingPort,
    ASTMHandlerMarshaller astmHandlerMarshaller,
    ASTMInterpreterFactory interpreterFactory
  ) {
    this.defaultForwardingAddress = forwardingAddress;
    this.defaultForwardingPort = forwardingPort;
    this.defaultForwardingProtocol = ASTMVersion.LIS01_A;
    this.interpreterFactory = interpreterFactory;
    this.astmHandlerMarshaller = astmHandlerMarshaller;
  }

  @Override
  public HTTPHandlerResponse handle(ASTMMessage message, Set<HTTPHandlerInfo> handlerInfos)
    throws FrameParsingException {
    return handle(message, handlerInfos, 0);
  }

  private HTTPHandlerResponse handle(ASTMMessage message, Set<HTTPHandlerInfo> handlerInfos, int retryAttempt)
    throws FrameParsingException {
    Socket socket = null;
    Communicator communicator = null;
    String forwardingAddress = this.defaultForwardingAddress;
    int forwardingPort = this.defaultForwardingPort;
    ASTMVersion forwardingProtocol = this.defaultForwardingProtocol;
    for (HTTPHandlerInfo handlerInfo : handlerInfos) {
      if (handlerInfo instanceof HTTPForwardingHandlerInfo) {
        HTTPForwardingHandlerInfo httpForwardingHandlerInfo = (HTTPForwardingHandlerInfo) handlerInfo;
        forwardingAddress = StringUtils.isBlank(httpForwardingHandlerInfo.getForwardAddress())
          ? forwardingAddress
          : httpForwardingHandlerInfo.getForwardAddress();
        forwardingPort = httpForwardingHandlerInfo.getForwardPort() <= 0
          ? forwardingPort
          : httpForwardingHandlerInfo.getForwardPort();
        forwardingProtocol = httpForwardingHandlerInfo.getForwardAstmVersion() == null
          ? forwardingProtocol
          : httpForwardingHandlerInfo.getForwardAstmVersion();
      }
    }
    try {
      if (retryAttempt > 0) {
        log.debug("waiting to reattempt sending to astm server...");
        Thread.sleep(SEND_ATTEMPTS_WAIT * 1000);
        log.debug("reattempting forward to astm server...");
      } else if (retryAttempt > MAX_FORWARD_RETRY_ATTEMPTS) {
        log.error("reached max number of retries while attempting to forward http over astm");
        return new HTTPHandlerResponse("", HandleStatus.FAIL_TOO_MANY_ATTEMPTS, false, this);
      }
      try {
        log.debug("connecting to forward to astm server at " + forwardingAddress + ":" + forwardingPort);
        socket = new Socket(forwardingAddress, forwardingPort);
        log.debug("connected to astm server at " + forwardingAddress + ":" + forwardingPort);
        communicator = new GeneralASTMCommunicator(interpreterFactory, socket, forwardingProtocol);
        log.debug(
          "successfully created communicator " +
          communicator.getID() +
          " for astm server at " +
          forwardingAddress +
          ":" +
          forwardingPort
        );
        SendResult result = communicator.sendProtocol(message);

        if (result.isLineContention()) {
          log.warn(
            "line was contested by the remote server, defaulting to receive information from " + forwardingAddress
          );
          return handleLineContention(communicator, socket, message);
        } else if (result.isRejected()) {
          return handle(message, handlerInfos, ++retryAttempt);
        } else {
          return new HTTPHandlerResponse("", HandleStatus.SUCCESS, false, this);
        }
      } catch (IOException | ASTMCommunicationException e) {
        log.error("error occurred communicating with astm server at " + forwardingAddress + ":" + forwardingPort, e);
        return handle(message, handlerInfos, ++retryAttempt);
      } finally {
        if (socket != null && !socket.isClosed()) {
          try {
            socket.close();
            log.debug("successfully closed socket with astm server at " + forwardingAddress + ":" + forwardingPort);
          } catch (IOException e) {
            log.error(
              "error occurred closing socket with astm server at " + forwardingAddress + ":" + forwardingPort,
              e
            );
          }
        }
      }
    } catch (InterruptedException e) {
      log.error("thread was interrupted while handling http astm message", e);
      return new HTTPHandlerResponse("", HandleStatus.INTERRUPTED, false, this);
    }
  }

  private HTTPHandlerResponse handleLineContention(Communicator communicator, Socket socket, ASTMMessage message)
    throws ASTMCommunicationException, InterruptedException {
    // the communicator must remain open to receive the line contention. The thread will close the socket
    ASTMReceiveThread receiveThread = new ASTMReceiveThread(communicator, socket, astmHandlerMarshaller, true);
    receiveThread.run();
    log.debug("waiting after line contention to see if sender has a message that needs to be received...");
    TimeUnit.SECONDS.sleep(LINE_CONTENTION_REATTEMPT_TIMEOUT);
    if (receiveThread.didReceiveEstablishmentSucceed()) {
      log.debug("received an establishment after the line was in contention before the timeout");
    } else {
      log.error("a timeout occured waiting for the sender to reattempt establishment after the line was contested.");
      receiveThread.interrupt();
      throw new ASTMCommunicationException(
        "line contention occurred but receiver didn't receive an establishment character"
      );
    }

    if (message.getMessageLength() == 0) {
      log.info("since original message request was empty, it is assumed this was a ping to trigger an action");
      return new HTTPHandlerResponse("", HandleStatus.SUCCESS, false, this);
    } else {
      return new HTTPHandlerResponse("", HandleStatus.FAIL_LINE_CONTESTED, false, this);
    }
  }

  @Override
  public boolean matches(ASTMMessage message) {
    return message instanceof DefaultASTMMessage;
  }

  @Override
  public String getName() {
    return "Default HTTP to ASTM Handler";
  }
}
