package com.holun.springcloud.test;

import org.springframework.cloud.client.ServiceInstance;
import java.util.List;

public interface LoadBalancer {
    //获取服务中包含的实例
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}

