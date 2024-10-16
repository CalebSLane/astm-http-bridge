package org.itech.ahb.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.config.properties.ASTMForwardServerConfigurationProperties;
import org.itech.ahb.lib.astm.servlet.ASTMHandlerMarshaller;
import org.itech.ahb.lib.astm.servlet.ASTMServlet.ASTMVersion;
import org.itech.ahb.lib.common.ASTMInterpreterFactory;
import org.itech.ahb.lib.common.ASTMMessage;
import org.itech.ahb.lib.common.DefaultASTMMessage;
import org.itech.ahb.lib.common.HandleStatus;
import org.itech.ahb.lib.http.servlet.DefaultForwardingHTTPToASTMHandler;
import org.itech.ahb.lib.http.servlet.HTTPForwardingHandlerInfo;
import org.itech.ahb.lib.http.servlet.HTTPHandler;
import org.itech.ahb.lib.http.servlet.HTTPHandlerMarshaller;
import org.itech.ahb.lib.http.servlet.HTTPHandlerMarshaller.MarshallerMode;
import org.itech.ahb.lib.http.servlet.HTTPHandlerResponse;
import org.itech.ahb.lib.http.servlet.HTTPMarshallerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class HTTPListenController {

  private final HTTPHandlerMarshaller httpHandlerMarshaller;
  private final ASTMInterpreterFactory interpreterFactory;

  public HTTPListenController(
    ASTMInterpreterFactory interpreterFactory,
    ASTMForwardServerConfigurationProperties astmForwardConfig,
    ASTMHandlerMarshaller astmHandlerMarshaller
  ) {
    List<HTTPHandler> httpHandlers = Arrays.asList(
      new DefaultForwardingHTTPToASTMHandler(
        astmForwardConfig.getHostName(),
        astmForwardConfig.getPort(),
        astmHandlerMarshaller,
        interpreterFactory
      )
    );
    this.interpreterFactory = interpreterFactory;
    this.httpHandlerMarshaller = new HTTPHandlerMarshaller(httpHandlers, MarshallerMode.FIRST);
  }

  @PostMapping
  public HTTPMarshallerResponse recieveASTMMessageOverHttp(
    @RequestBody(required = false) String requestBody,
    @RequestParam(required = false) String forwardAddress,
    @RequestParam(required = false, defaultValue = "0") Integer forwardPort,
    @RequestParam(required = false, defaultValue = "LIS01_A") ASTMVersion forwardAstmVersion,
    HttpServletResponse response
  ) {
    log.debug("received http request to handle");
    ASTMMessage message = interpreterFactory
      .createInterpreterForText(requestBody)
      .interpretASTMTextToMessage(requestBody);
    HTTPForwardingHandlerInfo handlerInfo = new HTTPForwardingHandlerInfo();
    handlerInfo.setForwardAddress(forwardAddress);
    handlerInfo.setForwardPort(forwardPort);
    handlerInfo.setForwardAstmVersion(forwardAstmVersion);
    HTTPMarshallerResponse marshallerResponse = httpHandlerMarshaller.handle(message, Set.of(handlerInfo));
    if (marshallerResponse.getResponses() == null || marshallerResponse.getResponses().size() == 0) {
      log.error("message was unhandled");
    } else {
      for (HTTPHandlerResponse handlerResponse : marshallerResponse.getResponses()) {
        if (handlerResponse.getStatus() != HandleStatus.SUCCESS) {
          log.error("message was not handled successfully by: " + handlerResponse.getHandler().getName());
        } else {
          log.debug("message was handled successfully by: " + handlerResponse.getHandler().getName());
        }
      }
    }
    return marshallerResponse;
  }
}
