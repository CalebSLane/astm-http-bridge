package org.itech.ahb.health;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.config.properties.HTTPForwardServerConfigurationProperties;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("httpforward")
@ConditionalOnEnabledHealthIndicator("httpforward")
@Slf4j
public class HTTPForwardServerHealthIndicator implements HealthIndicator {

  private final HTTPForwardServerConfigurationProperties properties;

  public HTTPForwardServerHealthIndicator(HTTPForwardServerConfigurationProperties properties) {
    this.properties = properties;
  }

  @Override
  public Health health() {
    if (properties.getHealthUri() == null) {
      return Health.unknown().build();
    }
    HttpClient client = HttpClient.newHttpClient();
    log.debug("creating request to test forward http server at " + properties.getHealthUri().toString());
    Builder requestBuilder = HttpRequest.newBuilder()
      .method(properties.getHealthMethod().toString(), HttpRequest.BodyPublishers.ofString(properties.getHealthBody()))
      .uri(properties.getHealthUri());
    if (!(properties.getUsername() == null || properties.getUsername().equals(""))) {
      log.debug(
        "using username '" +
        properties.getUsername() +
        "' to test forward http server at " +
        properties.getUri().toString().toString()
      );
      requestBuilder.header(
        "Authorization",
        "Basic " +
        Base64.getEncoder()
          .encodeToString((properties.getUsername() + ":" + new String(properties.getPassword())).getBytes())
      );
    }
    HttpRequest request = requestBuilder.build();

    try {
      log.debug("testing forward http server at " + properties.getHealthUri().toString());
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() == 200) {
        log.debug("testing forward http server at " + properties.getHealthUri().toString() + " success");
        return Health.up().build();
      }
    } catch (IOException | InterruptedException e) {
      log.debug("error occurred communicating with http server at " + properties.getHealthUri().toString(), e);
    }
    log.debug("testing forward http server at " + properties.getHealthUri().toString() + " failure");
    return Health.down().build();
  }
}
