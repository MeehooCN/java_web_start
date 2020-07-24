package com.meehoo.biz.web.controller.basic.common;

import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.service.setting.IValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Wanghan on 2016/5/16.
 */
//@Controller
//@RequestMapping("/vCode")
public class VCodeController {

    private final IValidateCodeService validateCodeService;

    @Autowired
    public VCodeController(IValidateCodeService validateCodeService) {
        this.validateCodeService = validateCodeService;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @GetMapping("")
    @ResponseBody
    public HttpResult getVCode(String tel) {
        try {
            validateCodeService.sendCode(tel);
            return new HttpResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult(e);
        }
    }

    /**
     * 验证
     * 用于修改密码\找回密码
     * @param tel
     * @param code
     * @return
     */
    @GetMapping("checkCode")
    @ResponseBody
    public HttpResult checkVCode(String tel, String code) {
        try {
            if(validateCodeService.checkCode(code, tel, new Date())){
                return new HttpResult();
            }else{
                throw new RuntimeException("失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult(e);
        }
    }

}
