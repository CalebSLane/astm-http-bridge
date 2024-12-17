package org.itech.ahb.lib.util;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Utility class for thread-related operations.
 */
public class ThreadUtil {

  /**
   * Reads a character from the given BufferedReader and checks for thread interruption.
   * Throws an InterruptedException if the thread is interrupted.
   *
   * @param reader the BufferedReader to read from.
   * @return the read character.
   * @throws IOException if an I/O error occurs.
   * @throws InterruptedException if the thread is interrupted.
   */
  public static char readCharWithInterruptCheck(BufferedReader reader) throws IOException, InterruptedException {
    char character = (char) reader.read();
    if (Thread.interrupted()) {
      throw new InterruptedException();
    }
    return character;
  }
}
