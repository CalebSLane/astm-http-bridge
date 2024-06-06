package org.itech.ahb.lib.astm.servlet;

import java.io.IOException;
import org.itech.ahb.lib.common.ASTMMessage;
import org.itech.ahb.lib.common.exception.ASTMCommunicationException;
import org.itech.ahb.lib.common.exception.FrameParsingException;

public interface Communicator {
  String getID();
  boolean sendProtocol(ASTMMessage message) throws ASTMCommunicationException, IOException;
  ASTMMessage receiveProtocol() throws FrameParsingException, ASTMCommunicationException, IOException;
}
