package com.v1ok.commons.interceptor;

import com.v1ok.commons.ContextHolder;
import com.v1ok.commons.Head;
import com.v1ok.commons.HeadCode;
import com.v1ok.commons.IRestResponse;
import com.v1ok.commons.IUserContext;
import com.v1ok.commons.RequestValue;
import com.v1ok.commons.annotation.AuthorityRequired;
import com.v1ok.commons.impl.DefaultContext;
import com.v1ok.commons.impl.RestResponse;
import com.v1ok.commons.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoginInterceptor extends AbstractInterceptor {


  @Around("@annotation(com.v1ok.commons.annotation.AuthorityRequired) && @annotation(authority)")
  public Object Interceptor(ProceedingJoinPoint point, AuthorityRequired authority) {

    RequestValue<?> value = getRequestValue(point);

    IUserContext userContext = getUserContext(value);

    Head head = this.getHead(point);
    if ((isGet() || isDelete()) && head != null) {
      head.setTenantId(userContext.getTenantId());
    }

    // 权限判断
    if (StringUtils.isNotEmpty(authority.permissionCode())
        && !userContext.getPermissions().contains(authority.permissionCode())) {

      return RestResponse.builder().error(HeadCode.NO_PERMISSION);

    }

    DefaultContext context = new DefaultContext(userContext);
    ContextHolder.getHolder().set(context);

    try {

      Object proceed = point.proceed();

      if (proceed instanceof IRestResponse) {
        IRestResponse<?> restResponse = (IRestResponse<?>) proceed;
        if (restResponse.getHead().getCode() == 200) {
          String token = TokenUtil.createToken(context, key);
          restResponse.getHead().setToken(token);
        }
      }

      return proceed;

    } catch (Throwable throwable) {
      throw new RuntimeException(throwable);
    } finally {
      ContextHolder.getHolder().remove();
    }
  }

}
