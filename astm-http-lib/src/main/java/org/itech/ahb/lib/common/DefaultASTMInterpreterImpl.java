package org.itech.ahb.lib.common;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.lib.astm.servlet.LIS01A2Communicator;
import org.itech.ahb.lib.common.ASTMFrame.FrameType;
import org.itech.ahb.lib.common.exception.FrameParsingException;

@Slf4j
public class DefaultASTMInterpreterImpl implements ASTMInterpreter {

  private static final String RECORD_SEPERATOR = Character.toString(0x0D); // CR
  private static final String MESSAGE_TERMINATOR_RECORD_START = "L";

  @Override
  public List<ASTMMessage> interpretFramesToASTMMessages(List<ASTMFrame> frames) throws FrameParsingException {
    log.debug("interpreting frames as astm messages...");
    List<ASTMMessage> messages = new ArrayList<>();
    StringBuilder messageBuilder = new StringBuilder();
    for (ASTMFrame frame : frames) {
      log.trace("frame: " + frame);
      if (frame.getType() == FrameType.INTERMEDIATE || !frameContainsMessageTerminator(frame)) {
        log.debug("adding frame to ASTM message");
        messageBuilder.append(frame.getText());
      } else if (frame.getType() == FrameType.END) {
        log.debug("adding end frame to ASTM message");
        messageBuilder.append(frame.getText());
        String message = messageBuilder.toString();
        messages.add(new DefaultASTMMessage(message));
        log.trace("added message: '" + message + "' to list of messages");
        messageBuilder = new StringBuilder();
      } else {
        throw new FrameParsingException("frame type is an unrecognized type so message cannot be reconstructed");
      }
    }
    log.debug("finished interpreting frames as astm messages");
    return messages;
  }

  private boolean frameContainsMessageTerminator(ASTMFrame frame) {
    String[] lines = frame.getText().split(RECORD_SEPERATOR);
    if (lines[lines.length - 1].startsWith(MESSAGE_TERMINATOR_RECORD_START)) {
      return true;
    }
    return false;
  }

  @Override
  public List<ASTMFrame> interpretASTMMessageToFrames(ASTMMessage message) {
    log.debug("interpreting astm messages as frames...");
    List<ASTMFrame> frames = new ArrayList<>();
    String[] frameTexts = message.getMessage().split("(?<=\\G.{" + LIS01A2Communicator.MAX_TEXT_SIZE + "})");
    log.trace("astm message: " + message);
    for (int i = 0; i < frameTexts.length; i++) {
      ASTMFrame curFrame = new ASTMFrame();
      curFrame.setText(frameTexts[i]);
      curFrame.setFrameNumber((i + 1) % 8);
      curFrame.setType(i != (frameTexts.length - 1) ? FrameType.INTERMEDIATE : FrameType.END);
      frames.add(curFrame);
      log.trace(curFrame.toString());
    }
    log.debug("finished interpreting astm message as frames");
    return frames;
  }
}
