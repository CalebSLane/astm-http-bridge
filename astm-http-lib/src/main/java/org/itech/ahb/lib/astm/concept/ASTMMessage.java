package org.itech.ahb.lib.astm.concept;

import java.util.List;

public interface ASTMMessage {
  void addRecord(ASTMRecord record);
  List<ASTMRecord> getRecords();
  int getMessageLength();
  String getMessage();
}
