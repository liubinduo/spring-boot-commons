package com.v1ok.commons.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrossDomainFilter implements Filter {

  private static final Logger LOG = LoggerFactory.getLogger(CrossDomainFilter.class);

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    LOG.info("添加跨域请求消息头");

    HttpServletResponse httpResponse = (HttpServletResponse) response;

    httpResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    httpResponse.setHeader("Access-Control-Max-Age", "3600");
    httpResponse.setHeader("Access-Control-Allow-Headers",
        "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
    httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
  }

}
