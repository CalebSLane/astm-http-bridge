package org.itech.ahb.lib.http.handling;

import java.util.Set;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.astm.exception.FrameParsingException;

public interface HTTPHandler {
  boolean matches(ASTMMessage message);

  HTTPHandlerResponse handle(ASTMMessage message, Set<HTTPHandlerInfo> handlerInfo) throws FrameParsingException;

  String getName();
}
