package org.itech.ahb.lib.astm.exception;

/**
 * Exception thrown when there is an error parsing a frame in the ASTM transmission protocol.
 */
public class FrameParsingException extends Exception {

  private static final long serialVersionUID = -7478368614716068041L;

  /**
   * Constructs a new FrameParsingException with {@code null} as its detail message.
   */
  public FrameParsingException() {
    super();
  }

  /**
   * Constructs a new FrameParsingException with the specified detail message.
   *
   * @param message the detail message.
   */
  public FrameParsingException(String message) {
    super(message);
  }

  /**
   * Constructs a new FrameParsingException with the specified cause.
   *
   * @param t the cause.
   */
  public FrameParsingException(Throwable t) {
    super(t);
  }

  /**
   * Constructs a new FrameParsingException with the specified detail message and cause.
   *
   * @param message the detail message.
   * @param t the cause.
   */
  public FrameParsingException(String message, Throwable t) {
    super(message, t);
  }
}
