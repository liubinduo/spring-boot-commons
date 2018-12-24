package com.v1ok.commons.transaction;

import com.forceclouds.context.transaction.annotation.BeginTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by liubinduo on 2017/5/22.
 */
@Component
public class TransactionalHolderUtil {

  private static ThreadLocal<TransactionStatus> local = new ThreadLocal<>();

  @Autowired(required = false)
  protected AbstractTenantTransactionManagerHolder transactionManagerHolder;

  public void start(BeginTransactional beginTransactional) {

    PlatformTransactionManager transactionManager = transactionManagerHolder
        .getTransactionManager();

    if (transactionManager != null) {

      DefaultTransactionDefinition definition = new DefaultTransactionDefinition();

      definition.setIsolationLevel(beginTransactional.isolation().value());
      definition.setReadOnly(beginTransactional.readOnly());
      definition.setPropagationBehavior(beginTransactional.propagation().value());
      definition.setTimeout(beginTransactional.timeout());

      TransactionStatus transactionStatus = transactionManager.getTransaction(definition);

      this.setTransactionStatus(transactionStatus);
    }

  }


  public void commit() {

    PlatformTransactionManager transactionManager = transactionManagerHolder
        .getTransactionManager();
    if (transactionManager == null) {
      return;
    }

    TransactionStatus transactionStatus = this.getTransactionStatus();

    if (transactionStatus == null) {
      return;
    }

    transactionManager.commit(transactionStatus);
    setTransactionStatus(null);
  }

  public void rollback() {
    PlatformTransactionManager transactionManager = transactionManagerHolder
        .getTransactionManager();
    if (transactionManager == null) {
      return;
    }

    TransactionStatus transactionStatus = this.getTransactionStatus();

    if (transactionStatus == null) {
      return;
    }

    transactionManager.rollback(transactionStatus);
  }

  private void setTransactionStatus(TransactionStatus transactionStatus) {
    local.set(transactionStatus);
  }

  private TransactionStatus getTransactionStatus() {
    return local.get();
  }

}
