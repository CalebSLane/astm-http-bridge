package org.itech.ahb.lib.astm.concept;

import lombok.Data;

@Data
public class DefaultASTMFrame implements ASTMFrame {

  private FrameType type;
  private int frameNumber = 0;
  private String text;
}
