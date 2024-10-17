package org.itech.ahb.lib.astm.concept;

public interface ASTMFrame {
  public enum FrameType {
    INTERMEDIATE,
    END
  }

  FrameType getType();
  void setType(FrameType object);
  String getText();
  void setText(String text);
  void setFrameNumber(int numericValue);
  int getFrameNumber();
}
