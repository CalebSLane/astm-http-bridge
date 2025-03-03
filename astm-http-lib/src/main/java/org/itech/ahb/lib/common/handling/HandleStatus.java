package org.itech.ahb.lib.common.handling;

/**
 * This enum represents the status of a handler response when handling a message.
 */
public enum HandleStatus {
  /**
   * The message was handled successfully, per the intention of the handler.
   */
  SUCCESS,
  /**
   * The message could not be handled successfully, and no other status described why adequately.
   */
  GENERIC_FAIL,
  /**
   * The message could not be handled successfully, received a bad response frome a forwarding entity.
   */
  FORWARD_FAIL_BAD_RESPONSE,
  /**
   * The message could not be handled successfully, an ungraceful error occurred while trying to forward.
   */
  FORWARD_FAIL_ERROR,
  /**
   * The message could not be handled successfully, too many attempts while trying to forward.
   */
  FAIL_TOO_MANY_ATTEMPTS,
  /**
   * The message could not be handled successfully, the line was contested by the forwarding entity.
   */
  FAIL_LINE_CONTESTED,
  /**
   * The message could not be handled successfully, the process was interrupted while attempting forwarding.
   */
  INTERRUPTED,
  /**
   * The message could not be handled successfully, the frames could not be interpreted as a message.
   * @deprecated handlers will not be responsible for parsing frames.
   */
  @Deprecated
  FAIL_FRAME_PARSING,
  /**
   * The message could not be handled successfully, it was left unhadled.
   * @deprecated a handler should not record an unhandled status, and should indicate a specific error status instead.
   */
  @Deprecated
  UNHANDLED
}
