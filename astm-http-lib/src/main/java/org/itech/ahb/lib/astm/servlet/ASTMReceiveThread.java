package org.itech.ahb.lib.astm.servlet;

import java.io.IOException;
import java.net.Socket;
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

  @Override
  public void run() {
    log.trace("thread started to receive ASTM message");
    try {
      try {
        ASTMMessage message = communicator.receiveProtocol();
        HandleStatus status = astmHandlerMarshaller.handle(message);
        log.debug("astm HandleStatus is: " + status);
        if (status != HandleStatus.SUCCESS) {
          log.error("message was not handled successfully");
        }
      } catch (IllegalStateException | FrameParsingException | ASTMCommunicationException e) {
        log.error("an error occurred understanding or handling what was received from the astm sender", e);
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
}
