package com.v1ok.commons.support;

public class ResultObject<T> {

  private boolean result;

  private String errorMsg;

  private T date;

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public T getDate() {
    return date;
  }

  public void setDate(T date) {
    this.date = date;
  }
}
