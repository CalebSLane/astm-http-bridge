package org.itech.ahb.lib.common;

import java.util.List;

public class DefaultASTMInterpreterFactory implements ASTMInterpreterFactory {

  @Override
  public ASTMInterpreter createInterpreterForFrames(List<ASTMFrame> frames) {
    return new DefaultASTMInterpreter();
  }

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
