package com.meehoo.biz.core.basic.service.security;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StreamUtil;
import com.meehoo.biz.core.basic.dao.security.IRoleDao;
import com.meehoo.biz.core.basic.dao.security.IUserOrganizationDao;
import com.meehoo.biz.core.basic.domain.security.Role;
import com.meehoo.biz.core.basic.domain.security.UserOrganization;
import com.meehoo.biz.core.basic.domain.security.UserOrganizationRole;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.security.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CZ on 2017/10/19.
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseService implements IRoleService {
    private final IRoleDao roleDao;
    private final IUserOrganizationDao userOrganizationDao;
    @Autowired
    public RoleServiceImpl(IRoleDao roleDao, IUserOrganizationDao userOrganizationDao) {
        this.roleDao = roleDao;
        this.userOrganizationDao = userOrganizationDao;
    }

    @Override
    public RoleVO getRoleByNumber(String number) throws Exception {
        List<Role> roleList = roleDao.queryByNumber(number);
        if (BaseUtil.collectionNotNull(roleList)) {
            return new RoleVO(roleList.get(0));
        }
        return null;
    }

    @Override
    public Role queryByNumber(String number) throws Exception {
        List<Role> roleList = roleDao.queryByNumber(number);
        return BaseUtil.collectionNotNull(roleList) ? roleList.get(0) : null;
    }
    @Override
    public List<Role> listAll() throws Exception {
        return roleDao.findAll();
    }

    @Override
    public List<Role> getRolesByUserAndOrg(String userId, String organizationId) throws Exception {
        UserOrganization uerOrg = userOrganizationDao.findByUserIdAndOrganizationId(userId, organizationId);
        if (BaseUtil.objectNotNull(uerOrg)) {
            return StreamUtil.extractFieldToList(uerOrg.getUserOrganizationRoleList(), UserOrganizationRole::getRole);
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<RoleVO> listByRoleType(Integer roleType) throws Exception{
        List<Role> roleList = roleDao.queryByRoleType(roleType);
        return VOUtil.convertDomainListToTempList(roleList,RoleVO.class);
    }
}
