package org.itech.ahb;

import java.util.Arrays;
import java.util.List;

import org.itech.ahb.config.YamlPropertySourceFactory;
import org.itech.ahb.config.properties.ASTMListenServerConfigurationProperties;
import org.itech.ahb.config.properties.HTTPForwardServerConfigurationProperties;
import org.itech.ahb.lib.astm.servlet.ASTMHandler;
import org.itech.ahb.lib.astm.servlet.ASTMHandlerMarshaller;
import org.itech.ahb.lib.astm.servlet.ASTMServlet;
import org.itech.ahb.lib.astm.servlet.DefaultForwardingASTMToHTTPHandler;
import org.itech.ahb.lib.common.ASTMInterpreterFactory;
import org.itech.ahb.lib.common.DefaultASTMInterpreterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAsync
@PropertySource(value = { "file:/app/configuration.yml",
		"classpath:application.yml" }, ignoreResourceNotFound = true, factory = YamlPropertySourceFactory.class)
@Slf4j
public class AstmHttpBridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AstmHttpBridgeApplication.class, args);
	}

	@Bean
	public ASTMInterpreterFactory astmInterpreterFactory() {
		return new DefaultASTMInterpreterFactory();
	}

	@Bean
	public ASTMHandlerMarshaller astmHandlerMarshaller(HTTPForwardServerConfigurationProperties httpForwardConfig) {
		List<ASTMHandler> astmHandlers;
		if (StringUtils.hasText(httpForwardConfig.getUsername())) {
			astmHandlers = Arrays.asList(new DefaultForwardingASTMToHTTPHandler(httpForwardConfig.getUri(),
					httpForwardConfig.getUsername(), httpForwardConfig.getPassword()));
		} else {
			astmHandlers = Arrays.asList(new DefaultForwardingASTMToHTTPHandler(httpForwardConfig.getUri()));
		}
		return new ASTMHandlerMarshaller(astmHandlers);
	}

	@Bean
	public ASTMServlet astmServlet(ASTMListenServerConfigurationProperties astmListenConfig,
			HTTPForwardServerConfigurationProperties httpForwardConfig) {
		log.info("creating astm server bean to handle incoming astm requests on port " + astmListenConfig.getPort());
		return new ASTMServlet(astmHandlerMarshaller(httpForwardConfig), astmInterpreterFactory(),
				astmListenConfig.getPort());
	}

}
