package org.itech.ahb.lib.astm.interpretation;

import java.util.List;
import org.itech.ahb.lib.astm.concept.ASTMFrame;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.astm.concept.ASTMRecord;

public class DefaultASTMInterpreterFactory implements ASTMInterpreterFactory {

  /**
   * @param frames
   * @return ASTMInterpreter
   */
  @Override
  public ASTMInterpreter createInterpreterForFrames(List<ASTMFrame> frames) {
    return new DefaultASTMInterpreter();
  }

  /**
   * @param records
   * @return ASTMInterpreter
   */
  @Override
  public ASTMInterpreter createInterpreterForRecords(List<ASTMRecord> records) {
    return new DefaultASTMInterpreter();
  }

  @Override
  public ASTMInterpreter createInterpreter(ASTMMessage message) {
    return new DefaultASTMInterpreter();
  }

  @Override
  public ASTMInterpreter createInterpreterForText(String text) {
    return new DefaultASTMInterpreter();
  }
}
