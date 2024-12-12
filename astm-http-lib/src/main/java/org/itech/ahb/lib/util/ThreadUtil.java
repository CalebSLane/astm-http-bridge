package org.itech.ahb.lib.util;

import java.io.BufferedReader;
import java.io.IOException;

public class ThreadUtil {

  
  /** 
   * @param reader
   * @return char
   * @throws IOException
   * @throws InterruptedException
   */
  public static char readCharWithInterruptCheck(BufferedReader reader) throws IOException, InterruptedException {
    char character = (char) reader.read();
    if (Thread.interrupted()) {
      throw new InterruptedException();
    }
    return character;
  }
}
