package com.v1ok.commons.impl;

import com.forceclouds.context.FunctionCode;
import com.forceclouds.context.IContext;
import com.forceclouds.context.IUserContext;
import java.util.Map;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by liubinduo on 2017/5/16.
 */
@ToString
public class DefaultContext extends AbstractContext implements IContext {

  private Map<String, Long> functionPermission;

  public DefaultContext(IUserContext userContext) {
    super(userContext);
  }


  public DefaultContext(IUserContext userContext, Map<String, Long> functionPermission) {
    super(userContext);
    this.functionPermission = functionPermission;
  }


  @Override
  public boolean hasFunctionPermission(String objApi, FunctionCode functionCode) {
    if (functionPermission == null) {
      return true;
    }
    Long code = functionPermission.get(objApi);

    if (code == null) {
      return false;
    }

    return functionCode.hasCode(code);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public void setFunctionPermission(Map<String, Long> functionPermission) {
    this.functionPermission = functionPermission;
  }
}
