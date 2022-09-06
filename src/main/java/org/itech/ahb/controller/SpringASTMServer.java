package org.itech.ahb.controller;

import org.itech.ahb.lib.astm.servlet.ASTMServlet;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SpringASTMServer {

	private ASTMServlet server;

	public SpringASTMServer(ASTMServlet server) {
		this.server = server;
	}

	@Async
	public void listen() {
		server.listen();
	}
}
