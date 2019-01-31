package com.v1ok.commons.interceptor;

import com.v1ok.commons.ContextHolder;
import com.v1ok.commons.HeadCode;
import com.v1ok.commons.IRestResponse;
import com.v1ok.commons.IUserContext;
import com.v1ok.commons.RequestValue;
import com.v1ok.commons.annotation.AuthorityRequired;
import com.v1ok.commons.exception.AuthorityException;
import com.v1ok.commons.impl.DefaultContext;
import com.v1ok.commons.impl.RestResponse;
import com.v1ok.commons.util.TokenUtil;
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

  @Around("@annotation(com.v1ok.commons.annotation.AuthorityRequired) && @annotation(log)")
  public Object Interceptor(ProceedingJoinPoint pjp, AuthorityRequired log) {

    RequestValue<?> value = getArg(pjp, RequestValue.class);

    if (value == null) {
      value = new RequestValue<>();
    }

    String token = TokenUtil.getToken(value.getHead());

    if (StringUtils.isEmpty(token)) {
      throw new AuthorityException();
    }

    IUserContext userContext = TokenUtil.parseToken(token, key);

    if (userContext == null) {
      throw new AuthorityException();
    }

    // 权限判断
    if (StringUtils.isNotEmpty(log.permissionCode())
        && !userContext.getPermissions().contains(log.permissionCode())) {

      IRestResponse resp = RestResponse.builder().error(HeadCode.FORBIDDEN);

      resp.getHead().setMsg("当前用户没有此操作权限");

      return resp;

    }

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
