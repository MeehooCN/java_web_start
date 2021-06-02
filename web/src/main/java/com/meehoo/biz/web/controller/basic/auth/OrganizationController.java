package com.meehoo.biz.web.controller.basic.auth;

import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.security.Organization;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.param.SearchCondition;
import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.ro.security.AuthenticationRO;
import com.meehoo.biz.core.basic.ro.security.OrganizationRO;
import com.meehoo.biz.core.basic.service.security.IOrganizationService;
import com.meehoo.biz.core.basic.util.UserContextUtil;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.security.OrganizationTreeTotalVO;
import com.meehoo.biz.core.basic.vo.security.OrganizationTreeVO;
import com.meehoo.biz.core.basic.vo.security.OrganizationVO;
import com.meehoo.biz.web.controller.basic.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by CZ on 2018/1/19.
 */
@Api(tags = "组织机构管理")
@RestController
@RequestMapping("/security/organization")
public class OrganizationController extends BaseController<Organization, OrganizationVO> {

    private final IOrganizationService organizationService;

    @Autowired
    public OrganizationController(IOrganizationService organizationService) {
        super(organizationService);
        this.organizationService = organizationService;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public HttpResult<String> create(@RequestBody OrganizationRO organizationRO) throws Exception {
        Map<String, Object> map = organizationRO.checkFields();//成功状态的map为空
//        if (!"0".equals(map.get("flag")))
//            throw new RuntimeException("请输入机构名称");

        organizationService.createOrUpdate(map, organizationRO);

        return HttpResult.success();
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public HttpResult<String> update(@RequestBody OrganizationRO organizationRO) throws Exception {
        Map<String, Object> map = organizationRO.checkFields();
//        if (BaseUtil.mapContainsErrorFlag(map))
//            throw new RuntimeException("请输入机构名称");
        map = organizationService.createOrUpdate(map, organizationRO);

        return HttpResult.success();
    }

    /**
     * 查询所有机构
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "listAllForAdmin", method = RequestMethod.POST)
    public HttpResult<OrganizationTreeTotalVO> listAllForAdmin() throws Exception {
        OrganizationTreeTotalVO organizationTreeTotalVO = organizationService.listAll();
        return HttpResult.success(organizationTreeTotalVO);
    }


    /**
     * 查询所有根节点
     */
    @PostMapping("listRoot")
    public HttpResult<List<OrganizationVO>> listRoot() throws Exception {
        List<Organization> organizations = organizationService.listRoot();
        List<OrganizationVO> organizationVOS = VOUtil.convertDomainListToTempList(organizations, OrganizationVO.class);
        return HttpResult.success(organizationVOS);
    }

    /**
     * 查询所有机构并标记被查询用户所属的机构
     *
     * @param idRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "listAllForUser", method = RequestMethod.POST)
    public HttpResult<List<OrganizationTreeVO>> test(@RequestBody IdRO idRO) throws Exception {
        if (StringUtil.stringIsNull(idRO.getId()))
            throw new RuntimeException("请选择要查询的用户");
        List<OrganizationTreeVO> organizationTreeVOS = organizationService.listAll(idRO.getId());
        return HttpResult.success(organizationTreeVOS);
    }

    /**
     * 查询当前机构的子机构,如果当前机构id为空则查询顶层机构
     *
     * @param idRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "listSubOrg", method = RequestMethod.POST)
    public HttpResult<List<OrganizationVO>> listSubOrg(@RequestBody IdRO idRO) throws Exception {
        List<OrganizationVO> voList = organizationService.getSubOrgList(idRO.getId());
        return HttpResult.success(voList);
    }

    @RequestMapping(value = "listSubOrgTree", method = RequestMethod.POST)
    public HttpResult<List<OrganizationTreeVO>> listSubOrgTree(String parentOrgCode) throws Exception {
        List<OrganizationTreeVO> voList = organizationService.getSubOrgListTree(parentOrgCode);
        return HttpResult.success(voList);
    }


    /**
     * 查询当前用户所在机构的子孙机构
     * @param idRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "listSubOrgByUserId", method = RequestMethod.POST)
    public HttpResult< List<OrganizationVO>> listSubOrgByUserId(@RequestBody IdRO idRO) throws Exception {
        List<OrganizationVO> organizationVOS = organizationService.queryByUserId(UserContextUtil.getUser().getId());
        return HttpResult.success(organizationVOS);
    }

    /**
     * 查询当前机构的子机构,如果当前机构id为空则查询顶层机构
     *
     * @param authenticationRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "listOrgAndSubOrg", method = RequestMethod.POST)
    public HttpResult< List<OrganizationVO>> listOrgAndSubOrg(@RequestBody AuthenticationRO authenticationRO) throws Exception {
        List<OrganizationVO> voList = organizationService.listOrgAndSubOrg(authenticationRO.getCurrOrgId());
        return HttpResult.success(voList);
    }

//    @RequestMapping(value = "delete", method = RequestMethod.POST)
//    public HttpResult delete(@RequestBody IdRO idRO) throws Exception {
//        organizationService.delete(idRO.getId());
//        return new HttpResult();
//    }

    @ApiOperation("根据名称类型模糊查询单位")
    @GetMapping("getOrgByNameAndType")
    public HttpResult<List<OrganizationVO>> getOrgByNameAndType(String name,String typeName) throws Exception {
        List<SearchCondition> conditionList = new ArrayList<>();
        if (!StringUtil.stringNotNull(name))
            conditionList.add(new SearchCondition("name",name,"like"));
        if (!StringUtil.stringNotNull(typeName))
            conditionList.add(new SearchCondition("projectType",typeName,"like"));
        List<OrganizationVO> organizationVOS = organizationService.listAll(Organization.class, OrganizationVO.class, conditionList);
        return HttpResult.success(organizationVOS);
    }
}