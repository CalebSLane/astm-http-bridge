package org.itech.ahb.lib.astm.exception;

/**
 * Exception thrown when there is a communication error in the ASTM transmission protocol.
 */
public class ASTMCommunicationException extends Exception {

  private static final long serialVersionUID = -7478368614716068041L;

  /**
   * Constructs a new ASTMCommunicationException with {@code null} as its detail message.
   */
  public ASTMCommunicationException() {
    super();
  }

  /**
   * Constructs a new ASTMCommunicationException with the specified detail message.
   *
   * @param message the detail message.
   */
  public ASTMCommunicationException(String message) {
    super(message);
  }

  /**
   * Constructs a new ASTMCommunicationException with the specified cause.
   *
   * @param t the cause.
   */
  public ASTMCommunicationException(Throwable t) {
    super(t);
  }

  /**
   * Constructs a new ASTMCommunicationException with the specified detail message and cause.
   *
   * @param message the detail message.
   * @param t the cause.
   */
  public ASTMCommunicationException(String message, Throwable t) {
    super(message, t);
  }
}
