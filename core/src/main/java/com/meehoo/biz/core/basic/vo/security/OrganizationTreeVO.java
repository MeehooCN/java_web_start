package com.meehoo.biz.core.basic.vo.security;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.security.Organization;
import com.meehoo.biz.core.basic.util.VOUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by CZ on 2018/1/30.
 */
@Setter
@Getter
@NoArgsConstructor
public class OrganizationTreeVO {

    private String label;//name

    private String value;//id

    private String key;//number

    private String address;//机构地址

    private String detailAddress;

    /**
     * 机构类型
     */
//    private ProOrgTypeVO proOrgTypeVO;
    private String proOrgType;

    private String parentOrgId;

    private String parentOrgName;//上级机构名称

    private String codeRule;
    private int status;

    private List<OrganizationTreeVO> children;

    public OrganizationTreeVO(Organization organization) throws Exception {
        this.label = organization.getName();
        this.key = organization.getCode();
        this.value = organization.getId();
        this.address = organization.getAddress();
        this.proOrgType = organization.getProOrgType();
        StringBuilder sb = new StringBuilder();
        sb.append(organization.getProvince())
                .append(sb.toString().equals(organization.getCity()) ? "" : organization.getCity())
                .append(organization.getArea())
                .append(organization.getAddress());
        this.detailAddress = sb.toString();
        if (organization.getParentOrg()!=null){
            this.parentOrgId = organization.getParentOrg().getId();
            this.parentOrgName =organization.getParentOrg().getName();
        }
        this.children = VOUtil.convertDomainListToTempList(organization.getSubOrgList(), OrganizationTreeVO.class);
        this.status = organization.getStatus();
    }
}