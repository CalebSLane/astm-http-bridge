package org.itech.ahb.lib.http.handling;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.itech.ahb.lib.common.handling.MarshallerResponse;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class HTTPMarshallerResponse implements MarshallerResponse {

  List<HTTPHandlerResponse> responses;
}
