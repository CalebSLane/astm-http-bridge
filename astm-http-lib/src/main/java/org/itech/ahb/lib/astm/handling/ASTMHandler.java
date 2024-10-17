package org.itech.ahb.lib.astm.handling;

import org.itech.ahb.lib.astm.concept.ASTMMessage;

public interface ASTMHandler {
  String getName();

  ASTMHandlerResponse handle(ASTMMessage message);

  boolean matches(ASTMMessage message);
}
