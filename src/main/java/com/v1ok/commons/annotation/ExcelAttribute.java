package com.v1ok.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Delong on 2017/11/10.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAttribute {

  /**
   * 是否必填
   */
  boolean required() default false;

  /**
   * 列索引 0，1，3....
   */
  int columnIndex();

  /**
   * 是否扩展字段
   */
  boolean isExt() default false;

}
