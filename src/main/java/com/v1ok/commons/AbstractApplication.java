package com.v1ok.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.v1ok.commons.converter.json.TextPlanMappingJackson2HttpMessageConverter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by liubinduo on 2017/6/29.
 */
public abstract class AbstractApplication implements WebMvcConfigurer {


  @Autowired
  ObjectMapper objectMapper;

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    TextPlanMappingJackson2HttpMessageConverter converter = new TextPlanMappingJackson2HttpMessageConverter(
        objectMapper);

    converters.add(converter);

  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    CorsRegistration corsRegistration = registry.addMapping("/**");

    corsRegistration.allowedOrigins("*");
    corsRegistration.allowedHeaders("*");
    corsRegistration.allowedMethods("OPTIONS", "POST", "GET", "PUT", "DELETE");
    corsRegistration.allowCredentials(true).maxAge(3600);
  }

}
