package com.v1ok.commons;

/**
 * Created by xinli on 05/04/2017.
 */
public interface IContext {

  boolean hasFunctionPermission(String objApi, FunctionCode code);

  IUserContext currentUser();


}
