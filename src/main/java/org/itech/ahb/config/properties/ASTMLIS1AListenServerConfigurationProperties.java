package org.itech.ahb.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

//LIS1A is equivalent to E1381-02
@ConfigurationProperties(prefix = "org.itech.ahb.listen-astm-server")
@Data
public class ASTMLIS1AListenServerConfigurationProperties {

  private int port = 12001;
}
