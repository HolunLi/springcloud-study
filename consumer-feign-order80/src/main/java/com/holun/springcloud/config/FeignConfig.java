package com.holun.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenFeign日志增强
 *
 * Feign提供了日志打印功能，我们可以通过配置来调整日恙级别，从而了解Feign 中 Http请求的细节。
 * 说白了就是对Feign接口的调用情况进行监控和输出
 * 日志级别
 * NONE：默认的，不显示任何日志;
 * BASIC：仅记录请求方法、URL、响应状态码及执行时间;
 * HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息;
 * FULL：除了HEADERS中定义的信息之外，还有请求和响应的正文及元数据。
 */
@Configuration
public class FeignConfig {

    /**
     * 配置日志bean
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}


