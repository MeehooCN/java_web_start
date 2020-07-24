package com.meehoo.biz.web.controller.basic.common;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.BaseEntity;
import com.meehoo.biz.core.basic.domain.IdEntity;
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

/**
 * Created by Administrator on 2017/10/31.
 */
public abstract class BaseControllerPlus<D extends IdEntity, V extends IdEntityVO, R extends IdRO> extends BaseController<D ,V>{

    protected Class<R> clazzR;

    public BaseControllerPlus(IBaseService baseService) {
        super(baseService);
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        Type[] classes = ReflectUtil.getActualTypes(this.getClass().getGenericSuperclass());
        if (classes != null) {
//            clazzD = (Class<D>) classes[0];
//            clazzV = (Class<V>) classes[1];
            clazzR = (Class<R>) classes[2];
        }
    }

    @Override
    protected HttpResult<PageResult<V>> list(PageRO pagePO) throws Exception {
        if (BaseEntity.class.isAssignableFrom(clazzD)){
            pagePO.getSearchConditionList().add(new SearchCondition("isDelete","0","="));
        }
        return super.list(pagePO);
    }

    protected HttpResult<PageResult<V>> listWithDelete(PageRO pagePO) throws Exception {
        return super.list(pagePO);
    }

    @Override
    protected HttpResult<List<V>> listAll(@RequestBody SearchConditionListRO searchConditionListRO) throws Exception {
        if (BaseEntity.class.isAssignableFrom(clazzD)){
            searchConditionListRO.getSearchConditionList().add(new SearchCondition("isDelete","0","="));
        }
        return super.listAll(searchConditionListRO);
    }

    protected HttpResult<List<V>> listAllWithDelete(@RequestBody SearchConditionListRO searchConditionListRO) throws Exception {
        return super.listAll(searchConditionListRO);
    }

    @ApiOperation(value = "通用新增接口", notes = "具体参数参考Ro类型定义")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public HttpResult<String> create(@RequestBody R ro) throws Exception {
//        Map<String, Object> map;
//        Method checkMethod = ReflectUtil.findMethod(clazzR, CommonMethod.FIELDSCHECK.toString());
//        if (checkMethod != null) {
//            Map<String, Object> map = (Map<String, Object>) ReflectUtil.invokeMethod(checkMethod, vo);
//            if (BaseUtil.mapContainsErrorFlag(map)){
//                //                return map;
//            }
//        }
        D domain = baseService.createOrUpdate(clazzD, ro);
        String idProperty = (String) ReflectUtil.getIdProperty(domain);
        return new HttpResult<>(idProperty);
    }

    @ApiOperation("通用更改接口")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public HttpResult<Boolean> update(@RequestBody R ro) throws Exception {
//        Method checkMethod = ReflectUtil.findMethod(clazzR, CommonMethod.FIELDSCHECK.toString());
//        if (checkMethod != null) {
//            Map<String, Object> map = (Map<String, Object>) ReflectUtil.invokeMethod(checkMethod, vo);
//            if (BaseUtil.mapContainsErrorFlag(map))
//                return new HttpResult<>(false);
//        }
        baseService.createOrUpdate(clazzD, ro);
        return new HttpResult<>(true);
    }

    //    @ApiOperation("修改某些字段,key是字段名，value是修改后的值")
//    @RequestMapping(value = "updateField", method = RequestMethod.POST)
    public HttpResult<Boolean> updateField(@RequestBody UpdateFiledRO updateFiledRO) throws Exception {
//        Method checkMethod = ReflectUtil.findMethod(clazzR, CommonMethod.FIELDSCHECK.toString());
//        if (checkMethod != null) {
//            Map<String, Object> map = (Map<String, Object>) ReflectUtil.invokeMethod(checkMethod, vo);
//            if (BaseUtil.mapContainsErrorFlag(map))
//                return new HttpResult<>(false);
//        }
        baseService.updateField(clazzD, updateFiledRO);
        return new HttpResult<>(true);
    }

}