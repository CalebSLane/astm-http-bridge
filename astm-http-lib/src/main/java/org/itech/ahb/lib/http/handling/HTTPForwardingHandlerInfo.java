package org.itech.ahb.lib.http.handling;

import lombok.Data;
import org.itech.ahb.lib.astm.servlet.ASTMServlet.ASTMVersion;

/**
 * This class holds information that is used for forwrding an ASTM message over HTTP(S).
 */
@Data
public class HTTPForwardingHandlerInfo implements HTTPHandlerInfo {

  private String forwardAddress;
  private int forwardPort;
  private ASTMVersion forwardAstmVersion = ASTMVersion.LIS01_A;

  /**
   * @param value the handler to check if this handler info supports
   * @return true if the handler is of type DefaultForwardingHTTPToASTMHandler, false otherwise.
   */
  @Override
  public boolean supports(HTTPHandler value) {
    return value instanceof DefaultForwardingHTTPToASTMHandler;
  }
}
