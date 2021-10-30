package com.holun.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.concurrent.TimeUnit;

@Service
//指明全局回调方法
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class PaymentService {

    public String paymentInfo_OK(Integer id)
    {
        return "线程池:  "+Thread.currentThread().getName()+"  paymentInfo_OK,id:  "+id+"\t"+"O(∩_∩)O哈哈~";
    }

    //=================================测试服务降级============================================================
    /**
     * 在多线程并发下，如果对paymentInfo_TimeOut方法进行大量请求，会出现请求响应缓慢。不仅如此，还会影响对该服务中其他接口的请求
     * 解决方法：设置自身调用超时时间的峰值，峰值内可以正常运行，超过了需要有兜底的方法处埋，作服务降级的fallback（回调方法）。
     */
   @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            //设置调用该方法的超时时间为3秒。如果调用该方法超过3秒，都还没有响应，说明服务出现超时异常，就进行服务降级，执行回调方法
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")
    })
    public String paymentInfo_TimeOut(Integer id)
    {
        //int age = 10/0;  //除了出现超时异常外，如果出现其他异常，或者服务宕机，都会进行服务降级，执行回调方法
        //这里让线程休眠5秒，模拟复杂业务
        try { TimeUnit.MILLISECONDS.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
        return "线程池:  "+Thread.currentThread().getName()+" id:  "+id+"\t"+"O(∩_∩)O哈哈~"+"  耗时(秒): 5";
    }
    /**
     *  回调方法（用来善后的方法）.
     *  这个是专属于paymentInfo_TimeOut方法的兜底方法，如果专属的兜底方法太多，会导致代码膨胀。
     *  为了避免代码膨胀，除了个别重要核心的业务方法有专属的兜底方法外，为其它普通的业务方法，提供一个全局的兜底方法（服务降级时的回调方法）
     */
    public String paymentInfo_TimeOutHandler(Integer id)
    {
        return "线程池:  "+Thread.currentThread().getName()+"  8001系统繁忙或者运行报错，请稍后再试,id:  "+id+"\t"+"o(╥﹏╥)o";
    }

    @HystrixCommand
    public String paymentInfo_OtherException(Integer id)
    {
        int age = 10/0;  //除了出现超时异常外，如果出现其他异常，或者服务宕机，都会进行服务降级，执行回调方法（这里执行的是全局的回调方法）
        return "线程池:  "+Thread.currentThread().getName()+" id:  "+id+"\t"+"O(∩_∩)O哈哈~"+"  耗时(秒): 5";
    }

    //全局fallback方法，适用于任何发生服务降级的业务方法
    public String payment_Global_FallbackMethod()
    {
        return "Global异常处理信息，请稍后再试，/(ㄒoㄒ)/~~";
    }
    //=================================测试服务降级============================================================


    //=================================测试服务熔断============================================================
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数（默认为20次，这里改为10次）
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期,也就是时间范围（默认就是10秒）
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//请求失败率（百分比）达到多少后，开启熔断器（默认是50%）
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if(id < 0) {
            throw new RuntimeException("******id 不能负数"); //传入的参数id小于0，直接抛出一个异常，这是为了模拟在调用该方法时出现异常
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }
    //=================================测试服务熔断============================================================


}

