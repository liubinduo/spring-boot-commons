package com.v1ok.commons.tree;

import java.io.Serializable;

/**
 * @Author:liubinduo
 * @Description:
 * @Date:Create in 15:36 2017/11/12
 */
public abstract class AbstractTreeData<E> {

  protected Serializable getParentId(E e) {
    if (e instanceof IParentId) {
      IParentId p = (IParentId) e;
      return p.getParentId();
    }
    //TODO 通过注解获取PARENT_ID

    throw new IllegalArgumentException("can't find parentId");

  }

  protected Serializable getPId(E e) {
    if (e instanceof IPid) {
      IPid p = (IPid) e;
      return p.getPid();
    }
    //TODO 通过注解获取PID
    throw new IllegalArgumentException("can't find pid");
  }
}
