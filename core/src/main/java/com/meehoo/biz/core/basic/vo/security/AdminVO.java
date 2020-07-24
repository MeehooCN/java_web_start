package com.meehoo.biz.core.basic.vo.security;

import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by Wanghan on 2015/11/11.
 */
@Getter
@Setter
public class AdminVO extends IdEntityVO{

    private String token;
    private String id;

    private String username;

    private String headPic;

    private Date createTime;

    private String roleId;

    private String roleName;

    private String organizationId;

    private String organizationName;

    private String organizationCode;

    private List<OrganizationVO> organizationVOS;

    public AdminVO(Admin admin){
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.headPic = admin.getHeadPic();
        this.createTime = admin.getCreateTime();
        roleId = admin.getRoleId();
        roleName = admin.getRoleName();
        this.organizationId = admin.getOrganizationId();
        this.organizationName = admin.getOrganizationName();
        this.organizationCode = admin.getOrganizationCode();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
