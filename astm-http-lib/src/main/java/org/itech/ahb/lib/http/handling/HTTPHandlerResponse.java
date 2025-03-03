package org.itech.ahb.lib.http.handling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.itech.ahb.lib.common.handling.HandleStatus;
import org.itech.ahb.lib.common.handling.HandlerResponse;

/**
 * This class represents the response from an HTTP handler.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class HTTPHandlerResponse implements HandlerResponse {

  @NonNull
  String response;

  @NonNull
  HandleStatus status;

  Boolean communicateResponse = false;

  @NonNull
  HTTPHandler handler;
}
