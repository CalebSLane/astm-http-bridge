package org.itech.ahb.lib.astm.servlet;

import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.lib.astm.communication.GeneralASTMCommunicator;
import org.itech.ahb.lib.astm.handling.ASTMHandlerService;
import org.itech.ahb.lib.astm.handling.ASTMReceiveThread;
import org.itech.ahb.lib.astm.interpretation.ASTMInterpreterFactory;

/**
 * This class represents a servlet that listens for ASTM messages via an ASTM transmission protocol
 * on a specified port and handles them using the provided handler service.
 */
@Slf4j
public class ASTMServlet {

  /**
   * Enum representing the ASTM version.
   */
  public enum ASTMVersion {
    LIS01_A,
    E1381_95,
    /**
     * Non-compliant ASTM version. A non-standard version of the ASTM transmission protocol
     * where the message is communicaed character by character without the normal ASTM frames,
     * control characters, checksums, etc.
     */
    NON_COMPLIANT
  }

  private final ASTMHandlerService astmMessageMarshaller;
  private final ASTMInterpreterFactory astmInterpreterFactory;
  private final int listenPort;
  private final ASTMVersion astmVersion;

  /**
   * Constructs a new ASTMServlet with the specified handler service, interpreter factory, listen port, and ASTM version.
   *
   * @param astmMessageMarshaller the handler service to use for processing messages.
   * @param astmInterpreterFactory the interpreter factory to use for interpreting messages.
   * @param listenPort the port to listen on for ASTM messages.
   * @param astmVersion the ASTM version to use for communication.
   */
  public ASTMServlet(
    ASTMHandlerService astmMessageMarshaller,
    ASTMInterpreterFactory astmInterpreterFactory,
    int listenPort,
    ASTMVersion astmVersion
  ) {
    this.astmMessageMarshaller = astmMessageMarshaller;
    this.astmInterpreterFactory = astmInterpreterFactory;
    this.listenPort = listenPort;
    this.astmVersion = astmVersion;
  }

  /**
   * Starts the servlet to listen for ASTM messages on the specified port.
   *
   * Will spawn a new thread for every incoming connection.
   */
  public void listen() {
    try (ServerSocket serverSocket = new ServerSocket(listenPort)) {
      log.info(
        "Server is listening on port " + listenPort + " for ASTM transmission protocol: " + astmVersion + " messages"
      );
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
