package org.itech.ahb.lib.http.servlet;

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

  public HandleStatus handle(ASTMMessage message) {
    return handle(message, Set.of());
  }

  //TODO rework this like ASTMHandler to allow multiple handlers and more informative responses
  public HandleStatus handle(ASTMMessage message, Set<HttpForwardingHandlerInfo> handlersInfos) {
    Map<ASTMMessage, HTTPHandler> messageHandlers = new HashMap<>();
    log.debug("finding a handler for astm http message: " + message.hashCode());
    for (HTTPHandler handler : handlers) {
      if (handler.matches(message)) {
        log.debug("handler found for astm http message: " + message.hashCode());
        messageHandlers.put(message, handler);
        if (mode == MarshallerMode.FIRST) {
          break;
        }
      }
    }
    if (!messageHandlers.containsKey(message)) {
      log.warn("astm http message received but no handler was configured to handle the message");
    }

    log.debug("handling astm http message...");
    for (Entry<ASTMMessage, HTTPHandler> messageHandler : messageHandlers.entrySet()) {
      try {
        HandleStatus status = messageHandler
          .getValue()
          .handle(
            messageHandler.getKey(),
            handlersInfos.stream().filter(e -> e.supports(messageHandler.getValue())).collect(Collectors.toSet())
          );
        log.debug("finished attempting handling astm http message");
        return status;
      } catch (RuntimeException e) {
        log.error("unexpected error occurred during handling astm http message: " + messageHandler.getKey(), e);
        return HandleStatus.FAIL;
        // TODO add some handle exception handling. retry queue? db save?
      } catch (FrameParsingException e) {
        log.error("Line contention has occured and could not parse the received information", e);
        return HandleStatus.FAIL;
      }
    }
    return HandleStatus.UNHANDLED;
  }
}
