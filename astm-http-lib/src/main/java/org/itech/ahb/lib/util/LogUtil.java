package org.itech.ahb.lib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for logging-related operations.
 */
public class LogUtil {

  /**
   * Converts a character for display in logs.
   *
   * @param input the character to convert.
   * @return the converted character as a string.
   */
  public static String convertForDisplay(char input) {
    return convertForDisplay("" + input);
  }

  /**
   * Converts a string for display in logs.
   *
   * Replaces control characters with their corresponding Unicode control character representations.
   *
   * @param input the string to convert.
   * @return the converted string.
   */
  public static String convertForDisplay(String input) {
    StringBuffer buf = new StringBuffer();
    Matcher m = Pattern.compile("[\u0000-\u001F\u007F]").matcher(input);
    while (m.find()) {
      char c = m.group().charAt(0);
      m.appendReplacement(buf, Character.toString(c == '\u007F' ? '\u2421' : (char) (c + 0x2400)));
    }
    return m.appendTail(buf).toString();
  }
}
