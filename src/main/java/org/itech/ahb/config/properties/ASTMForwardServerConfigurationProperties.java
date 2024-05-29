package org.itech.ahb.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "org.itech.ahb.forward-astm-server")
@Data
public class ASTMForwardServerConfigurationProperties {

  private String hostName = "localhost";
  private int port = 12001;
}
