package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.Mapper.EmployeeMapper;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;

import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceimpl extends ServiceImpl<EmployeeMapper, Employee>implements EmployeeService{

}
