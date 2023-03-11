package com.reggie.controller;

import com.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * （1） controller
 * 控制层
 * 相当于MVC的C层，controller通过service的接口来控制业务流程，
 * 也可通过接收前端传过来的参数进行业务操作。
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

}

