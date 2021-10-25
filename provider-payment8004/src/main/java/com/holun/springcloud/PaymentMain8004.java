package com.holun.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 将payment-service服务在zookeeper注册中心中注册
 */
@SpringBootApplication
@EnableDiscoveryClient  //使用该注解将服务在zookeeper注册中心中注册
public class PaymentMain8004 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8004.class, args);
    }
}
