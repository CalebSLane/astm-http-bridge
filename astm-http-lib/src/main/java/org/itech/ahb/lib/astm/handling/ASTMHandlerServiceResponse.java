package org.itech.ahb.lib.astm.handling;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.itech.ahb.lib.common.handling.HandlerServiceResponse;

/**
 * This class represents the response from an ASTM handler service. ie. a collection of responses from individual hadlers.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ASTMHandlerServiceResponse implements HandlerServiceResponse {

  List<ASTMHandlerResponse> responses;
}
