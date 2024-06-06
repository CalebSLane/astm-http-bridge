package org.itech.ahb.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "org.itech.ahb.listen-astm-server.e1381-95")
@Data
public class ASTME138195ListenServerConfigurationProperties {

  private int port = 12011;
}
