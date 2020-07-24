package com.meehoo.biz.core.basic.param;

import com.meehoo.biz.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zc
 * @date 2020-04-20
 */
public class SearchConditionBuilder {
    private List<SearchCondition> searchCondition;

    public SearchConditionBuilder(){
        searchCondition = new ArrayList<>();
    }

    public SearchConditionBuilder add(String name, String operand, String value){
        if (StringUtil.stringNotNull(name)&& StringUtil.stringNotNull(value)){
            searchCondition.add(new SearchCondition(name, operand, value));
        }
        return this;
    }

    public SearchConditionBuilder addEq(String name, String value){
        return add(name,"=",value);
    }

    public SearchConditionBuilder addGe(String name, String value){
        return add(name,">=",value);
    }

    public SearchConditionBuilder addLe(String name, String value){
        return add(name,"<=",value);
    }

    public SearchConditionBuilder addLikeAny(String name, String value){
        return add(name,"like","%"+value+"%");
    }

    public SearchConditionBuilder addLikeStart(String name, String value){
        return add(name,"like","%"+value);
    }



    public List<SearchCondition> toList(){
        return searchCondition;
    }
}
