package org.itech.ahb.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "org.itech.ahb.listen-http-server")
@Data
public class HTTPListenServerConfigurationProperties {

	private int port = 8442;

}
