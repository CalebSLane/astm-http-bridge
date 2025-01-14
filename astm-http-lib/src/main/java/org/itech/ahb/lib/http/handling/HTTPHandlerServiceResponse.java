package org.itech.ahb.lib.http.handling;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.itech.ahb.lib.common.handling.HandlerServiceResponse;

/**
 * This class represents the response from an HTTP handler service. ie. a collection of responses from individual handlers.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class HTTPHandlerServiceResponse implements HandlerServiceResponse {

  List<HTTPHandlerResponse> responses;
}
