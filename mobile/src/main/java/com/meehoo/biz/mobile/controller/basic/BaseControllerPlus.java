package com.meehoo.biz.mobile.controller.basic;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.IdEntity;
import com.meehoo.biz.core.basic.enumeration.CommonMethod;
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
import com.meehoo.biz.core.basic.service.setting.IOptLogService;
import com.meehoo.biz.core.basic.util.ReflectUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/31.
 */
public abstract class BaseControllerPlus<D extends IdEntity, V, R> {

    private final IBaseService baseService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    protected IOptLogService optLogService;

    private Class<D> clazzD;

    private Class<V> clazzV;

    private Class<R> clazzR;

    public BaseControllerPlus(IBaseService baseService) {
        init();
        this.baseService = baseService;
    }

    @SuppressWarnings("unchecked")
    private void init() {
        Type[] classes = ReflectUtil.getActualTypes(this.getClass().getGenericSuperclass());
        if (classes != null) {
            clazzD = (Class<D>) classes[0];
            clazzV = (Class<V>) classes[1];
            clazzR = (Class<R>) classes[2];
        }
    }

    /**
     * 分页显示域模型信息
     *
     * @param pagePO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public HttpResult<PageResult<V>> list(@RequestBody PageRO pagePO) throws Exception {
        PageCriteria pageCriteria = new PageCriteria(pagePO.getPage(), pagePO.getRows());
        PageResult<V> roleVOPageResult = baseService.listPage(clazzD, clazzV, pageCriteria, pagePO.getSearchConditionList());
        return new HttpResult<>(roleVOPageResult);
    }

    /**
     * 显示所有域模型信息
     *
     * @param searchConditionListRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "listAll", method = RequestMethod.POST)
    public HttpResult<List<V>> listAll(@RequestBody SearchConditionListRO searchConditionListRO) throws Exception {
        List<V> resultList = baseService.listAll(clazzD, clazzV, searchConditionListRO.getSearchConditionList());
        return new HttpResult<>(resultList);
    }

    /**
     * 获取编号
     *
     * @param authenticationRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getNumber", method = RequestMethod.POST)
    public HttpResult<String> getNumber(@RequestBody AuthenticationRO authenticationRO) throws Exception {
//        Map<String, Object> map = new HashMap<>();
        String number = commonService.getBizObjectSerialNumber(clazzD.getSimpleName());
//        map.put("data", number);
        return new HttpResult<>(number);
    }

    /**
     * 获取单个域模型信息
     *
     * @param idPO
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public HttpResult<V> getById(@RequestBody IdRO idPO) throws Exception{
        Object objId = null;
        Method methodId = clazzD.getMethod("getId");
        if (methodId.getReturnType().getSimpleName().equals("Long")) {
            objId = Long.valueOf(idPO.getId());
        } else if (methodId.getReturnType().getSimpleName().equals("String")) {
            objId = idPO.getId();
        }
        D domain = this.baseService.queryById(clazzD, objId);
        if (BaseUtil.objectNotNull(domain)) {
            Constructor<V> VOconstructor = clazzV.getConstructor(new Class[]{clazzD});
            V voInstance = VOconstructor.newInstance(new Object[]{domain});
            return new HttpResult<>(voInstance);
        } else {
            throw new RuntimeException("未查询到" + idPO.getId() + "的对象:" + clazzD.getSimpleName());
        }
    }

    /**
     * 通用删除接口
     *
     * @param idRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public HttpResult delete(@RequestBody IdRO idRO) throws Exception {
        baseService.deleteById(clazzD, idRO);
        return new HttpResult();
    }

    @RequestMapping(value = "changeStatus", method = RequestMethod.POST)
    public HttpResult changeStatus(@RequestBody ChangeStatusRO changeStatusRO) throws Exception {
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
        } else {
//                map.put("flag", "1");
//                map.put("msg", "未查询到" + changeStatusRO.getId() + "的对象:" + clazzD.getSimpleName());
        }
        return new HttpResult();
    }

    /**
     * 通用新增接口
     *
     * @param ro
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "通用新增接口", notes = "具体参数参考Ro类型定义")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public HttpResult<String> create(@RequestBody R ro) throws Exception {
//        Map<String, Object> map;
        Method checkMethod = ReflectUtil.findMethod(clazzR, CommonMethod.FIELDSCHECK.toString());
        if (checkMethod != null) {
            Map<String, Object> map = (Map<String, Object>) ReflectUtil.invokeMethod(checkMethod, ro);
        }
        D domain = baseService.createOrUpdate(clazzD, ro);
        return new HttpResult<>(domain.getId());
    }

    /**
     * 通用更改接口
     *
     * @param ro
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public HttpResult<String> update(@RequestBody R ro) throws Exception {
        Method checkMethod = ReflectUtil.findMethod(clazzR, CommonMethod.FIELDSCHECK.toString());
        if (checkMethod != null) {
            Map<String, Object> map = (Map<String, Object>) ReflectUtil.invokeMethod(checkMethod, ro);
        }
        D domain = baseService.createOrUpdate(clazzD, ro);
        return new HttpResult<>(domain.getId());
    }

}