package com.v1ok.commons.tree;

import java.io.Serializable;

/**
 * @Author:liubinduo
 * @Description:
 * @Date:Create in 15:19 2017/11/12
 */
public interface IParentId<ID extends Serializable> {

  ID getParentId();
}
