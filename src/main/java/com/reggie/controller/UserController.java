package com.reggie.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie.utils.MailUtils;
import org.apache.commons.lang.StringUtils;

import com.reggie.common.R;
import com.reggie.entity.User;
import com.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController{

    @Autowired
    private UserService userService;

    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) throws MessagingException {
        // 获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            //生成随机验证码
            String code = MailUtils.achieveCode();
            //这里的phone其实就是邮箱，code是我们生成的验证码
            MailUtils.sendTestMail(phone, code);
            log.info("验证码为：{}",code);
            //验证码存session，方便后面拿来对比进行登录
            session.setAttribute(phone, code);
            return R.success("验证码发送成功");
        }
        return R.error("验证码发送失败");
    }
    /**
     * 移动端用户登录
     * @param map
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) throws MessagingException {
        log.info(map.toString());
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //根据手机号，从session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone).toString();
        //进行验证码的比对（页面提交的验证码和session中保存的验证码）
        if (codeInSession!=null&&codeInSession.equals(code)){
            //比对成功，则登陆成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            //判断当前手机号是否为新用户，如果是则自动完成注册
            User userInTab = userService.getOne(queryWrapper);
            if (userInTab==null){
                //自动完成注册
                userInTab = new User();
                userInTab.setPhone(phone);
                userInTab.setStatus(1);
                userService.save(userInTab);
            }
            //记得将当前session中的id设置为用户id，不然会继续拦截
            session.setAttribute("user",userInTab.getId());
            return R.success(userInTab);
        }
        return R.error("登陆失败");
    }
}
