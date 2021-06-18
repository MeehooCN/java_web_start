package com.meehoo.biz.web.controller.basic.setting;

//import com.meehoo.biz.common.util.PageResult;
//import com.meehoo.biz.core.sysmanage.auth.ro.common.IdListRO;
//import com.meehoo.biz.core.sysmanage.auth.ro.common.PageRO;
//import com.meehoo.biz.core.sysmanage.base.criteria.SearchCondition;
//import com.meehoo.biz.core.sysmanage.setting.datadict.domain.OptLog;
//import com.meehoo.biz.core.sysmanage.setting.datadict.service.IOptLogService;
//import com.meehoo.biz.core.sysmanage.setting.datadict.vo.OptLogVO;
//import com.meehoo.biz.core.sysmanage.setting.param.HttpResult;
//import com.meehoo.biz.web.controller.common.BaseController;
import com.meehoo.biz.core.basic.domain.setting.OptLog;
import com.meehoo.biz.core.basic.param.*;
import com.meehoo.biz.core.basic.ro.bos.IdListRO;
import com.meehoo.biz.core.basic.ro.bos.PageRO;
import com.meehoo.biz.core.basic.service.setting.IOptLogService;
import com.meehoo.biz.core.basic.service.setting.PageOptLogRO;
import com.meehoo.biz.core.basic.vo.setting.OptLogVO;
import com.meehoo.biz.web.controller.basic.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wangjian on 2017-12-06.
 */
@Api(tags = "操作日志管理")
@RestController
@RequestMapping("sysmanage/optlog")
public class OptLogController extends BaseController<OptLog,OptLogVO>
{
    private final IOptLogService optLogService;

    @Autowired
    public OptLogController(IOptLogService optLogService){
        super();
        this.optLogService = optLogService;
    }

//    @Override
    @PostMapping("page")
    public HttpResult<PageResult<OptLogVO>> page(@RequestBody PageOptLogRO pagePO){
        SearchConditionBuilder builder = new SearchConditionBuilder().addLikeAny("userName", pagePO.getUserName());
        return super.page(builder.toList(),pagePO.toPageCriteria());
    }

    @ApiOperation("批量删除日志")
    @PostMapping("batchDelete")
    public HttpResult batchDelete(@RequestBody IdListRO idListRO) {
        optLogService.batchDelete(idListRO.getIdList());
        return HttpResult.success();
    }
}
