package org.itech.ahb.lib.util;

import java.io.BufferedReader;
import java.io.IOException;

public class ThreadUtil {

  public static char readCharWithInterruptCheck(BufferedReader reader) throws IOException, InterruptedException {
    char character = (char) reader.read();
    if (Thread.interrupted()) {
      throw new InterruptedException();
    }
    return character;
  }
}
