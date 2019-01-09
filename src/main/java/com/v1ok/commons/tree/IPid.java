package com.v1ok.commons.tree;

import java.io.Serializable;

/**
 * @author liubinduo
 * Create in 15:26 2017/11/12
 */
public interface IPid<ID extends Serializable> {

  ID getPid();
}
