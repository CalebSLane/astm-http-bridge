package org.itech.ahb.lib.astm.servlet;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.lib.common.ASTMMessage;
import org.itech.ahb.lib.common.HandleStatus;
import org.itech.ahb.lib.common.exception.ASTMCommunicationException;
import org.itech.ahb.lib.common.exception.FrameParsingException;

@Slf4j
public class ASTMReceiveThread extends Thread {

  private final Socket socket;
  private final Communicator communicator;
  private ASTMHandlerMarshaller astmHandlerMarshaller;
  private boolean lineWasContentious;

  public ASTMReceiveThread(Communicator communicator, Socket socket, ASTMHandlerMarshaller astmHandlerMarshaller) {
    this.communicator = communicator;
    this.socket = socket;
    this.astmHandlerMarshaller = astmHandlerMarshaller;
  }

  public ASTMReceiveThread(Communicator communicator, ASTMHandlerMarshaller astmHandlerMarshaller) {
    this.communicator = communicator;
    this.socket = null;
    this.astmHandlerMarshaller = astmHandlerMarshaller;
  }

  public ASTMReceiveThread(
    Communicator communicator,
    Socket socket,
    ASTMHandlerMarshaller astmHandlerMarshaller,
    boolean lineWasContentious
  ) {
    this.communicator = communicator;
    this.socket = socket;
    this.astmHandlerMarshaller = astmHandlerMarshaller;
    this.lineWasContentious = lineWasContentious;
  }

  @Override
  public void run() {
    log.trace("thread started to receive ASTM message");
    try {
      ASTMMessage message;
      try {
        message = communicator.receiveProtocol(lineWasContentious);
      } catch (IllegalStateException | FrameParsingException | ASTMCommunicationException e) {
        log.error("an error occurred understanding what was received from the astm sender", e);
        return;
      } catch (InterruptedException e) {
        log.error("the thread was interrupted during receive protocol", e);
        return;
      } catch (SocketTimeoutException e) {
        log.error("there was a timeout in the receive protocol at the socket level, abandoning message", e);
        return;
      }
      ASTMMarshallerResponse response = astmHandlerMarshaller.handle(message);
      if (response.getResponses() == null || response.getResponses().size() == 0) {
        log.error("message was unhandled");
      } else {
        for (ASTMHandlerResponse handlerResponse : response.getResponses()) {
          if (handlerResponse.getStatus() != HandleStatus.SUCCESS) {
            log.error("message was not handled successfully by: " + handlerResponse.getHandler().getName());
          } else {
            log.debug("message was handled successfully by: " + handlerResponse.getHandler().getName());
          }
        }
      }
    } catch (IOException e) {
      log.error("error occurred communicating with astm sender", e);
    } finally {
      if (socket != null && !socket.isClosed()) {
        try {
          socket.close();
          log.debug("successfully closed socket with astm sender");
        } catch (IOException e) {
          log.error("error occurred closing socket with astm sender", e);
        }
      }
    }
  }

  public boolean didReceiveEstablishmentSucceed() {
    return communicator.didReceiveEstablishmentSucceed();
  }
}
