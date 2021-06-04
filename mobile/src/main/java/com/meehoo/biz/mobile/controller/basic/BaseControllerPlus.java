package com.meehoo.biz.mobile.controller.basic;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.IdEntity;
import com.meehoo.biz.core.basic.enumeration.CommonMethod;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.param.SearchCondition;
import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.ro.bos.ChangeStatusRO;
import com.meehoo.biz.core.basic.ro.bos.PageRO;
import com.meehoo.biz.core.basic.ro.bos.SearchConditionListRO;
import com.meehoo.biz.core.basic.ro.security.AuthenticationRO;
import com.meehoo.biz.core.basic.ro.security.UpdateFiledRO;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.service.common.ICommonService;
import com.meehoo.biz.core.basic.service.setting.IOptLogService;
import com.meehoo.biz.core.basic.util.ReflectUtil;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
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
public abstract class BaseControllerPlus<D extends IdEntity, V extends IdEntityVO, R extends IdRO> extends BaseController<D ,V>{

    protected Class<R> clazzR;

    public BaseControllerPlus() {
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        Type[] classes = ReflectUtil.getActualTypes(this.getClass().getGenericSuperclass());
        if (classes != null) {
            clazzR = (Class<R>) classes[2];
        }
    }

    @ApiOperation(value = "基础__通用新增接口", notes = "具体参数参考Ro类型定义")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public HttpResult<String> create(@RequestBody R ro) throws Exception {
        D domain = baseService.createOrUpdate(clazzD, ro);
        String idProperty = (String) ReflectUtil.getIdProperty(domain);
        return HttpResult.success(idProperty);
    }

    @ApiOperation("基础__通用更改接口")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public HttpResult<Boolean> update(@RequestBody R ro) throws Exception {
        baseService.createOrUpdate(clazzD, ro);
        return HttpResult.success(true);
    }

    public HttpResult<Boolean> updateField(@RequestBody UpdateFiledRO updateFiledRO) throws Exception {
        baseService.updateField(clazzD, updateFiledRO);
        return HttpResult.success(true);
    }
}