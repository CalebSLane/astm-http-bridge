package org.itech.ahb.lib.astm.servlet;

import org.itech.ahb.lib.common.ASTMMessage;

public interface ASTMHandler {
  String getName();

  ASTMHandlerResponse handle(ASTMMessage message);

  boolean matches(ASTMMessage message);
}
