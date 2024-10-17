package org.itech.ahb.lib.http.handling;

import lombok.Data;
import org.itech.ahb.lib.astm.servlet.ASTMServlet.ASTMVersion;

@Data
public class HTTPForwardingHandlerInfo implements HTTPHandlerInfo {

  private String forwardAddress;
  private int forwardPort;
  private ASTMVersion forwardAstmVersion = ASTMVersion.LIS01_A;

  @Override
  public boolean supports(HTTPHandler value) {
    return value instanceof DefaultForwardingHTTPToASTMHandler;
  }
}
