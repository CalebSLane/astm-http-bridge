package org.itech.ahb.lib.astm.servlet;

import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.lib.common.ASTMInterpreterFactory;

@Slf4j
public class ASTMServlet {

  private final ASTMHandlerMarshaller astmMessageMarshaller;
  private final ASTMInterpreterFactory astmInterpreterFactory;
  private final int listenPort;

  public ASTMServlet(
    ASTMHandlerMarshaller astmMessageMarshaller,
    ASTMInterpreterFactory astmInterpreterFactory,
    int listenPort
  ) {
    this.astmMessageMarshaller = astmMessageMarshaller;
    this.astmInterpreterFactory = astmInterpreterFactory;
    this.listenPort = listenPort;
  }

  public void listen() {
    try (ServerSocket serverSocket = new ServerSocket(listenPort)) {
      log.info("Server is listening on port " + listenPort + " for ASTM protocol messages");
      // Communication Endpoint for the client and server.
      while (true) {
        // Waiting for socket connection
        Socket s = serverSocket.accept();
        new ASTMReceiveThread(s, astmInterpreterFactory, astmMessageMarshaller).start();
      }
    } catch (Exception e) {}
  }
}
