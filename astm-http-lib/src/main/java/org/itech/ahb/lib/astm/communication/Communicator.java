package org.itech.ahb.lib.astm.communication;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.astm.exception.ASTMCommunicationException;
import org.itech.ahb.lib.astm.exception.FrameParsingException;

// This interface is used to indicate a class that can send or receive via an ASTM protocol (such as LIS02-A2)
public interface Communicator {
  //the id for this communicator instance, useful for logging purposes
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
