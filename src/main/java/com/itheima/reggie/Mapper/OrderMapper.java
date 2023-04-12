package com.itheima.reggie.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Orders;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderMapper extends BaseMapper<Orders> {
}
