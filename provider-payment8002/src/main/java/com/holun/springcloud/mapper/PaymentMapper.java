package com.holun.springcloud.mapper;

import com.holun.springcloud.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface PaymentMapper
{
    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
