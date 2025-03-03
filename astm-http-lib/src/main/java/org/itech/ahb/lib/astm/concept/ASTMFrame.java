package org.itech.ahb.lib.astm.concept;

/**
 * This interface defines methods for ASTM frames.
 * A frame is defined in the ASTM LIS01-A2 as -
 * "a subdivision of a message, used to allow periodic communication housekeeping, such as
 * error checks and acknowledgments."
 *
 * ASTM Message are communicated via a sequence of one or more frames.
 * If there is more than one frame making up the message, the last one will be of type END. All others will be of type INTERMEDIATE.
 */
public interface ASTMFrame {
  /**
   *  The type of frame this is.
   */
  public enum FrameType {
    /**
     * An intermediate frame. Indicates that more frames should be expected for the ASTM message.
     */
    INTERMEDIATE,
    /**
     * An end frame. Indicates that no more frames should be expected for the ASTM message.
     */
    END
  }

  /**
   * Gets the type of the frame.
   *
   * @return the type of the frame.
   */
  FrameType getType();

  /**
   * Sets the type of the frame.
   *
   * @param type the type of the frame.
   */
  void setType(FrameType type);

  /**
   * Gets the text of the frame.
   * The text is the portion that will be joined with the other frames to build the ASTM message.
   *
   * @return the text of the frame.
   */
  String getText();

  /**
   * Sets the text of the frame.
   * The text is the portion that will be joined with the other frames to build the ASTM message.
   *
   * @param text the text of the frame.
   */
  void setText(String text);

  /**
   * Sets the frame number.
   *
   * @param numericValue the frame number.
   */
  void setFrameNumber(int numericValue);

  /**
   * Gets the frame number.
   *
   * @return the frame number.
   */
  int getFrameNumber();
}
