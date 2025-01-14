package org.itech.ahb.config;

import java.io.IOException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Factory for interpreting YAML files as a property source for Spring Boot.
 */
@Slf4j
public class YamlPropertySourceFactory implements PropertySourceFactory {

  /**
   * Creates a property source from the given encoded resource.
   *
   * @param name the name of the property source
   * @param encodedResource the encoded resource
   * @return the property source
   * @throws IOException if an I/O error occurs accessing the file
   */
  @Override
  public @NonNull PropertySource<?> createPropertySource(
    @Nullable String name,
    @NonNull EncodedResource encodedResource
  ) throws IOException {
    YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
    factory.setResources(encodedResource.getResource());

    String filename = encodedResource.getResource().getFilename();
    if (filename == null) {
      filename = "";
      log.warn("could not get filename of a property source from encodedResource. Using blank string as name.");
    }

    Properties properties = factory.getObject();
    if (properties == null) {
      properties = new Properties();
      log.warn("encodedResource '" + filename + "'' is empty");
    }

    return new PropertiesPropertySource(filename != null ? filename : "", properties);
  }
}
