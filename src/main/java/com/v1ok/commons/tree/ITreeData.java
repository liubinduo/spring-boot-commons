package com.v1ok.commons.tree;

import java.io.Serializable;
import java.util.List;

/**
 * @author liubinduo Create in 14:12 2017/11/12
 */
public interface ITreeData<E, ID extends Serializable> {

  /**
   * 获取所有子结点
   *
   * @param id id
   * @return 子集
   */
  List<E> getAllChildren(ID id);

  /**
   * 获取直属子结点
   *
   * @param id id
   * @return 子集
   */
  List<E> getChildren(ID id);

}
