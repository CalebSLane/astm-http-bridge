package org.itech.ahb.lib.astm.handling;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.itech.ahb.lib.common.handling.MarshallerResponse;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ASTMMarshallerResponse implements MarshallerResponse {

  List<ASTMHandlerResponse> responses;
}
