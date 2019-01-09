package com.v1ok.commons.tree;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author liubinduo
 * Create in 14:27 2017/11/12
 */
public class MemTreeData<E, ID extends Serializable> extends AbstractTreeData<E> implements
    ITreeData<E, ID> {

  private List<E> datas;

  public MemTreeData(List<E> datas) {
    this.datas = datas;
  }

  @Override
  public List<E> getAllChildren(ID id) {
    List<E> value = Lists.newLinkedList();
    List<E> children = getChildren(id);

    if (CollectionUtils.isNotEmpty(children)) {
      value.addAll(children);
      Iterator<E> iterator = children.iterator();
      while (iterator.hasNext()) {
        E e = iterator.next();
        Serializable pid = getPId(e);
        List<E> c = getAllChildren((ID) pid);
        if (CollectionUtils.isNotEmpty(c)) {
          value.addAll(c);
        }
      }

    }

    return value;
  }


  @Override
  public List<E> getChildren(ID id) {

    List<E> children = Lists.newLinkedList();
    Iterator<E> iterator = datas.iterator();
    while (iterator.hasNext()) {
      E e = iterator.next();

      Serializable parentId = getParentId(e);

      if (parentId.equals(id)) {
        children.add(e);
      }

    }
    datas.removeAll(children);

    return children;
  }

}
