package com.v1ok.commons.error;

import com.v1ok.commons.HeadCode;
import com.v1ok.commons.IRestResponse;
import com.v1ok.commons.exception.AuthorityException;
import com.v1ok.commons.impl.RestResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(AuthorityException.class)
  public IRestResponse<?> error(AuthorityException exception) {
    log.error("需要登录校验没通过。", exception);
    return RestResponse.builder().error(HeadCode.UN_AUTHORIZED);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST) //设置状态码为 400
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public IRestResponse<?> paramExceptionHandler(MethodArgumentNotValidException exception) {
    BindingResult exceptions = exception.getBindingResult();
    // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
    RestResponse<Object> response = RestResponse.builder().error(HeadCode.BAD_REQUEST);
    if (exceptions.hasErrors()) {
      List<ObjectError> errors = exceptions.getAllErrors();
      if (!errors.isEmpty()) {
        // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
        FieldError fieldError = (FieldError) errors.get(0);
        return response.message(fieldError.getField() + ":" + fieldError.getDefaultMessage());
      }
    }
    return response;
  }

}
