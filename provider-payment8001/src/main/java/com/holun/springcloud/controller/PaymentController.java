package com.holun.springcloud.controller;

import com.holun.springcloud.entity.CommonResult;
import com.holun.springcloud.entity.Payment;
import com.holun.springcloud.service.impl.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * restful风格
 * post 对应插入操作
 * get 对应查询操作
 * put 对应修改操作
 * delete 对应删除操作
 */
@Slf4j
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("*****插入结果："+result);

        if(result > 0) {
            return new CommonResult(200,"插入数据库成功,serverPort"+serverPort, result);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);

        if(payment != null) {
            return new CommonResult(200,"查询成功,serverPort"+serverPort, payment);
        }else {
            return new CommonResult(444,"没有对应记录,查询ID: "+id,null);
        }
    }
}
