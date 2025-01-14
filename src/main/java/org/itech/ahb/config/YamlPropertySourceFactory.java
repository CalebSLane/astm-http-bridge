package org.itech.ahb.config;

import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * Factory for interpreting YAML files as a property source for Spring Boot.
 */
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
  public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
    YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
    factory.setResources(encodedResource.getResource());

    Properties properties = factory.getObject();

    return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
  }
}
