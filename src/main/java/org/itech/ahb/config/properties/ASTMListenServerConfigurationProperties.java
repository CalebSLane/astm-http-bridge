package org.itech.ahb.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "org.itech.ahb.listen-astm-server")
@Data
public class ASTMListenServerConfigurationProperties {

  private int port = 12001;
}
