package org.itech.ahb.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.itech.ahb.config.properties.ASTMForwardServerConfigurationProperties;
import org.itech.ahb.lib.common.ASTMInterpreterFactory;
import org.itech.ahb.lib.common.ASTMMessage;
import org.itech.ahb.lib.common.DefaultASTMMessage;
import org.itech.ahb.lib.http.servlet.DefaultForwardingHTTPToASTMHandler;
import org.itech.ahb.lib.http.servlet.HTTPHandler;
import org.itech.ahb.lib.http.servlet.HTTPHandler.HandleStatus;
import org.itech.ahb.lib.http.servlet.HTTPHandlerMarshaller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HTTPListenController {

	private final HTTPHandlerMarshaller httpHandlerMarshaller;

	public HTTPListenController(ASTMInterpreterFactory interpreterFactory,
			ASTMForwardServerConfigurationProperties astmForwardConfig) {
		List<HTTPHandler> httpHandlers = Arrays.asList(new DefaultForwardingHTTPToASTMHandler(
				astmForwardConfig.getHostName(), astmForwardConfig.getPort(), interpreterFactory));
		this.httpHandlerMarshaller = new HTTPHandlerMarshaller(httpHandlers);
	}

	@PostMapping
	public void recieveASTM(@RequestBody String requestBody, HttpServletResponse response) {
		try {
			ASTMMessage message = new DefaultASTMMessage(requestBody);
			HandleStatus status = httpHandlerMarshaller.handle(message);
			if (status.equals(HandleStatus.SUCCESS)) {
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
