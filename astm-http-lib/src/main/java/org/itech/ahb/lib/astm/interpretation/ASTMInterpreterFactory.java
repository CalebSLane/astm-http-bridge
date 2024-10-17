package org.itech.ahb.lib.astm.interpretation;

import java.util.List;
import org.itech.ahb.lib.astm.concept.ASTMFrame;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.astm.concept.ASTMRecord;

public interface ASTMInterpreterFactory {
  ASTMInterpreter createInterpreterForFrames(List<ASTMFrame> frames);

  ASTMInterpreter createInterpreterForRecords(List<ASTMRecord> records);

  ASTMInterpreter createInterpreter(ASTMMessage message);

  ASTMInterpreter createInterpreterForText(String text);
}
