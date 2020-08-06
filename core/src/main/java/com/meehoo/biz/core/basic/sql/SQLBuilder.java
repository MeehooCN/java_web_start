package com.meehoo.biz.core.basic.sql;


import com.meehoo.biz.common.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author zc
 * @date 2020-08-05
 */
public class SQLBuilder {
    private String select;
    private String from;
    private String where;
    private String groupBy;
    private String orderBy;
    private SQLHelper sqlHelper;

    public SQLBuilder(SQLHelper sqlHelper) {
        this.select = "SELECT ";
        this.from = "FROM ";
        this.where = "WHERE ";
        this.groupBy = "GROUP BY ";
        this.orderBy = "ORDER BY ";
        this.sqlHelper = sqlHelper;
    }

    public SQLBuilder createGroupBy(String... groupByParams){
        StringBuilder selectSB = new StringBuilder(" COUNT(*) as count");
        StringBuilder groupBySB = new StringBuilder();
        for (String param : groupByParams) {
            selectSB.append(",");
            selectSB.append(param);
            selectSB.append(" ");

            groupBySB.append(param);
            groupBySB.append(" ,");
        }
//        selectSB.deleteCharAt(selectSB.length()-1);
        groupBySB.deleteCharAt(groupBySB.length()-1);
        select+= selectSB.toString();
        groupBy+= groupBySB.toString();
        return this;
    }

    public SQLBuilder addFrom(String tableName){
        from+= " "+tableName+" ";
        return this;
    }

    public SQLBuilder addWhereParamsEQ(String param, String value){
        if (StringUtil.stringNotNull(value)){
            where+= " "+param+" = '"+value+"' ";
        }
        return this;
    }

    public SQLBuilder addWhereParamsLikeStart(String param, String value){
        if (StringUtil.stringNotNull(value)){
            where+= " "+param+" LIKE '"+value+"%' ";
        }
        return this;
    }

    public String toSQL(){
        String sql = select+from;
        if (!"WHERE ".equals(where)){
            sql+=where;
        }
        if (!"GROUP BY ".equals(groupBy)){
            sql+=groupBy;
        }
        if (!"ORDER BY ".equals(orderBy)){
            sql+=orderBy;
        }
        return sql;
    }

    public List<Map<String,Object>> exe(){
        String sql = toSQL();
        List<Map<String, Object>> result = sqlHelper.getSqlExecute().queryForEntry(sql);
        destroy();
        return result;
    }

    public void destroy(){
        select = null;
        where = null;
        from = null;
        groupBy = null;
        orderBy = null;
        sqlHelper=null;
    }
}
