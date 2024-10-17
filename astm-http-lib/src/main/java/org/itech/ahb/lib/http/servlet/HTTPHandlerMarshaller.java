package org.itech.ahb.lib.http.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.lib.common.ASTMMessage;
import org.itech.ahb.lib.common.HandleStatus;
import org.itech.ahb.lib.common.exception.FrameParsingException;

@Slf4j
public class HTTPHandlerMarshaller {

  public enum MarshallerMode {
    ALL,
    FIRST
  }

  private List<HTTPHandler> handlers;
  private MarshallerMode mode;

  public HTTPHandlerMarshaller(List<HTTPHandler> handlers, MarshallerMode mode) {
    this.handlers = handlers;
    this.mode = mode;
  }

  public HTTPMarshallerResponse handle(ASTMMessage message) {
    return handle(message, Set.of());
  }

  public HTTPMarshallerResponse handle(ASTMMessage message, Set<HTTPForwardingHandlerInfo> handlersInfos) {
    Map<ASTMMessage, List<HTTPHandler>> messageHandlersMap = new HashMap<>();
    log.debug("finding a handler for astm http message: " + message.hashCode());
    for (HTTPHandler handler : handlers) {
      if (handler.matches(message)) {
        log.debug("handler found for astm http message: " + message.hashCode());
        List<HTTPHandler> matchingMessageHandlers = messageHandlersMap.getOrDefault(message, new ArrayList<>());
        matchingMessageHandlers.add(handler);

        messageHandlersMap.put(message, matchingMessageHandlers);
        if (mode == MarshallerMode.FIRST) {
          log.debug("marshall mode is FIRST, proceeding with a single handler");
          break;
        }
      }
    }
    if (!messageHandlersMap.containsKey(message)) {
      log.warn("astm http message received but no handler was configured to handle the message");
      return new HTTPMarshallerResponse();
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
        } catch (FrameParsingException e) {
          log.error("couldn't parse frames into a message", e);
          handleResponses.add(new HTTPHandlerResponse("", HandleStatus.FAIL_FRAME_PARSING, false, messageHandler));
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

    return new HTTPMarshallerResponse(handleResponses);
  }
}
