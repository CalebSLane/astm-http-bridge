package org.itech.ahb.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the ASTM server that this application should forward to when it receives
 * an ASTM message over HTTP(S). This is used to forward the ASTM messages to a server like an alayzer device that understands an  ASTM transmission protocol.
 */
@ConfigurationProperties(prefix = "org.itech.ahb.forward-astm-server")
@Data
public class ASTMForwardServerConfigurationProperties {

  /**
   * The hostname of the forward server.
   */
  private String hostName = "localhost";

  /**
   * The port on which the forward server listens.
   */
  private int port = 12001;
}
