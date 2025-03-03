package org.itech.ahb.lib.http.handling;

/**
 * This interface defines methods for providing information for handling HTTP requests.
 */
public interface HTTPHandlerInfo {
  /**
   * Checks if the handler info supports the given HTTP handler.
   *
   * @param value the HTTP handler to check against.
   * @return true if the handler info supports the handler, false otherwise.
   */
  boolean supports(HTTPHandler value);
}
