package com.meehoo.biz.web.controller.basic.auth;


import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.service.security.IAdminService;
import com.meehoo.biz.core.basic.service.security.ILoginRecordService;
import com.meehoo.biz.core.basic.service.security.IUserService;
import com.meehoo.biz.core.basic.util.UserContextUtil;
import com.meehoo.biz.web.config.ApplicationValues;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "登录管理")
@Controller
@Slf4j
@AllArgsConstructor
public class LoginController {
    private final ILoginRecordService loginRecordService;
    private final IAdminService adminService;
    private final IUserService userService;

    //    @ApiOperation("登录成功")
    @GetMapping(value = "/loginSuccess")
    public String loginSuccess(HttpServletRequest request) throws Exception {
        Object object = UserContextUtil.getCurrentUser();
        if(object instanceof Admin) {
            return "redirect:/sysmanage/index";
        }else {
            return "redirect:/";
        }
    }
    @RequestMapping(value = "/remoteLogin",method = RequestMethod.GET)
    @ResponseBody
    public String remoteLogin(String username, String password) {
        try {
            String token = loginRecordService.login(username, password);
            return "登录成功?token="+token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "登陆失败";
    }


    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, HttpServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        String msg = "";
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            msg =   "账号不存在！";
        } catch (DisabledAccountException e) {
            msg =   "账号未启用！";
        } catch (IncorrectCredentialsException e) {
            msg =   "密码错误！";
        } catch (Throwable e) {
            msg =   e.getMessage();
        }
        if (StringUtil.stringNotNull(msg)){
            log.info(msg);
            request.setAttribute("error", true);

            request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",msg);
//            return "redirect:/logout";
//            return "/login";
            return "redirect:/logoutError";
        }
        request.setAttribute("error", null);
        request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",null);
        if (subject.isAuthenticated()&& StringUtil.stringIsNull(msg)){
//            afterAuthenticate();
            return "redirect:sysmanage/index";
        }else{
            return "login-admin";
        }
    }

    @RequestMapping("/403")
    public String unauthorized() {
        log.info("------没有权限-------");
        return "unauthorized";
    }
}
