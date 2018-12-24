package com.v1ok.commons.util;


/**
 * Created by Zhang on 2017/3/17.
 */
public final class ThreadLocaUtiles {

  private static ThreadLocal<ThreadValues> connectionHolder = new ThreadLocal<ThreadValues>() {
    @Override
    protected ThreadValues initialValue() {
      ThreadValues threadValues = new ThreadValues();
      return threadValues;
    }
  };

  public static void setValue(ThreadValues map) {
    connectionHolder.set(map);
  }


  public static ThreadValues getValue() {
    ThreadValues threadValues = connectionHolder.get();
    return threadValues;
  }


}
