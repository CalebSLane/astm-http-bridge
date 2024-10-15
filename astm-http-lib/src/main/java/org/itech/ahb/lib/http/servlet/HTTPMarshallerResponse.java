package org.itech.ahb.lib.http.servlet;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.itech.ahb.lib.common.MarshallerResponse;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class HTTPMarshallerResponse implements MarshallerResponse {

  List<HTTPHandlerResponse> responses;
}
