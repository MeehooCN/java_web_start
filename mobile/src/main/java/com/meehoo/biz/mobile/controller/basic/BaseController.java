package com.meehoo.biz.mobile.controller.basic;

import com.meehoo.biz.common.util.BaseUtil;
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
public abstract class BaseController<D, V> {

    @Autowired
    private IBaseService baseService;

    @Autowired
    private ICommonService commonService;

    protected Class<D> clazzD;

    protected Class<V> clazzV;

    @SuppressWarnings("unchecked")
    public BaseController() {
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
     *
     * @param pageRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public HttpResult<PageResult<V>> list(@RequestBody PageRO pageRO) throws Exception {
        PageCriteria pageCriteria = new PageCriteria(pageRO.getPage(), pageRO.getRows());
        PageResult<V> roleVOPageResult = baseService.listPage(clazzD, clazzV, pageCriteria, pageRO.getSearchConditionList());
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
     * @param idRO
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public HttpResult<V> getById(@RequestBody IdRO idRO) throws Exception{
        Object objId = null;
        Method methodId = clazzD.getMethod("getId");
        if (methodId.getReturnType().getSimpleName().equals("Long")) {
            objId = Long.valueOf(idRO.getId());
        } else if (methodId.getReturnType().getSimpleName().equals("String")) {
            objId = idRO.getId();
        }
        D domain = this.baseService.queryById(clazzD, objId);
        if (BaseUtil.objectNotNull(domain)) {
            Constructor<V> VOconstructor = clazzV.getConstructor(new Class[]{clazzD});
            V voInstance = VOconstructor.newInstance(new Object[]{domain});
            return new HttpResult<>(voInstance);
        } else {
            throw new RuntimeException("未查询到" + idRO.getId() + "的对象:" + clazzD.getSimpleName());
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult delete(@RequestBody IdRO idRO) throws Exception {
        baseService.deleteById(clazzD, idRO);
        return new HttpResult();
    }

    /**
     * 更新status状态
     * @param changeStatusRO
     * @return
     */
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
            return new HttpResult();
        } else {
            throw new RuntimeException("未查询到" + changeStatusRO.getId() + "的对象:" + clazzD.getSimpleName());
//                map.put("flag", "1");
//                map.put("msg", "未查询到" + changeStatusRO.getId() + "的对象:" + clazzD.getSimpleName());
        }
    }

}
