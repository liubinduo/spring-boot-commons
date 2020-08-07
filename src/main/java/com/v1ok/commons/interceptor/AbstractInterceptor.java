package com.v1ok.commons.interceptor;

import com.v1ok.commons.Head;
import com.v1ok.commons.IUserContext;
import com.v1ok.commons.RequestValue;
import com.v1ok.commons.exception.AuthorityException;
import com.v1ok.commons.util.BeanUtil;
import com.v1ok.commons.util.TokenUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

@Slf4j
public abstract class AbstractInterceptor {

  @Value("${jwt.key}")
  protected String key;

  public AbstractInterceptor() {
  }


  protected <T> T getArg(JoinPoint pjp, Class<T> clazz) {
    Object[] args = pjp.getArgs();

    for (Object arg : args) {
      if (arg == null) {
        continue;
      }
      Class<? extends Object> argClass = arg.getClass();

      if (ClassUtils.isAssignable(argClass, clazz)) {
        return (T) arg;
      }
    }

    return null;
  }

  protected RequestValue<?> getRequestValue(JoinPoint point) {
    return this.getArg(point, RequestValue.class);
  }

  protected Head getHead(JoinPoint point) {
    return this.getArg(point, Head.class);
  }

  protected IUserContext getUserContext(RequestValue<?> requestValue) {

    if (requestValue == null)
      requestValue = new RequestValue<Objects>();

    Head head = requestValue.getHead();

    String token = TokenUtil.getToken(head);

    if (StringUtils.isEmpty(token)) {
      throw new AuthorityException();
    }

    IUserContext userContext = TokenUtil.parseToken(token, key);

    if (userContext == null) {
      throw new AuthorityException();
    }

    head.setTenantId(userContext.getTenantId());

    return userContext;
  }

  protected String getRequestMethod() {
    HttpServletRequest request = ((ServletRequestAttributes) Objects
        .requireNonNull(RequestContextHolder
            .getRequestAttributes())).getRequest();
    return request.getMethod();
  }

  protected boolean isGet() {
    return "GET".equals(getRequestMethod());
  }

  protected boolean isDelete() {
    return "DELETE".equals(getRequestMethod());
  }

}
