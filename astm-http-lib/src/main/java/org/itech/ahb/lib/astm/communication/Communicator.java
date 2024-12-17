package org.itech.ahb.lib.astm.communication;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.astm.exception.ASTMCommunicationException;
import org.itech.ahb.lib.astm.exception.FrameParsingException;

/**
 * This interface is used to indicate a class that can send or receive via an ASTM transmission protocol (such as LIS02-A2).
 */
public interface Communicator {
  /**
   * Gets the ID for this communicator instance, useful for logging purposes.
   *
   * @return the ID of the communicator.
   */
  String getID();

  /**
   * Sends an ASTM message using the ASTM transmission protocol.
   *
   * @param message the ASTM message to send.
   * @return the result of the send operation.
   * @throws ASTMCommunicationException if there is a communication error in the ASTM transmission protocol.
   * @throws IOException if an I/O error occurs.
   * @throws InterruptedException if the operation is interrupted.
   */
  SendResult sendProtocol(ASTMMessage message) throws ASTMCommunicationException, IOException, InterruptedException;

  /**
   * Receives an ASTM message using the ASTM transmission protocol.
   *
   * @param lineWasContentious indicates if this receive protocol was started because the line was contentious. Line contention occurs after the LIS attempts to send information but the lab device sends a signal indicating it has something to send instead. ASTM transmission protocols usually dictate that the device has priority to send its message.
   * @return the received ASTM message.
   * @throws FrameParsingException if there is an error parsing the frame.
   * @throws ASTMCommunicationException if there is a communication error in the ASTM transmission protocol.
   * @throws IOException if an I/O error occurs.
   * @throws InterruptedException if the operation is interrupted.
   */
  ASTMMessage receiveProtocol(boolean lineWasContentious)
    throws FrameParsingException, ASTMCommunicationException, IOException, InterruptedException;

  /**
   * Checks if the establishment phase of the communication succeeded.
   *
   * @return true if the establishment succeeded, false otherwise.
   */
  boolean didReceiveEstablishmentSucceed();

  /**
   * Object for holding information about the result of sending data.
   */
  @Data
  @AllArgsConstructor
  public class SendResult {

    private boolean lineContention;
    private boolean rejected;
  }
}
