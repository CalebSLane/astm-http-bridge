package org.itech.ahb.lib.common.handling;

public enum HandleStatus {
  SUCCESS,
  GENERIC_FAIL,
  FORWARD_FAIL_BAD_RESPONSE,
  FORWARD_FAIL_ERROR,
  FAIL_TOO_MANY_ATTEMPTS,
  FAIL_LINE_CONTESTED,
  INTERRUPTED,
  FAIL_FRAME_PARSING,
  @Deprecated
  UNHANDLED
}
