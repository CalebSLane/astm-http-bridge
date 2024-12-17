package org.itech.ahb.lib.astm.interpretation;

import java.util.List;
import org.itech.ahb.lib.astm.concept.ASTMFrame;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.astm.concept.ASTMRecord;

/**
 * This interface defines methods for creating ASTM interpreters.
 */
public interface ASTMInterpreterFactory {
  /**
   * Creates an interpreter for a list of ASTM frames.
   *
   * @param frames the list of ASTM frames that need to be interpreted.
   * @return the created ASTM interpreter.
   */
  ASTMInterpreter createInterpreterForFrames(List<ASTMFrame> frames);

  /**
   * Creates an interpreter for a list of ASTM records.
   *
   * @param records the list of ASTM records that need to be interpreted.
   * @return the created ASTM interpreter.
   */
  ASTMInterpreter createInterpreterForRecords(List<ASTMRecord> records);

  /**
   * Creates an interpreter for an ASTM message.
   *
   * @param message the ASTM message that needs to be interpreted.
   * @return the created ASTM interpreter.
   */
  ASTMInterpreter createInterpreter(ASTMMessage message);

  /**
   * Creates an interpreter for a text string.
   *
   * @param text the text string that needs to be interpreted.
   * @return the created ASTM interpreter.
   */
  ASTMInterpreter createInterpreterForText(String text);
}
