package org.itech.ahb.lib.astm.servlet;

import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.lib.astm.communication.GeneralASTMCommunicator;
import org.itech.ahb.lib.astm.handling.ASTMHandlerMarshaller;
import org.itech.ahb.lib.astm.handling.ASTMReceiveThread;
import org.itech.ahb.lib.astm.interpretation.ASTMInterpreterFactory;

@Slf4j
public class ASTMServlet {

  public enum ASTMVersion {
    LIS01_A,
    E1381_95,
    NON_COMPLIANT
  }

  private final ASTMHandlerMarshaller astmMessageMarshaller;
  private final ASTMInterpreterFactory astmInterpreterFactory;
  private final int listenPort;
  private final ASTMVersion astmVersion;

  public ASTMServlet(
    ASTMHandlerMarshaller astmMessageMarshaller,
    ASTMInterpreterFactory astmInterpreterFactory,
    int listenPort,
    ASTMVersion astmVersion
  ) {
    this.astmMessageMarshaller = astmMessageMarshaller;
    this.astmInterpreterFactory = astmInterpreterFactory;
    this.listenPort = listenPort;
    this.astmVersion = astmVersion;
  }

  public void listen() {
    try (ServerSocket serverSocket = new ServerSocket(listenPort)) {
      log.info("Server is listening on port " + listenPort + " for ASTM protocol: " + astmVersion + " messages");
      // Communication Endpoint for the client and server.
      while (true) {
        // Waiting for socket connection
        Socket s = serverSocket.accept();
        new ASTMReceiveThread(
          new GeneralASTMCommunicator(astmInterpreterFactory, s, astmVersion),
          s,
          astmMessageMarshaller
        ).start();
      }
    } catch (Exception e) {
      log.error("an exception caused the astm server to shut down", e);
    }
  }
}
