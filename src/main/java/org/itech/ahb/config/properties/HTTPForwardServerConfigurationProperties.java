package org.itech.ahb.config.properties;

import java.net.URI;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMethod;

@ConfigurationProperties(prefix = "org.itech.ahb.forward-http-server")
@Data
public class HTTPForwardServerConfigurationProperties {

  private URI uri = URI.create("https://localhost:8443");
  private String username;
  private char[] password;
  private URI healthUri;
  private RequestMethod healthMethod = RequestMethod.GET;
  private String healthBody = "";
}
