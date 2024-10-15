package org.itech.ahb.lib.astm.servlet;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.itech.ahb.lib.astm.ASTMHandlerResponse;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ASTMMarshallerResponse {

  List<ASTMHandlerResponse> responses;
}
