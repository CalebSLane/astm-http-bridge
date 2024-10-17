package org.itech.ahb;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.config.YamlPropertySourceFactory;
import org.itech.ahb.config.properties.ASTME138195ListenServerConfigurationProperties;
import org.itech.ahb.config.properties.ASTMLIS1AListenServerConfigurationProperties;
import org.itech.ahb.config.properties.HTTPForwardServerConfigurationProperties;
import org.itech.ahb.lib.astm.handling.ASTMHandler;
import org.itech.ahb.lib.astm.handling.ASTMHandlerMarshaller;
import org.itech.ahb.lib.astm.handling.ASTMHandlerMarshaller.MarshallerMode;
import org.itech.ahb.lib.astm.handling.DefaultForwardingASTMToHTTPHandler;
import org.itech.ahb.lib.astm.interpretation.ASTMInterpreterFactory;
import org.itech.ahb.lib.astm.interpretation.DefaultASTMInterpreterFactory;
import org.itech.ahb.lib.astm.servlet.ASTMServlet;
import org.itech.ahb.lib.astm.servlet.ASTMServlet.ASTMVersion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAsync
@PropertySource(
  value = { "file:/app/configuration.yml", "classpath:application.yml" },
  ignoreResourceNotFound = true,
  factory = YamlPropertySourceFactory.class
)
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
      astmHandlers = Arrays.asList(
        new DefaultForwardingASTMToHTTPHandler(
          httpForwardConfig.getUri(),
          httpForwardConfig.getUsername(),
          httpForwardConfig.getPassword()
        )
      );
    } else {
      astmHandlers = Arrays.asList(new DefaultForwardingASTMToHTTPHandler(httpForwardConfig.getUri()));
    }
    return new ASTMHandlerMarshaller(astmHandlers, MarshallerMode.FIRST);
  }

  @Bean
  public ASTMServlet astmLIS01AServlet(
    ASTMLIS1AListenServerConfigurationProperties astmListenConfig,
    HTTPForwardServerConfigurationProperties httpForwardConfig
  ) {
    log.info("creating astm server bean to handle incoming astm LIS1-A requests on port " + astmListenConfig.getPort());
    return new ASTMServlet(
      astmHandlerMarshaller(httpForwardConfig),
      astmInterpreterFactory(),
      astmListenConfig.getPort(),
      ASTMVersion.LIS01_A
    );
  }

  @Bean
  public ASTMServlet astmE138195Servlet(
    ASTME138195ListenServerConfigurationProperties astmListenConfig,
    HTTPForwardServerConfigurationProperties httpForwardConfig
  ) {
    log.info(
      "creating astm 1381-95 server bean to handle incoming astm 1381-95 requests on port " + astmListenConfig.getPort()
    );
    return new ASTMServlet(
      astmHandlerMarshaller(httpForwardConfig),
      astmInterpreterFactory(),
      astmListenConfig.getPort(),
      ASTMVersion.E1381_95
    );
  }
}
