package org.itech.ahb.lib.common.handling;

/**
 * This interface defines methods for the response from a handler,
 * holding key values that are useful to know aout the state/result of
 * handling a request.
 */
public interface HandlerResponse {
  /**
   * Gets the status of the handler response.
   *
   * @return the status of the handler response.
   */
  HandleStatus getStatus();

  /**
   * Checks if the response should be communicated.
   *
   * @return true if the response should be communicated, false otherwise.
   */
  Boolean getCommunicateResponse();

  /**
   * Gets the response message.
   *
   * @return the response message.
   */
  String getResponse();
}
