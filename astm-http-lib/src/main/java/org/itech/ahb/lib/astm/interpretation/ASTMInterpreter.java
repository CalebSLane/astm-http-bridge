package org.itech.ahb.lib.astm.interpretation;

import java.util.List;
import org.itech.ahb.lib.astm.concept.ASTMFrame;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.astm.concept.ASTMRecord;
import org.itech.ahb.lib.astm.exception.FrameParsingException;

public interface ASTMInterpreter {
  ASTMMessage interpretFramesToASTMMessage(List<ASTMFrame> frames) throws FrameParsingException;

  List<ASTMFrame> interpretASTMRecordsToFrames(ASTMRecord record);

  List<ASTMFrame> interpretASTMMessageToFrames(ASTMMessage message);

  ASTMMessage interpretASTMRecordsToMessage(List<ASTMRecord> records);

  ASTMRecord interpretASTMTextToRecord(String recordText);

  ASTMMessage interpretASTMTextToMessage(String messageText);
}
