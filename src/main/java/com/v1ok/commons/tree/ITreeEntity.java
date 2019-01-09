package com.v1ok.commons.tree;

import java.io.Serializable;

/**
 * @author liubinduo
 * Create in 15:27 2017/11/12
 */
public interface ITreeEntity<ID extends Serializable> extends IParentId<ID>, IPid<ID> {

}
