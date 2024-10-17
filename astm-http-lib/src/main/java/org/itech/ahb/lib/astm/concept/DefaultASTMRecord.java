package org.itech.ahb.lib.astm.concept;

import lombok.Data;

@Data
public class DefaultASTMRecord implements ASTMRecord {

  private String record;

  public DefaultASTMRecord(String record) {
    this.record = record;
  }

  @Override
  public int getRecordLength() {
    if (record == null) {
      return 0;
    } else {
      return record.length();
    }
  }

  public String getRecord() {
    if (record == null) {
      return "";
    }
    return record;
  }
}
