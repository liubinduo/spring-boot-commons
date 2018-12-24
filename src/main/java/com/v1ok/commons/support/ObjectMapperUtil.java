package com.v1ok.commons.support;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ObjectMapperUtil {

  private static final String DEFAULT_DATE_FORMATS = "yyyy-MM-dd HH:mm";
  private static ObjectMapper OM = null;

  private static final Lock LOCK = new ReentrantLock(true);

  public static ObjectMapper getSingleton() {
    return getSingleton(DEFAULT_DATE_FORMATS);
  }

  public static ObjectMapper getSingleton(final String DATE_FORMATS) {
    if (OM == null) {
      LOCK.lock();
      try {
        if (OM == null) {
          OM = getInstens(DATE_FORMATS);
        }
      } finally {
        LOCK.unlock();
      }
    }

    return OM;
  }

  public static ObjectMapper getInstens() {
    return getInstens(DEFAULT_DATE_FORMATS);
  }

  public static ObjectMapper getInstens(String DATE_FORMATS) {
    ObjectMapper om = new ObjectMapper();
    om.setSerializationInclusion(Include.NON_NULL);
    om.setSerializationInclusion(Include.NON_EMPTY);
//        om.setDateFormat(new SimpleDateFormat(DATE_FORMATS));
//        om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    om.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    return om;
  }
}
