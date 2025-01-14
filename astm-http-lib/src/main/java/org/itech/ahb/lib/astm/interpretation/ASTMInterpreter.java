package org.itech.ahb.lib.astm.interpretation;

import java.util.List;
import org.itech.ahb.lib.astm.concept.ASTMFrame;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.astm.concept.ASTMRecord;
import org.itech.ahb.lib.astm.exception.FrameParsingException;

/**
 * This interface defines methods for interpreting ASTM frames, records, and messages.
 */
public interface ASTMInterpreter {
  /**
   * Interprets a list of ASTM frames as an ASTM message.
   *
   * @param frames the list of ASTM frames.
   * @return the complete ASTM message represented by the frames.
   * @throws FrameParsingException if there is an error parsing the frames.
   */
  ASTMMessage interpretFramesToASTMMessage(List<ASTMFrame> frames) throws FrameParsingException;

  /**
   * Interprets an ASTM message as a list of ASTM frames.
   *
   * @param message the ASTM message.
   * @return the list of interpreted ASTM frames that make up that message for transmission.
   */
  List<ASTMFrame> interpretASTMMessageToFrames(ASTMMessage message);

  /**
   * Interprets a list of ASTM records as an ASTM message.
   *
   * @param records the list of ASTM records.
   * @return the interpreted ASTM message.
   */
  ASTMMessage interpretASTMRecordsToMessage(List<ASTMRecord> records);

  /**
   * Interprets a text string as an ASTM record.
   *
   * @param recordText the text string representing the ASTM record.
   * @return the interpreted ASTM record.
   */
  ASTMRecord interpretASTMTextToRecord(String recordText);

  /**
   * Interprets a text string as an ASTM message.
   *
   * @param messageText the text string representing the ASTM message.
   * @return the interpreted ASTM message.
   */
  ASTMMessage interpretASTMTextToMessage(String messageText);
}
