package org.itech.ahb.lib.common;

import lombok.Data;

@Data
public class DefaultASTMMessage implements ASTMMessage {

  private String message;

  public DefaultASTMMessage(String message) {
    this.message = message;
  }

  @Override
  public int getMessageLength() {
    if (message == null) {
      return 0;
    } else {
      return message.length();
    }
  }

  public String getMessage() {
    if (message == null) {
      return "";
    }
    return message;
  }
}
