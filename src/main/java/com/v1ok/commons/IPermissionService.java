package com.v1ok.commons;

import java.util.Map;

/**
 * Created by liubinduo on 2017/6/9.
 */
public interface IPermissionService {


  Map<String, Long> functionPermission(Long userId);

}
