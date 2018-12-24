package com.v1ok.commons.tree;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:liubinduo
 * @Description:
 * @Date: 14:12 2017/11/12
 */
public interface ITreeData<E, ID extends Serializable> {

  /**
   * @Author:liubinduo
   * @Description: 获取所有子结点
   * @Date: 14:24 2017/11/12
   */
  List<E> getAllChildren(ID id);

  /**
   * @Author:liubinduo
   * @Description:获取直属子结点
   * @Date: 14:24 2017/11/12
   */
  List<E> getChildren(ID id);

}
