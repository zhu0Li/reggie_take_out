package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie.common.R;
import com.reggie.entity.Employee;
import com.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 实现员工登录方法
     * 如果是分布式，用session可能会出现问题
     * 方法通常写在业务层Service当中，controller直接调用；但是简单方法可以放在controller中实现
     * service通常用来实现复杂方法，mapper（dao）实现简单方法
     *
     * @param request  要将登录用户的id存到session ，表示登陆成功
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //1，将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());//封装好条件
        Employee emp = employeeService.getOne(queryWrapper);//username不能重复，所以可以查出唯一数据

        //3、如果没有查询到则返回登录失败结果
        if (emp == null) return R.error("登录失败，不存在用户名");

        //4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) return R.error("登录失败，密码错误");

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus()==0) return R.error("账号已禁用");

        //6、登录成功，将员工id存入Session并返回登灵成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * 要操作session就需要request
     * @param request
     * @return
     */
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request){
        //清理session中保存的登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
}

