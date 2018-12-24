package com.v1ok.commons.impl;

import com.forceclouds.context.IContext;
import com.forceclouds.context.IUserContext;

/**
 * Created by liubinduo on 2017/5/16.
 */
public abstract class AbstractContext implements IContext {

  protected IUserContext userContext;

  public AbstractContext(IUserContext userContext) {
    this.userContext = userContext;
  }

  @Override
  public IUserContext currentUser() {
    return userContext;
  }
}
