package com.v1ok.commons.interceptor;

import org.apache.commons.lang3.ClassUtils;
import org.aspectj.lang.JoinPoint;

public abstract class AbstractInterceptor {

  public AbstractInterceptor() {
    super();
  }


  protected <T> T getArg(JoinPoint pjp, Class<T> clazz) {
    Object[] args = pjp.getArgs();

    for (Object arg : args) {
      if (arg == null) {
        continue;
      }
      Class<? extends Object> argClass = arg.getClass();

      if (ClassUtils.isAssignable(argClass, clazz)) {
        return (T) arg;
      }
    }

    return null;
  }

}