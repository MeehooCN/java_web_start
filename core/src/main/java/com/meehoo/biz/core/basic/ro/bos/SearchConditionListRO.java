package com.meehoo.biz.core.basic.ro.bos;


import com.meehoo.biz.core.basic.param.SearchCondition;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/03.
 */
@Getter
@Setter
public class SearchConditionListRO {
    private List<SearchCondition> searchConditionList;

    public List<SearchCondition> getSearchConditionList() {
        return searchConditionList;
    }

    public void setSearchConditionList(List<SearchCondition> searchConditionList) {
        this.searchConditionList = searchConditionList;
    }
}
