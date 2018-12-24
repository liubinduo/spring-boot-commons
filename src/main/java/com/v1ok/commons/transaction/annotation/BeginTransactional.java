package com.v1ok.commons.transaction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

/**
 * Created by liubinduo on 2017/5/22.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeginTransactional {

  /**
   * The transaction propagation type.
   * <p>Defaults to {@link Propagation#REQUIRED}.
   *
   * @see org.springframework.transaction.interceptor.TransactionAttribute#getPropagationBehavior()
   */
  Propagation propagation() default Propagation.REQUIRED;

  /**
   * The transaction isolation level.
   * <p>Defaults to {@link Isolation#DEFAULT}.
   *
   * @see org.springframework.transaction.interceptor.TransactionAttribute#getIsolationLevel()
   */
  Isolation isolation() default Isolation.DEFAULT;

  /**
   * The timeout for this transaction.
   * <p>Defaults to the default timeout of the underlying transaction system.
   *
   * @see org.springframework.transaction.interceptor.TransactionAttribute#getTimeout()
   */
  int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

  /**
   * {@code true} if the transaction is read-only.
   * <p>Defaults to {@code false}.
   * <p>This just serves as a hint for the actual transaction subsystem;
   * it will <i>not necessarily</i> cause failure of write access attempts. A transaction manager
   * which cannot interpret the read-only hint will
   * <i>not</i> throw an exception when asked for a read-only transaction
   * but rather silently ignore the hint.
   *
   * @see org.springframework.transaction.interceptor.TransactionAttribute#isReadOnly()
   */
  boolean readOnly() default false;


}
