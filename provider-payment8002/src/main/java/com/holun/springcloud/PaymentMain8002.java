package com.holun.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 搭建支付微服务集群（服务提供者集群）
 */
@SpringBootApplication
@EnableEurekaClient //需要在注册中心注册的服务，需要在主启动类上使用该注解
@EnableDiscoveryClient  //对于在eureka注册中心中注册的服务，可以通过开启全服务发现来获得该服务的信息
public class PaymentMain8002 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8002.class, args);
    }
}
