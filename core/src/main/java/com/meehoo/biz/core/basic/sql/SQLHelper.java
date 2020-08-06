package com.meehoo.biz.core.basic.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @date 2020-08-06
 */
@Component
public class SQLHelper {
    private final SqlExecute sqlExecute;
    private SQLBuilder sqlBuilder;


    @Autowired
    public SQLHelper(SqlExecute sqlExecute) {
        this.sqlExecute = sqlExecute;
    }

    public SQLBuilder createNewQuery(){
        sqlBuilder = new SQLBuilder(this);
        return sqlBuilder;
    }

    public SqlExecute getSqlExecute() {
        return sqlExecute;
    }
}
