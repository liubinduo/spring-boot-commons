package com.v1ok.commons.impl;

import com.v1ok.commons.IUserContext;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;

/**
 * Created by liubinduo on 2017/5/15.
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DefaultUserContext extends AbstractUserContext implements IUserContext {


  protected String userId;
  protected List<String> positions;
  protected List<String> permissions;
  protected String tenantId;

}
