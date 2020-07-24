package com.meehoo.biz.core.basic.vo.setting;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.core.basic.domain.setting.OptLog;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjian on 2017-12-05.
 */
@Setter
@Getter
public class OptLogVO {

    private String id;

    /**
     * 操作人Id
     */
    private String userId;

    /**
     * 操作人姓名
     */
    private String userName;

    /**
     * 所在机构名称
     */
    private String orgName;

    /**
     * 所在机构类型
     */
    private String orgType;

    /**
     * 所在机构角色
     */
    private String roleName;

    /**
     * 操作时间
     */
    private String createTime;

    /**
     * 操作内容
     */
    private String opt;

    public OptLogVO(OptLog optLog) {
        this.id = optLog.getId();
        this.userId = optLog.getUserId();
        this.userName = optLog.getUserName();
        this.orgName = optLog.getOrgName();
        this.roleName = optLog.getRoleName();
        this.createTime = DateUtil.timeToString(optLog.getCreateTime());
        this.opt = optLog.getOpt();
//        switch (optLog.getOrgType()) {
//            case Organization.ORG_TYPE_SQUADRON:
//                this.orgType = "中队";
//                break;
//            case Organization.ORG_TYPE_DIVISION:
//                this.orgType = "支队";
//                break;
//            case Organization.ORG_TYPE_CORPS:
//                this.orgType = "总队";
//                break;
//            default:
//                this.orgType = "";
//        }
    }
}
