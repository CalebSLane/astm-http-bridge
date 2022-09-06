package org.itech.ahb.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "org.itech.ahb.listen-astm-server")
@Data
public class ASTMListenServerConfigurationProperties {

	private int port = 12001;

}
