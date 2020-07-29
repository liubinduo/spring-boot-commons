package com.v1ok.commons;

/**
 * Created by liubinduo on 2017/6/12.
 */
public enum FunctionCode {

  CREATE(2L), EDIT(4L), READE(8L), REMOVE(16L), LIST(32L), IMPORT(64L), EXPORT(128L);

  private final long code;

  FunctionCode(long code) {
    this.code = code;
  }

  public boolean hasCode(Long code) {
    if (code == null) {
      return false;
    }
    return (code & this.code) == this.code;
  }

  public static Long all() {
    FunctionCode[] values = FunctionCode.values();

    Long value = 0L;

    for (FunctionCode code :
        values) {
      value |= code.code;
    }

    return value;
  }

}
