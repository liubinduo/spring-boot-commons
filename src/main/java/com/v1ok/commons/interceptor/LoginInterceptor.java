package com.v1ok.commons.interceptor;

import com.forceclouds.context.ContextHolder;
import com.forceclouds.context.Head;
import com.forceclouds.context.HeadCode;
import com.forceclouds.context.IRestResponse;
import com.forceclouds.context.IUserContext;
import com.forceclouds.context.RequestValue;
import com.forceclouds.context.annotation.AuthorityRequired;
import com.forceclouds.context.impl.DefaultContext;
import com.forceclouds.context.impl.RestResponse;
import com.forceclouds.context.util.TokenUtil;
import com.google.common.collect.Sets;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoginInterceptor extends AbstractInterceptor {

  private static final String TOKEN = "token";

  @Value("${jwt.key}")
  private String key;

  /**
   *
   */
  @Autowired(required = false)
  private HttpServletRequest request;

  @Around("@annotation(com.forceclouds.context.annotation.AuthorityRequired) && @annotation(log)")
  public Object Interceptor(ProceedingJoinPoint pjp, AuthorityRequired log) {

    RequestValue<?> value;

    String method = request.getMethod();
    String httpHeader = request.getHeader("Content-Type");

    if (Sets.newHashSet("GET", "DELETE").contains(method)) {
      value = new RequestValue<>();
    } else if (StringUtils.isBlank(httpHeader) || httpHeader.startsWith("multipart/form-data")) {
      value = new RequestValue<>();
    } else {
      value = getArg(pjp, RequestValue.class);
    }

    if (value == null) {
      throw new RuntimeException("method of ctrl is error!");
    }

    Head head = value.getHead();

    String token = TokenUtil.getToken(head, request);

    if (StringUtils.isEmpty(token)) {
      IRestResponse resp = RestResponse.builder().error(HeadCode.UN_AUTHORIZED);

      resp.getHead().setMsg("TOKEN不能为空！");

      return resp;
    }

    IUserContext userContext = TokenUtil.parseToken(token, key);

    if (userContext == null) {
      IRestResponse resp = RestResponse.builder().error(HeadCode.UN_AUTHORIZED);

      resp.getHead().setMsg("身份失效");

      return resp;
    }

    // 权限判断
    if (StringUtils.isNotEmpty(log.permissionCode())
        && !userContext.getPermissions().contains(log.permissionCode())) {

      IRestResponse resp = RestResponse.builder().error(HeadCode.FORBIDDEN);

      resp.getHead().setMsg("当前用户没有此操作权限");

      return resp;

    }

    head.setToken(token);
    value.setHead(head);

    DefaultContext context = new DefaultContext(userContext);
    ContextHolder.getHolder().set(context);

    try {
      Object proceed = pjp.proceed();

      if (proceed instanceof IRestResponse) {
        IRestResponse restResponse = (IRestResponse) proceed;
        if (restResponse.getHead().getCode() == 200) {
          token = TokenUtil.createToken(context, key);
          restResponse.getHead().setToken(token);
        }
      }

      return proceed;
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable);
    }
  }


}
