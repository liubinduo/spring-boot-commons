package com.v1ok.commons.http.converter;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 * StringToDate
 *
 * @author liubinduo
 * @date 2021/11/1 11:41 上午
 * @since 1.0.0
 */
public class StringToDate implements Formatter<Date> {

  StdDateFormat stdDateFormat = StdDateFormat.instance;

  @Override
  public Date parse(String text, Locale locale) throws ParseException {
    return stdDateFormat.withLocale(locale).parse(text);
  }

  @Override
  public String print(Date object, Locale locale) {
    return stdDateFormat.withLocale(locale).format(object);
  }
}
