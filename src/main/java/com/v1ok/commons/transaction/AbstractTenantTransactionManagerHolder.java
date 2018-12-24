package com.v1ok.commons.transaction;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created by liubinduo on 2017/5/17.
 */
public abstract class AbstractTenantTransactionManagerHolder {

  public abstract PlatformTransactionManager getTransactionManager();

}
