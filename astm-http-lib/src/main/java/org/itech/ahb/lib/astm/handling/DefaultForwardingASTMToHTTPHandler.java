package org.itech.ahb.lib.astm.handling;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.itech.ahb.lib.astm.concept.ASTMMessage;
import org.itech.ahb.lib.astm.concept.DefaultASTMMessage;
import org.itech.ahb.lib.common.handling.HandleStatus;
import org.itech.ahb.lib.util.LogUtil;

@Slf4j
public class DefaultForwardingASTMToHTTPHandler implements ASTMHandler {

  private final URI forwardingUri;
  private final String username;
  private final char[] password;

  public DefaultForwardingASTMToHTTPHandler(URI forwardingUri) {
    this.forwardingUri = forwardingUri;
    this.username = null;
    this.password = new char[0];
  }

  public DefaultForwardingASTMToHTTPHandler(URI forwardingUri, String username, char[] password) {
    this.forwardingUri = forwardingUri;
    this.username = username;
    this.password = password;
  }

  /**
   * @param message
   * @return ASTMHandlerResponse
   */
  @Override
  public ASTMHandlerResponse handle(ASTMMessage message) {
    HttpClient client = HttpClient.newHttpClient();
    log.debug("creating request to forward to http server at " + forwardingUri.toString());
    Builder requestBuilder = HttpRequest.newBuilder() //
      .uri(forwardingUri) //
      .POST(HttpRequest.BodyPublishers.ofString(message.getMessage())); //
    if (!(username == null || username.equals(""))) {
      log.debug("using username '" + username + "' to forward to http server at " + forwardingUri.toString());
      requestBuilder.header(
        "Authorization",
        "Basic " + Base64.getEncoder().encodeToString((username + ":" + new String(password)).getBytes())
      );
    }
    HttpRequest request = requestBuilder.build();

    try {
      log.debug("forwarding request to http server at " + forwardingUri.toString());
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      log.debug("received " + response.statusCode() + " response from http server at " + forwardingUri.toString());
      log.trace("response: " + LogUtil.convertForDisplay(response.body()));
      if (response.statusCode() == 200) {
        return new ASTMHandlerResponse(response.body(), HandleStatus.SUCCESS, false, this);
      }
      return new ASTMHandlerResponse(response.body(), HandleStatus.FORWARD_FAIL_BAD_RESPONSE, false, this);
    } catch (IOException | InterruptedException e) {
      log.error("error occurred communicating with http server at " + forwardingUri.toString(), e);
      return new ASTMHandlerResponse("", HandleStatus.FORWARD_FAIL_ERROR, false, this);
    }
  }

  /**
   * @param message
   * @return boolean
   */
  @Override
  public boolean matches(ASTMMessage message) {
    return message instanceof DefaultASTMMessage;
  }

  /**
   * @param message
   * @return boolean
   */
  @Override
  public String getName() {
    return "Forwarding ASTM to HTTP Handler";
  }
}
