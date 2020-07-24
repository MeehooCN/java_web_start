package com.meehoo.biz.core.basic.vo.security;
import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.core.basic.domain.security.Role;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by CZ on 2017/10/19.
 */
@Getter
@Setter
public class RoleVO extends IdEntityVO{

    public static final int USERHAS_NO = 0;

    public static final int USERHAS_YES = 1;

    private String id;

    private String number;

    private String name;

    private String remark;

    private int status;

    private String createTime;

    private String updateTime;

    private int    userHas; //用户是否拥有该角色(0没有,1有)

    public RoleVO(Role role) throws Exception {
        this.id = role.getId();
        this.number = role.getCode();
        this.name = role.getName();
        this.remark = role.getRemark();
        this.status = role.getStatus();
        this.createTime = DateUtil.dateToString(role.getCreateTime());
        this.updateTime = DateUtil.dateToString(role.getUpdateTime());
    }
}
