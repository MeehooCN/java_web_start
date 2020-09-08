package com.meehoo.biz.web.controller.basic.common;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.IdEntity;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.ro.bos.ChangeStatusRO;
import com.meehoo.biz.core.basic.ro.bos.PageRO;
import com.meehoo.biz.core.basic.ro.bos.SearchConditionListRO;
import com.meehoo.biz.core.basic.ro.security.AuthenticationRO;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.service.common.ICommonService;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */
public abstract class BaseController<D extends IdEntity, V extends IdEntityVO> {

    protected IBaseService baseService;

    @Autowired
    protected ICommonService commonService;

    protected Class<D> clazzD;

    protected Class<V> clazzV;

    @SuppressWarnings("unchecked")
    public BaseController(IBaseService baseService) {
        this.baseService = baseService;
        Class c = this.getClass();
        Type  t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();

            this.clazzD = (Class<D>) p[0];
            this.clazzV = (Class<V>) p[1];
        }
    }

    /**
     * 分页显示域模型信息
     * ！直接让前端调用这种接口并不安全
     */
    @ApiOperation("基础:分页查询")
//    @RequestMapping(value = "list", method = RequestMethod.POST)
//    @ResponseBody
    protected HttpResult<PageResult<V>> list(@RequestBody PageRO pagePO) throws Exception {
        PageCriteria pageCriteria     = new PageCriteria(pagePO.getPage(), pagePO.getRows());
        PageResult<V>       pageResult = baseService.listPage(clazzD, clazzV, pageCriteria, pagePO.getSearchConditionList());
        return new HttpResult<>(pageResult);
    }

    /**
     * 显示所有域模型信息
     * ！直接让前端调用这种接口并不安全
     */
    @ApiOperation("基础:查询所有")
//    @RequestMapping(value = "listAll", method = RequestMethod.POST)
//    @ResponseBody
    protected HttpResult<List<V>> listAll(@RequestBody SearchConditionListRO searchConditionListRO) throws Exception {
        List<V>             resultList = baseService.listAll(clazzD, clazzV, searchConditionListRO.getSearchConditionList());
        return new HttpResult<>(resultList);
    }

    @ApiOperation("基础:根据id获取实体类")
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<V> getById(@RequestBody IdRO idRO) throws Exception{
        Object objId    = null;
        Method methodId = clazzD.getMethod("getId");
        if (methodId.getReturnType().getSimpleName().equals("Long")) {
            objId = Long.valueOf(idRO.getId());
        } else if (methodId.getReturnType().getSimpleName().equals("String")) {
            objId = idRO.getId();
        }

        D domain = this.baseService.queryById(clazzD, objId);
        if (BaseUtil.objectNotNull(domain)) {
            Constructor<V> VOconstructor = clazzV.getConstructor(new Class[]{clazzD});
            V              voInstance    = VOconstructor.newInstance(new Object[]{domain});
            return new HttpResult<>(voInstance);
        } else {
            throw new RuntimeException("未查询到" + idRO.getId() + "的对象:" + clazzD.getSimpleName());
        }
    }

    @ApiOperation("基础:删除")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult delete(@RequestBody IdRO idRO) throws Exception {
        baseService.deleteById( clazzD, idRO.getId());
        return new HttpResult();
    }

    /**
     * 更新status状态
     * @param changeStatusRO
     * @return
     */
    @ApiOperation("基础:更新status状态")
    @RequestMapping(value = "changeStatus", method = RequestMethod.POST)
    public HttpResult<Boolean> changeStatus(@RequestBody ChangeStatusRO changeStatusRO) throws Exception {
        Object objId = null;
        Method methodId = clazzD.getMethod("getId");
        if (methodId.getReturnType().getSimpleName().equals("Long")) {
            objId = Long.valueOf(changeStatusRO.getId());
        } else if (methodId.getReturnType().getSimpleName().equals("String")) {
            objId = changeStatusRO.getId();
        }

        D domain = this.baseService.queryById(clazzD, objId);
        if (BaseUtil.objectNotNull(domain)) {
            Method mSetStatus = clazzD.getMethod("setStatus", int.class);
            mSetStatus.invoke(domain, changeStatusRO.getStatus());
            this.baseService.update(domain);
            return new HttpResult(true);
        } else {
            throw new RuntimeException("未查询到" + changeStatusRO.getId() + "的对象:" + clazzD.getSimpleName());
//                map.put("flag", "1");
//                map.put("msg", "未查询到" + changeStatusRO.getId() + "的对象:" + clazzD.getSimpleName());
        }
    }


}
