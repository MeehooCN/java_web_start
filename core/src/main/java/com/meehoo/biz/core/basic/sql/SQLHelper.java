package com.meehoo.biz.core.basic.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zc
 * @date 2020-08-06
 */
@Component
public class SQLHelper {
    public static final String Result_Count = "sCount";
    public static final String Result_Date = "sDate";

    private final SqlExecute sqlExecute;
    private SQLBuilder sqlBuilder;
    private final Map<String,String> entity_table = new HashMap<>(50);

    @Autowired
    public SQLHelper(SqlExecute sqlExecute) {
        this.sqlExecute = sqlExecute;
    }

    public SQLBuilder createNewQuery(){
        sqlBuilder = new SQLBuilder(this);
        return sqlBuilder;
    }

    public SQLBuilder createNewQuery(Class entityClass){
        String className = entityClass.getName();
        String tableName;
        if (entity_table.containsKey(className)){
            tableName = entity_table.get(className);
        }else{
            Table table = (Table) entityClass.getAnnotation(Table.class);
            tableName = table.name();
        }

        sqlBuilder = new SQLBuilder(this);
        sqlBuilder.from(tableName);
        return sqlBuilder;
    }

    public SqlExecute getSqlExecute() {
        return sqlExecute;
    }
}
