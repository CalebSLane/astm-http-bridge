package org.itech.ahb.lib.astm.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.lib.astm.ASTMHandlerResponse;
import org.itech.ahb.lib.common.ASTMMessage;
import org.itech.ahb.lib.common.HandleStatus;

@Slf4j
public class ASTMHandlerMarshaller {

  public enum MarshallerMode {
    ALL,
    FIRST
  }

  private List<ASTMHandler> handlers;
  private MarshallerMode mode;

  public ASTMHandlerMarshaller(List<ASTMHandler> handlers, MarshallerMode mode) {
    this.handlers = handlers;
    this.mode = mode;
  }

  public ASTMMarshallerResponse handle(ASTMMessage message) {
    Map<ASTMMessage, List<ASTMHandler>> messageHandlersMap = new HashMap<>();
    log.debug("finding a handler for astm message: " + message.hashCode());
    for (ASTMHandler handler : handlers) {
      if (handler.matches(message)) {
        log.debug("handler: '" + handler.getName() + "' found for astm message: " + message.hashCode());
        List<ASTMHandler> matchingMessageHandlers = messageHandlersMap.getOrDefault(message, new ArrayList<>());
        matchingMessageHandlers.add(handler);
        messageHandlersMap.put(message, matchingMessageHandlers);
        if (mode == MarshallerMode.FIRST) {
          log.debug("marshall mode is FIRST, proceeding with a single handler");
          break;
        }
      }
    }
    if (!messageHandlersMap.containsKey(message)) {
      log.warn("astm message received but no handler was configured to handle the message");
      log.debug("finished handling astm messages");
      return new ASTMMarshallerResponse();
    }

    List<ASTMHandlerResponse> handleResponses = new ArrayList<>();
    log.debug("handling astm message...");
    for (Entry<ASTMMessage, List<ASTMHandler>> matchingMessageHandlers : messageHandlersMap.entrySet()) {
      for (ASTMHandler messageHandler : matchingMessageHandlers.getValue()) {
        try {
          ASTMHandlerResponse handleResponse = messageHandler.handle(matchingMessageHandlers.getKey());
          log.debug("'" + messageHandler.getName() + "' finished handling http astm message");
          handleResponses.add(handleResponse);
        } catch (RuntimeException e) {
          log.error(
            "unexpected error occurred during '" +
            messageHandler.getName() +
            "' handling astm message: " +
            matchingMessageHandlers.getKey(),
            e
          );
          handleResponses.add(new ASTMHandlerResponse("", HandleStatus.FAIL, false, messageHandler));
          // TODO add some handle exception handling. retry queue? db save?
          // handler.handleError();
        }
      }
    }
    return new ASTMMarshallerResponse(handleResponses);
  }
}
