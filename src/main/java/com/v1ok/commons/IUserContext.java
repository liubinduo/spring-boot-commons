package com.v1ok.commons;

import java.util.List;

/**
 * Created by xinli on 05/04/2017.
 */
public interface IUserContext {

  List<String> getPositions();

  String getUserId();

  String getTenantId();

  List<String> getPermissions();
}
