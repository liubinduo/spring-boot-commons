package com.v1ok.commons.tree;

import java.io.Serializable;

/**
 * @author liubinduo
 * Create in 15:19 2017/11/12
 */
public interface IParentId<ID extends Serializable> {

  ID getParentId();
}
