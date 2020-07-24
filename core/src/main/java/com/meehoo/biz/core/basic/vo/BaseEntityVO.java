package com.meehoo.biz.core.basic.vo;

import com.meehoo.biz.core.basic.domain.BaseEntity;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by CZ on 2017/11/1.
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class BaseEntityVO extends IdEntityVO {
    protected String name;
    protected String code;
    protected int status;
    protected String statusShow;

    protected BaseEntityVO(BaseEntity baseEntity) {
        super(baseEntity);
        this.name = baseEntity.getName();
        this.code = baseEntity.getCode();
        this.status = baseEntity.getStatus();
        this.statusShow = baseEntity.getStatus() == BaseEntity.STATUS_ENABLE ? "启用" : "禁用";
    }
}
