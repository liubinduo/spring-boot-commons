package com.v1ok.commons.util.support;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

public class DateEditor extends PropertyEditorSupport {

  private final static String[] PATTERN = new String[]{"yyyy-MM-dd hh:mm:ss",
      "yyyy-MM-dd'T'HH:mm:ssXX", "yyyy-MM-dd'T'HH:mm:ss.SSSX"};

  @Override
  public void setValue(Object value) {
    if (value instanceof java.sql.Date) {
      super.setValue(new Date(((java.sql.Date) value).getTime()));
    } else if (value instanceof String) {
      super.setValue(this.parse((String) value));
    } else if (value instanceof Long) {
      super.setValue(new Date((Long) value));
    } else if (value instanceof Calendar) {
      super.setValue(((Calendar) value).getTime());
    }

  }


  @Override
  public void setAsText(String text) throws IllegalArgumentException {

    if (StringUtils.isBlank(text)) {
      super.setValue(null);
      return;
    }
    super.setValue(parse(text));
  }

  @Override
  public String getAsText() {
    Object value = this.getValue();

    if (value == null) {
      return null;
    }

    return DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format((Date) value);
  }

  private Date parse(String dateStr) {

    for (String parStr : PATTERN) {

      try {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance(parStr);
        return fastDateFormat.parse(dateStr);

      } catch (ParseException ignored) {
      }
    }

    try {
      return DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.parse(dateStr);
    } catch (ParseException e) {
    }

    return null;
  }


}
