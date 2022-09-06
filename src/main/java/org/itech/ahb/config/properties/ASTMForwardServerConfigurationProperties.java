package org.itech.ahb.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "org.itech.ahb.forward-astm-server")
@Data
public class ASTMForwardServerConfigurationProperties {

	private String hostName = "localhost";
	private int port = 12001;

}
