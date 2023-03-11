package com.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.Employee;
import com.reggie.mapper.EmployeeMapper;
import com.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * 在实现类中加入注解
 * 继承mybatisplus的ServiceImpl接口
 * 在impl中注册@Service
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
