package com.meehoo.biz.mobile.controller.basic;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.setting.DictType;
import com.meehoo.biz.core.basic.domain.setting.DictValue;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.param.SearchCondition;
import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.ro.setting.DictTypeModuleNumberRO;
import com.meehoo.biz.core.basic.ro.setting.DictValueRO;
import com.meehoo.biz.core.basic.service.setting.IDictTypeService;
import com.meehoo.biz.core.basic.service.setting.IDictValueService;
import com.meehoo.biz.core.basic.vo.setting.DictValueVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典的值
 *
 * @author xg
 * @create 2017-05-11-14:29
 */
@Api(tags = "数据字典的值管理")
@RestController
@RequestMapping("sysmanage/dictValue")
public class DictValueController extends BaseController<DictValue,DictValueVO> {
    private final IDictValueService dictValueService;
    private final IDictTypeService dictTypeService;

    @Autowired
    public DictValueController(IDictValueService dictValueService, IDictTypeService dictTypeService) {
        super();
        this.dictValueService = dictValueService;
        this.dictTypeService = dictTypeService;
    }

    /**
     * 新增
     *
     * @param dictValueRO
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public HttpResult create(@RequestBody DictValueRO dictValueRO) {
        try {
            DictType dictType = dictTypeService.queryById(DictType.class, dictValueRO.getDictTypeId());
            if (dictValueService.getByTypeNumberAndKey(dictType.getCode(),dictValueRO.getMkey())!=null){
                throw new RuntimeException("键已存在");
            }
            DictValue newValue = new DictValue();
            newValue.setMkey(dictValueRO.getMkey());
            newValue.setMvalue(dictValueRO.getMvalue());
            newValue.setDictType(dictType);
            newValue.setId(null);
            dictValueService.save(newValue);
            return new HttpResult<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(e);
        }
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public HttpResult update(@RequestBody DictValueRO dictValueRO) {
        Map<String, Object> returnMap = new HashMap<>();
        try {
            if(!StringUtil.stringNotNull(dictValueRO.getId())){
                throw new RuntimeException("未查询到当前对象");
            }

            DictValue oldPM = dictValueService.queryById(DictValue.class, dictValueRO.getId());
            oldPM.setMkey(dictValueRO.getMkey());
            oldPM.setMvalue(dictValueRO.getMvalue());
            dictValueService.update(oldPM);
            return new HttpResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult(e);
        }
    }

    /**
     * 根据数据字典类型Number获取数据
     */
    @RequestMapping(value = "getByTypeModuleNumber", method = RequestMethod.POST)
    public HttpResult<List<DictValueVO>> getByTypeModuleNumber(@RequestBody DictTypeModuleNumberRO dictTypeModuleNumberRO) throws Exception {
        try {
            List<DictValueVO>  dictValueVOList = dictValueService.getByTypeModuleNumber(dictTypeModuleNumberRO.getModule(),
                    dictTypeModuleNumberRO.getNumber());
            return new HttpResult<>(dictValueVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(e);
        }
    }

    /**
     * 根据数据字典类型Number获取数据
     */
//    @RequestMapping(value = "getByTypeNumber", method = RequestMethod.POST)
//    public HttpResult<List<DictValueVO>> getByTypeNumber(@RequestBody NumberRO numberRO) throws Exception {
//        try {
//            List<DictValueVO>  dictValueVOList = dictValueService.getByTypeNumber(numberRO.getNumber());
//            return new HttpResult<>(dictValueVOList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new HttpResult<>(e);
//        }
//    }

    /**
     * 根据数据字典类型Id获取数据
     */
    @RequestMapping(value = "getByTypeId", method = RequestMethod.POST)
    public HttpResult<List<DictValueVO>>   getByTypeId(@RequestBody IdRO idRO) throws Exception {
        try {
            List<DictValueVO>  dictValueVOList = dictValueService.getByTypeId(idRO.getId());
            return new HttpResult<>(dictValueVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(e);
        }
    }

    /**
     * 根据数据字典类型Id获取数据
     */
    @RequestMapping(value = "pageByTypeId", method = RequestMethod.POST)
    public HttpResult<PageResult<DictValueVO>>   pageByTypeId(PageCriteria pageCriteria, String typeId) throws Exception {
        try {
            List<SearchCondition> searchConditions = new ArrayList<>();
            searchConditions.add(new SearchCondition("dictType.id",typeId,"="));
            PageResult<DictValueVO> dictValueVOList = dictValueService.listPage(DictValue.class,DictValueVO.class,pageCriteria,searchConditions);
            return new HttpResult<>(dictValueVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(e);
        }
    }
}
