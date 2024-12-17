package org.itech.ahb.lib.http.handling;

import java.util.Set;
import org.itech.ahb.lib.astm.concept.ASTMMessage;

/**
 * This interface defines methods for handling HTTP requests.
 */
public interface HTTPHandler {
  /**
   * Checks if the handler matches the given ASTM message.
   *
   * @param message the ASTM message.
   * @return true if the handler matches the message, false otherwise.
   */
  boolean matches(ASTMMessage message);

  /**
   * Handles the given ASTM message with the provided handler information.
   *
   * @param message the ASTM message.
   * @param handlerInfo the set of handler information.
   * @return the HTTP handler response.
   */
  HTTPHandlerResponse handle(ASTMMessage message, Set<HTTPHandlerInfo> handlerInfo);

  /**
   * Gets the name of the handler for logging purposes.
   *
   * @return the name of the handler.
   */
  String getName();
}
