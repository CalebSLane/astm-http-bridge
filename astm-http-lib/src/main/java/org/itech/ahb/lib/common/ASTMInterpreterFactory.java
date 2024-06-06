package org.itech.ahb.lib.common;

import java.util.List;

public interface ASTMInterpreterFactory {
  ASTMInterpreter createInterpreterForFrames(List<ASTMFrame> frames);

  ASTMInterpreter createInterpreterForRecords(List<ASTMRecord> records);

  ASTMInterpreter createInterpreter(ASTMMessage message);

  ASTMInterpreter createInterpreterForText(String text);
}
