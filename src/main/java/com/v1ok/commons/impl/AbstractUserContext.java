package com.v1ok.commons.impl;

import com.forceclouds.context.IUserContext;
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
  public List<Long> getTerritories() {
    return null;
  }

  @Override
  public Long getUserId() {
    return null;
  }

  @Override
  public List<Long> getRoles() {
    return null;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public List<String> getPermissions() {
    return null;
  }
}
