package com.meehoo.biz.web.controller.basic.setting;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.setting.DataDict;
import com.meehoo.biz.core.basic.domain.setting.DictType;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.ro.bos.SearchConditionListRO;
import com.meehoo.biz.core.basic.ro.setting.DictTypeRO;
import com.meehoo.biz.core.basic.service.common.ICommonService;
import com.meehoo.biz.core.basic.service.setting.IDictTypeService;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.setting.DataDictVO;
import com.meehoo.biz.core.basic.vo.setting.DictTypeVO;
import com.meehoo.biz.web.controller.basic.common.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典 类型
 *
 * @author xg
 * @create 2017-05-11-14:45
 */
@Api(tags = "数据字典类型管理")
@RestController
@RequestMapping("/sysmanage/dictType")
public class DictTypeController extends BaseController<DictType,DictTypeVO> {
    private final IDictTypeService dictTypeService;
    private final ICommonService commonService;

    @Autowired
    public DictTypeController(IDictTypeService dictTypeService, ICommonService commonService) {
        super(dictTypeService);
        this.dictTypeService = dictTypeService;
        this.commonService = commonService;
    }

    /**
     * 根据数据字典的类型查询
     * @param idRO  id = 类型
     * @return
     */
    @RequestMapping(value = "getByType", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<List<DataDictVO>> getByType(@RequestBody IdRO idRO){
        Map<String,Object> map = new HashMap<>();
        try {
            List<DataDict> dataDictList = dictTypeService.queryListByColumn(DataDict.class,"dType",idRO.getId());
            List<DataDictVO> voList = VOUtil.convertDomainListToTempList(dataDictList,DataDictVO.class);
            return new HttpResult<>(voList);
        }catch (Exception e){
            e.printStackTrace();
            return new HttpResult<>(e);
        }
    }

    /**
     * 新增某个数据字典类型
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public HttpResult create(@RequestBody DictTypeRO dictTypeRO) {
        try {
            DictType oldType = new DictType();
            oldType.setId(null);
            boolean hasNoNumber = !StringUtil.stringNotNull(dictTypeRO.getCode());
            if (hasNoNumber) {
                oldType.setCode(commonService.getBizObjectSerialNumber("DictType"));
            }else {
                oldType.setCode(dictTypeRO.getCode());
            }
            oldType.setName(dictTypeRO.getName());
            oldType.setModule(dictTypeRO.getModule());
            dictTypeService.save(oldType);
            return new HttpResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult(e);
        }
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public HttpResult update(@RequestBody DictTypeRO dictTypeRO) {
        try {

            if(!StringUtil.stringNotNull(dictTypeRO.getId())){
                throw new RuntimeException("未查询到当前业务对象");
            }

            DictType oldPM = null;
            oldPM = dictTypeService.queryById(DictType.class, dictTypeRO.getId());
            oldPM.setCode(dictTypeRO.getCode());
            oldPM.setName(dictTypeRO.getName());
            oldPM.setModule(dictTypeRO.getModule());
            dictTypeService.update(oldPM);
            return new HttpResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult(e);
        }
    }

    /**
     * 查询所有数据字典类型
     *
     * @throws Exception
     */
    @RequestMapping(value = "getAllDictType", method = RequestMethod.POST)
    public HttpResult<List<DictTypeVO>>  getAllDictType(@RequestBody SearchConditionListRO searchConditionListRO) throws Exception {
        try {
            List<DictTypeVO> dictTypeVOList = dictTypeService.listAll(DictType.class,DictTypeVO.class,searchConditionListRO.getSearchConditionList());
            return new HttpResult<>(dictTypeVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(e);
        }
    }
}
