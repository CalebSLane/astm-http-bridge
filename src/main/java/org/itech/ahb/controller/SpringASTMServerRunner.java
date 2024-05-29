package org.itech.ahb.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SpringASTMServerRunner {

  private SpringASTMServer springASTMServer;

  public SpringASTMServerRunner(SpringASTMServer springASTMServer) {
    this.springASTMServer = springASTMServer;
  }

  @PostConstruct
  public void listen() {
    springASTMServer.listen();
  }
}
