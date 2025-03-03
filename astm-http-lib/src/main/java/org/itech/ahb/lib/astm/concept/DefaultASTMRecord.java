package org.itech.ahb.lib.astm.concept;

import lombok.Data;

@Data
public class DefaultASTMRecord implements ASTMRecord {

  private String record;

  /**
   * Constructor for creating an ASTM record by passing in the record as a string.
   * @param record the record text to store.
   */
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
