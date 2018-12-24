package com.v1ok.commons;

import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@ToString
public class Head {

  private int code;

  private String msg;

  @NotBlank(message = "token不能为空")
  private String token;

  private String tenantId;

  public Head() {

  }

  public Head(final int code, final String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }


}
