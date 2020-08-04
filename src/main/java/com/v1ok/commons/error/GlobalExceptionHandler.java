package com.v1ok.commons.error;

import com.v1ok.commons.HeadCode;
import com.v1ok.commons.IRestResponse;
import com.v1ok.commons.exception.AuthorityException;
import com.v1ok.commons.impl.RestResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AuthorityException.class)
  public IRestResponse<?> error(HttpServletRequest request, HttpServletResponse response) {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    return RestResponse.builder().error(HeadCode.UN_AUTHORIZED);
  }

}
