package org.itech.ahb.lib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogUtil {

  /**
   * @param input
   * @return String
   */
  public static String convertForDisplay(char input) {
    return convertForDisplay("" + input);
  }

  /**
   * @param input
   * @return String
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
