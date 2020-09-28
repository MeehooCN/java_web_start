package com.meehoo.biz.core.basic.sql;


import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zc
 * @date 2020-08-05
 */
public class SQLBuilder {
    private StringBuilder select;
    private StringBuilder from;
    private StringBuilder where;
    private StringBuilder groupBy;
    private StringBuilder orderBy;
    private SQLHelper sqlHelper;

    public SQLBuilder(SQLHelper sqlHelper) {
        this.select = new StringBuilder("SELECT ");
        this.from = new StringBuilder("FROM ");
        this.where = new StringBuilder("WHERE ");
        this.groupBy = new StringBuilder("GROUP BY ");
        this.orderBy = new StringBuilder("ORDER BY ");
        this.sqlHelper = sqlHelper;
    }

    public SQLBuilder groupBy(String groupByParams){
        if (groupBy.length()!=9){
            groupBy.append(" ,");
        }
        groupBy.append(groupByParams);
        return this;
    }

    public SQLBuilder groupByIf(boolean isFirst,String groupBy1,String groupBy2){
        if (isFirst){
            return groupBy(groupBy1);
        }else{
            return groupBy(groupBy2);
        }
    }

    public SQLBuilder groupByYMD(String groupByParams){
        return groupBy(SqlUtil.getYMDGroupBy(groupByParams));
    }

    public SQLBuilder groupByYM(String groupByParams){
        groupBy("year("+groupByParams+")");
        groupBy("month("+groupByParams+")");
        return this;
    }

    public SQLBuilder select(String param){
        if (select.length()!=7){
            select.append(",");
        }
        select.append(param);
        select.append(" ");
        return this;
    }

    public SQLBuilder selectCount(){
        return select("COUNT(*) AS " + SQLHelper.Result_Count);
    }

    public SQLBuilder selectIf(boolean isFirst,String p1,String p2){
        if (isFirst){
            return select(p1);
        }else{
            return select(p2);
        }
    }

    public SQLBuilder from(String tableName){
        from.append(" ");
        from.append(tableName);
        from.append(" ");
        return this;
    }

    public SQLBuilder whereParamsGE(String param,String value){
        if (StringUtil.stringNotNull(value)){
            if (where.length()!=6){
                where.append(" AND ");
            }
            where.append(" ");
            where.append(param);
            where.append(" >= '");
            where.append(value);
            where.append("' ");
        }
        return this;
    }

    public SQLBuilder whereParamsLT(String param,String value){
        if (StringUtil.stringNotNull(value)){
            if (where.length()!=6){
                where.append(" AND ");
            }
            where.append(" ");
            where.append(param);
            where.append(" < '");
            where.append(value);
            where.append("' ");
        }
        return this;
    }

    public SQLBuilder whereParamsEQ(String param,String value){
        if (StringUtil.stringNotNull(value)){
            if (where.length()!=6){
                where.append(" AND ");
            }
            where.append(" ");
            where.append(param);
            where.append(" = '");
            where.append(value);
            where.append("' ");
        }
        return this;
    }

    public SQLBuilder whereParamsLikeStart(String param,String value){
        if (StringUtil.stringNotNull(value)){
            if (where.length()!=6){
                where.append(" AND ");
            }
            where.append(" ");
            where.append(param);
            where.append(" LIKE '");
            where.append(value);
            where.append("%' ");
        }
        return this;
    }

    public SQLBuilder whereParamsIn(String param,Collection<String> values){
        if (BaseUtil.collectionNotNull(values)){
            if (where.length()!=6){
                where.append(" AND ");
            }
            where.append(" ");
            where.append(param);
            where.append(" IN ");
            where.append(SqlUtil.getInParam(values));
            where.append(" ");
        }
        return this;
    }

    public String toSQL(){
        String sql = check(select);
        sql+=from;
        String whereStr = where.toString();
        if (!"WHERE ".equals(whereStr)){
            sql+=whereStr;
        }

        String groupByStr = check(groupBy);
        if (!"GROUP BY ".equals(groupByStr)){
            sql+=groupByStr;
        }
        String orderByStr = check(orderBy);
        if (!"ORDER BY ".equals(orderByStr)){
            sql+=orderByStr;
        }
        return sql;
    }

    private String check(StringBuilder sb){
        if (','==sb.charAt(sb.length()-1)){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    public List<Map<String,Object>> exe(){
        String sql = toSQL();
        try {
            List<Map<String, Object>> result = sqlHelper.getSqlExecute().queryForEntry(sql);
            destroy();
            return result;
        }catch (Exception e){
            System.out.println(sql);
            throw e;
        }
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
