package com.meehoo.biz.core.basic.ro.security;

import com.meehoo.biz.core.basic.ro.IdRO;
import lombok.Data;

/**
 * @author zc
 * @date 2019-04-26
 */
@Data
public class AdminRO extends IdRO{

    private String id;

    /**
     * 登录名
     */
    private String username;

    private String password;

    /**
     * 头像
     */
    private String headPic;

    private String roleId;

    private String roleName;

    private String organizationId;

    private String organizationName;

    private String organizationCode;
}
