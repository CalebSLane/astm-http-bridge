package org.itech.ahb.lib.http.handling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.common.handling.HandleStatus;

/**
 * This class provides a service layer for deciding which HTTP handlers should be called for a message and then calling them.
 * Instead of directly calling a handler, this class should be used.
 */
@Slf4j
public class HTTPHandlerService {

  /**
   *  The mode of the handler service. ex, Call all handlers or just the first one.
   */
  public enum Mode {
    /**
     *  Call all handlers that match the message.
     */
    ALL,
    /**
     *  Call the first handler that matches the message.
     */
    FIRST
  }

  private List<HTTPHandler> handlers;
  private Mode mode;

  /**
   * Create a new HTTPHandlerService.
   * @param handlers the list of handlers to this handler service should be aware of.
   * @param mode the mode this handler service should operate.
   */
  public HTTPHandlerService(List<HTTPHandler> handlers, Mode mode) {
    this.handlers = handlers;
    this.mode = mode;
  }

  /**
   * Calls the relevant handler(s) for the given ASTM message.
   *
   * @param message the ASTM message.
   * @return the HTTP handler service response.
   */
  public HTTPHandlerServiceResponse handle(ASTMMessage message) {
    return handle(message, Set.of());
  }

  /**
   * Handles the given HTTP message with the provided handler information.
   *
   * @param message the ASTM message.
   * @param handlersInfos the set of handler information.
   * @return the HTTP handler service response.
   */
  public HTTPHandlerServiceResponse handle(ASTMMessage message, Set<HTTPForwardingHandlerInfo> handlersInfos) {
    Map<ASTMMessage, List<HTTPHandler>> messageHandlersMap = new HashMap<>();
    log.debug("finding a handler for astm http message: " + message.hashCode());
    for (HTTPHandler handler : handlers) {
      if (handler.matches(message)) {
        log.debug("handler found for astm http message: " + message.hashCode());
        List<HTTPHandler> matchingMessageHandlers = messageHandlersMap.getOrDefault(message, new ArrayList<>());
        matchingMessageHandlers.add(handler);

        messageHandlersMap.put(message, matchingMessageHandlers);
        if (mode == Mode.FIRST) {
          log.debug("marshall mode is FIRST, proceeding with a single handler");
          break;
        }
      }
    }
    if (!messageHandlersMap.containsKey(message)) {
      log.warn("astm http message received but no handler was configured to handle the message");
      return new HTTPHandlerServiceResponse();
    }

    List<HTTPHandlerResponse> handleResponses = new ArrayList<>();
    log.debug("handling astm http message...");
    for (Entry<ASTMMessage, List<HTTPHandler>> matchingMessageHandlers : messageHandlersMap.entrySet()) {
      for (HTTPHandler messageHandler : matchingMessageHandlers.getValue()) {
        try {
          HTTPHandlerResponse handleResponse = messageHandler.handle(
            matchingMessageHandlers.getKey(),
            handlersInfos.stream().filter(e -> e.supports(messageHandler)).collect(Collectors.toSet())
          );
          log.debug("'" + messageHandler.getName() + "' finished handling astm http message");
          handleResponses.add(handleResponse);
        } catch (RuntimeException e) {
          log.error(
            "unexpected error occurred during '" +
            messageHandler.getName() +
            "' handling astm http message: " +
            matchingMessageHandlers.getKey(),
            e
          );
          handleResponses.add(new HTTPHandlerResponse("", HandleStatus.GENERIC_FAIL, false, messageHandler));
        }
      }
    }
    // TODO add some handle exception handling. for every handleResponse not success call messageHandler.handleFailure();

    return new HTTPHandlerServiceResponse(handleResponses);
  }
}
