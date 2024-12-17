package org.itech.ahb.lib.astm.handling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.itech.ahb.lib.common.handling.HandleStatus;
import org.itech.ahb.lib.common.handling.HandlerResponse;

/**
 * This class represents the response from an ASTM handler.
 */
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
