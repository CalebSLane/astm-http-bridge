package org.itech.ahb.lib.astm.concept;

import lombok.Data;

@Data
public class DefaultASTMRecord implements ASTMRecord {

  private String record;

  public DefaultASTMRecord(String record) {
    this.record = record;
  }

  /**
   * @return int
   */
  @Override
  public int getRecordLength() {
    if (record == null) {
      return 0;
    } else {
      return record.length();
    }
  }

  /**
   * @return String
   */
  public String getRecord() {
    if (record == null) {
      return "";
    }
    return record;
  }
}
