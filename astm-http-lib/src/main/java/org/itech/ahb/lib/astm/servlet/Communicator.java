package org.itech.ahb.lib.astm.servlet;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.itech.ahb.lib.common.ASTMMessage;
import org.itech.ahb.lib.common.exception.ASTMCommunicationException;
import org.itech.ahb.lib.common.exception.FrameParsingException;

public interface Communicator {
  String getID();
  SendResult sendProtocol(ASTMMessage message) throws ASTMCommunicationException, IOException, InterruptedException;
  ASTMMessage receiveProtocol(boolean lineWasContentious)
    throws FrameParsingException, ASTMCommunicationException, IOException, InterruptedException;
  boolean didReceiveEstablishmentSucceed();

  @Data
  @AllArgsConstructor
  public class SendResult {

    private boolean lineContention;

    private boolean rejected;
  }
}
