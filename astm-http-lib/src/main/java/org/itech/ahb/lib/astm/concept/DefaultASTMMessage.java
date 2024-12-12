package org.itech.ahb.lib.astm.concept;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class DefaultASTMMessage implements ASTMMessage {

  private List<ASTMRecord> records;

  public DefaultASTMMessage() {}

  public DefaultASTMMessage(String message) {
    records = Arrays.stream(message.split("((?<=\\n))"))
      .map(e -> new DefaultASTMRecord(e))
      .collect(Collectors.toList());
  }

  public DefaultASTMMessage(List<ASTMRecord> records) {
    this.records = records;
  }

  /**
   * @return int
   */
  @Override
  public int getMessageLength() {
    if (records == null) {
      return 0;
    } else {
      return records.stream().mapToInt(record -> record.getRecordLength()).sum();
    }
  }

  /**
   * @return String
   */
  public String getMessage() {
    if (records == null) {
      return "";
    }
    return records.stream().map(record -> record.getRecord()).collect(Collectors.joining(""));
  }

  @Override
  public void addRecord(ASTMRecord record) {
    if (records == null) {
      records = new ArrayList<>();
    }
    records.add(record);
  }

  @Override
  public List<ASTMRecord> getRecords() {
    return Collections.unmodifiableList(records);
  }
}
