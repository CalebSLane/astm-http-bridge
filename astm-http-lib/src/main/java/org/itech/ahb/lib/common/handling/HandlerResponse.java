package org.itech.ahb.lib.common.handling;

public interface HandlerResponse {
  HandleStatus getStatus();
  Boolean getCommunicateResponse();
  String getResponse();
}
