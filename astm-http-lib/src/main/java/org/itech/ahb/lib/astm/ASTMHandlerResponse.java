package org.itech.ahb.lib.astm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.itech.ahb.lib.astm.servlet.ASTMHandler;
import org.itech.ahb.lib.common.HandleStatus;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ASTMHandlerResponse {

  @NonNull
  String response;

  @NonNull
  HandleStatus status;

  Boolean communicateResponse = false;

  @NonNull
  ASTMHandler handler;
}
