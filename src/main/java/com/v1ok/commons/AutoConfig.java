package com.v1ok.commons;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.v1ok.commons.error")
@ComponentScan("com.v1ok.commons.interceptor")
public class AutoConfig {

}
