package org.itech.ahb.lib.astm.concept;

/**
 * This interface defines methods for ASTM records.
 * ASTM records are not defined in the ASTM LIS01-A2, and are instead defined at length in ASTM LIS02-A2
 * Records are not a concept in the ASTM transmission protocol, and are instead a concept in the ASTM message format.
 */
public interface ASTMRecord {
  /**
   * Gets the length of the ASTM record.
   *
   * @return the length of the record.
   */
  int getRecordLength();

  /**
   * Gets the text of the ASTM record.
   *
   * @return the text of the record.
   */
  String getRecord();
}
