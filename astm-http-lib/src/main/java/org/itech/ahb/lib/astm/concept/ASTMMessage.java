package org.itech.ahb.lib.astm.concept;

import java.util.List;

/**
 * This interface defines methods for ASTM messages.
 * A message is defined in the ASTM LIS01-A2 as -
 * "a collection of related information on a single topic, used here to mean all the identity,
 * tests, and comments sent at one time;"
 *
 * ASTM Message are communicated via a sequence of one or more frames.
 * An ASTM message is a collection of ASTM records.
 */
public interface ASTMMessage {
  /**
   * Adds a record to the ASTM message.
   *
   * @param record the ASTM record to add.
   */
  void addRecord(ASTMRecord record);

  /**
   * Gets the list of records in the ASTM message.
   *
   * @return the list of records.
   */
  List<ASTMRecord> getRecords();

  /**
   * Gets the length of the ASTM message.
   *
   * @return the length of the message.
   */
  int getMessageLength();

  /**
   * Gets the text of the ASTM message. This is a textual representation of all of the records this message contains.
   *
   * @return the text of the message.
   */
  String getMessage();
}
