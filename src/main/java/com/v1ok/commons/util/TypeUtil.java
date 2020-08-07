package com.v1ok.commons.util;

public class TypeUtil {

  /**
   * 判断object是否为基本类型
   */
  public static boolean isBaseType(Object object) {
    Class<?> className = object.getClass();
    if (className.equals(java.lang.Integer.class) ||
        className.equals(java.lang.Byte.class) ||
        className.equals(java.lang.Long.class) ||
        className.equals(java.lang.Double.class) ||
        className.equals(java.lang.Float.class) ||
        className.equals(java.lang.Character.class) ||
        className.equals(java.lang.Short.class) ||
        className.equals(java.lang.Boolean.class)) {
      return true;
    }
    return false;
  }

  /**
   * 判断是否为基本类型的默认值
   */
  public static boolean isBaseDefaultValue(Object object) {
    Class<?> className = object.getClass();
    if (className.equals(java.lang.Integer.class)) {
      return (int) object == 0;
    } else if (className.equals(java.lang.Byte.class)) {
      return (byte) object == 0;
    } else if (className.equals(java.lang.Long.class)) {
      return (long) object == 0L;
    } else if (className.equals(java.lang.Double.class)) {
      return (double) object == 0.0d;
    } else if (className.equals(java.lang.Float.class)) {
      return (float) object == 0.0f;
    } else if (className.equals(java.lang.Character.class)) {
      return (char) object == '\u0000';
    } else if (className.equals(java.lang.Short.class)) {
      return (short) object == 0;
    } else if (className.equals(java.lang.Boolean.class)) {
      return !((boolean) object);
    }
    return false;
  }

}
