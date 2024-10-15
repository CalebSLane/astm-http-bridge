package org.itech.ahb.lib.common;

public interface HandlerResponse {
  HandleStatus getStatus();
  Boolean getCommunicateResponse();
  String getResponse();
}
