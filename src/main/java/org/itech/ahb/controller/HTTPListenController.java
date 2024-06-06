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
import org.itech.ahb.lib.http.servlet.HTTPHandler;
import org.itech.ahb.lib.http.servlet.HTTPHandlerMarshaller;
import org.itech.ahb.lib.http.servlet.HTTPHandlerMarshaller.MarshallerMode;
import org.itech.ahb.lib.http.servlet.HttpForwardingHandlerInfo;
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
  public void recieveASTMMessageOverHttp(
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
    HttpForwardingHandlerInfo handlerInfo = new HttpForwardingHandlerInfo();
    handlerInfo.setForwardAddress(forwardAddress);
    handlerInfo.setForwardPort(forwardPort);
    handlerInfo.setForwardAstmVersion(forwardAstmVersion);
    HandleStatus status = httpHandlerMarshaller.handle(message, Set.of(handlerInfo));
    log.debug("http HandleStatus is: " + status);
    if (status.equals(HandleStatus.SUCCESS)) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
