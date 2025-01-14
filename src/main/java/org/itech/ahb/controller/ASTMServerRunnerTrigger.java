package org.itech.ahb.controller;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.itech.ahb.lib.astm.servlet.ASTMServlet;
import org.springframework.stereotype.Component;

/**
 * Component for triggering an ASTM server runner for each ASTM transmission servlet.
 */
@Component
public class ASTMServerRunnerTrigger {

  private ASTMServerRunner serverRunner;
  private List<ASTMServlet> astmServlets;

  /**
   * Constructor for ASTMServerRunnerTrigger.
   *
   * @param serverRunner the ASTM server runner
   * @param astmServlets the list of ASTM servlets that need to be run
   */
  public ASTMServerRunnerTrigger(ASTMServerRunner serverRunner, List<ASTMServlet> astmServlets) {
    this.serverRunner = serverRunner;
    this.astmServlets = astmServlets;
  }

  /**
   * Triggers the ASTM server runner to start the servlets once this class is done setting up.
   */
  @PostConstruct
  public void triggerRunner() {
    for (ASTMServlet astmServlet : astmServlets) {
      serverRunner.run(astmServlet);
    }
  }
}
