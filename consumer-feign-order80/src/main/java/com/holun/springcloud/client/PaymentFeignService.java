package com.holun.springcloud.client;

import com.holun.springcloud.entity.CommonResult;
import com.holun.springcloud.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@Component 可以不加该注解
@FeignClient(value = "PAYMENT-SERVICE") //PAYMENT-SERVICE是要调用的服务名
public interface PaymentFeignService {

    //定义要调用的服务中的接口
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout();
}

