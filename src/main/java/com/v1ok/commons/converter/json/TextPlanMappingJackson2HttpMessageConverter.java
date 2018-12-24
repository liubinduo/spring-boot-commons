package com.v1ok.commons.converter.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedList;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class TextPlanMappingJackson2HttpMessageConverter extends
    MappingJackson2HttpMessageConverter {

  public TextPlanMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
    super(objectMapper);
    LinkedList<MediaType> supportedMediaTypes = new LinkedList<>();
    supportedMediaTypes.add(MediaType.TEXT_PLAIN);
    supportedMediaTypes.add(new MediaType("application", "*+json"));

    this.setSupportedMediaTypes(supportedMediaTypes);
  }

}
