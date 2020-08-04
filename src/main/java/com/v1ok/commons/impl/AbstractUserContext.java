package com.v1ok.commons.impl;

import com.v1ok.commons.IUserContext;
import java.util.List;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by xinli on 10/04/2017.
 */
@ToString
public abstract class AbstractUserContext implements IUserContext {


  @Override
  public List<String> getPositions() {
    return null;
  }

  @Override
  public String getUserId() {
    return null;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public boolean equals(Object obj) {

    if (obj instanceof AbstractUserContext) {
      return EqualsBuilder.reflectionEquals(this, obj);
    }
    return false;
  }

  @Override
  public List<String> getPermissions() {
    return null;
  }

  @Override
  public String getTenantId() {
    return null;
  }

}
