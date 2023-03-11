package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * （4） mapper
 * 数据存储对象
 * 相当于DAO层，mapper层直接与数据库打交道（执行SQL语句），接口提供给service层。
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
