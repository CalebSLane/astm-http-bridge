package org.itech.ahb.controller;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.itech.ahb.lib.astm.servlet.ASTMServlet;
import org.springframework.stereotype.Component;

@Component
public class ASTMServerRunnerTrigger {

  private ASTMServerRunner serverRunner;
  private List<ASTMServlet> astmServlets;

  public ASTMServerRunnerTrigger(ASTMServerRunner serverRunner, List<ASTMServlet> astmServlets) {
    this.serverRunner = serverRunner;
    this.astmServlets = astmServlets;
  }

  @PostConstruct
  public void triggerRunner() {
    for (ASTMServlet astmServlet : astmServlets) {
      serverRunner.run(astmServlet);
    }
  }
}
