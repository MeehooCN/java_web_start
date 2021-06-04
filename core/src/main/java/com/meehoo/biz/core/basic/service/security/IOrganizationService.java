package com.meehoo.biz.core.basic.service.security;



import com.meehoo.biz.core.basic.domain.security.Organization;
import com.meehoo.biz.core.basic.ro.security.OrganizationRO;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.vo.security.OrganizationTreeTotalVO;
import com.meehoo.biz.core.basic.vo.security.OrganizationTreeVO;
import com.meehoo.biz.core.basic.vo.security.OrganizationVO;

import java.util.List;
import java.util.Map;

/**
 * Created by CZ on 2018/1/17.
 */
public interface IOrganizationService extends IBaseService {
    String createOrUpdate(OrganizationRO organizationRO) throws Exception;

    OrganizationTreeTotalVO listAll() throws Exception;

    List<OrganizationVO> getSubOrgList(String parentOrgId) throws Exception;

    List<Organization> getAllSubOrg(String parentOrgCode) throws Exception;

    List<OrganizationTreeVO> getSubOrgListTree(String parentOrgCode) throws Exception;

    List<OrganizationVO> queryByUserId(String userId) throws Exception;

    List<OrganizationVO> listOrgAndSubOrg(String orgId) throws Exception;

    boolean delete(String id) throws Exception;

    Organization queryById(String orgId) throws Exception;

    /**
     * 获取父机构下所有子孙机构的Id
     * @param parentOrgId
     * @return
     */
    List<String> allSubOrgIdList(String parentOrgId);

    /**
     * 获取当前用户机构下子孙机构的id
     * @param userId
     * @return
     */
    List<String> allSubOrgIdListByUserId(String userId);

    List<Organization> listRoot();

    List<Organization> getAllOrganization()throws Exception;

    OrganizationTreeTotalVO getAllOrganizationWithEnable() throws Exception;
}