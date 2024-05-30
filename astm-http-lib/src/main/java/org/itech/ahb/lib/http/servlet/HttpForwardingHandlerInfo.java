package org.itech.ahb.lib.http.servlet;

import lombok.Data;

@Data
public class HttpForwardingHandlerInfo implements HTTPHandlerInfo {

  private String forwardAddress;
  private int forwardPort;

  @Override
  public boolean supports(HTTPHandler value) {
    return value instanceof DefaultForwardingHTTPToASTMHandler;
  }
}
