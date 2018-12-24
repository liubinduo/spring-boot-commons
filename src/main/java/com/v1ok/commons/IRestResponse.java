package com.v1ok.commons;

import java.util.Set;

/**
 * Created by liubinduo on 2017/5/16.
 */
public interface IRestResponse<B> {

  String HEAD_KEY = "head";
  String BODY_KEY = "body";

  IRestResponse data(B data);

  Head getHead();

  IRestResponse<B> filterOutAllExcept(String id, String... propertyArray);

  IRestResponse<B> filterOutAllExcept(String id, Set<String> properties);

  IRestResponse<B> serializeAllExcept(String id, String... propertyArray);

  IRestResponse<B> serializeAllExcept(String id, Set<String> properties);

}
