package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.annotation.SetBySystem;
import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 角色对象
 * Created by CZ on 2017/10/19.
 */
@Entity
@Table(name = "sec_role_inv")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Accessors(chain = true)
public class Role extends IdEntity {

    public static final int STATUS_FORBID = 0;

    public static final int STATUS_ENABLE = 1;

    public static final int ISPUBLIC_NO = 0;

    public static final int ISPUBLIC_YES = 1;

    public static final int SYSTEMDEFAUFT_NO = 0;

    public static final int SYSTEMDEFAUFT_YES = 1;
    /**
     * 编号
     */
    @Column(nullable = false,columnDefinition = "int default 0")
    private int code;

    /**
     * 角色名
     */
    @Column(nullable = false, length = 80)
    private String name;

    /**
     * 备注
     */
    @Column(length = 255)
    private String remark;

    /**
     * 状态值(STATUS_FORBID禁用；STATUS_ENABLE启用)
     */
    @Column
    private int status = STATUS_ENABLE;

    /**
     * 是否系统预设
     */
    @Column
    private int systemDefault = STATUS_ENABLE;


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
    @SetBySystem(updateOnly = true)
    private Date updateTime;

    /**
     * 用户类型
     * 0 管理员
     * 1 用户
     */
    @Column(columnDefinition = "INT default 0")
    private Integer roleType;
}