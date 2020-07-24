package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.annotation.SetBySystem;
import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 平台运维用户
 */
@Entity
@Table(name = "sec_admin_inv")
@DynamicInsert
@DynamicUpdate
@Data
@Accessors(chain = true)
public class Admin extends IdEntity {

    /**
     * 登录名
     */
    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    /**
     * 头像
     */
    @Column
    private String headPic;
    /**
     * 创建时间
     */
    @Column
    @SetBySystem(createOnly = true)
    private Date createTime;
    /**
     * 更新时间
     */
    @Column
    private Date updateTime;

    @Column(length = 50)
    private String roleName;

    @Column(length = 50)
    private String roleId;

    @Column(length = 50)
    private String organizationId;

    @Column(length = 100)
    private String organizationName;

    @Column(length = 100)
    private String organizationCode;
}

