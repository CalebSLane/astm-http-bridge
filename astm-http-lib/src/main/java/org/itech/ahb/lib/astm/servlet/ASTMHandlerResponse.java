package org.itech.ahb.lib.astm.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.itech.ahb.lib.common.HandleStatus;
import org.itech.ahb.lib.common.HandlerResponse;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ASTMHandlerResponse implements HandlerResponse {

  @NonNull
  String response;

  @NonNull
  HandleStatus status;

  Boolean communicateResponse = false;

  @NonNull
  ASTMHandler handler;
}
