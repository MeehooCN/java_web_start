package com.meehoo.biz.core.basic.ro.security;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.ro.bos.AddressEntityRO;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CZ on 2018/1/18.
 */
@Getter
@Setter
public class OrganizationRO extends AddressEntityRO {

    private String parentOrgId;

    private String parentOrgCode;

    private int grade;//级次

    private int orgType;

    private List<OrganizationRO> subOrgROList;

//    private String proOrgTypeId;// 机构类型Id
    private String proOrgType;// 机构类型

    private String contactPerson; // 联系人

    private String contactPhone; // 联系电话

    private String description;

    public Map<String,Object> checkFields() {
        Map<String,Object> map = new HashMap<>();
        if (StringUtil.stringIsNull(this.name)) {
            map.put("flag",1);
            map.put("msg","请输入机构名称");
            return map;
        }
        if (BaseUtil.collectionNotNull(subOrgROList)) {
            for (OrganizationRO organizationRO : subOrgROList) {
                map = organizationRO.checkFields();
                if(!"0".equals(map.get("flag"))){
                    return map;
                }
            }
        }
        return map;
    }
}