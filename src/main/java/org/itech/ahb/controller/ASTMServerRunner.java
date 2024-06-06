package org.itech.ahb.controller;

import org.itech.ahb.lib.astm.servlet.ASTMServlet;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//wrapper class so that we can take advantage of Spring's async handling
@Component
public class ASTMServerRunner {

  @Async
  public void run(ASTMServlet server) {
    server.listen();
  }
}
