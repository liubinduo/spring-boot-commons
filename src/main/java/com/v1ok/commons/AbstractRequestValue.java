package com.v1ok.commons;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractRequestValue<T> {


  public AbstractRequestValue() {

  }

  public Class<T> bodyClass() {

//    Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//    return tClass;
    return null;
  }

}
