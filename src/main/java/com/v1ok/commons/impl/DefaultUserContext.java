package com.v1ok.commons.impl;

import com.v1ok.commons.IUserContext;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by liubinduo on 2017/5/15.
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultUserContext extends AbstractUserContext implements IUserContext {


  protected String userId;
  protected List<String> territories;
  protected List<String> roles;
  protected List<String> permissions;

  @Override
  public List<String> getTerritories() {
    return this.territories;
  }

  @Override
  public String getUserId() {
    return this.userId;
  }

  @Override
  public List<String> getRoles() {
    return this.roles;
  }

  @Override
  public List<String> getPermissions() {
    return this.permissions;
  }
}
