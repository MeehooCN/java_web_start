package com.meehoo.biz.core.basic.util;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.core.basic.param.PageResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建sql的工具类
 * @author zc
 * @date 2019-08-22
 */
public class SqlUtil {

    public static String objectToString(Object object){
        return String.valueOf(object);
    }

    public static double objectToDouble(Object object){
        return Double.parseDouble(String.valueOf(object));
    }

    public static int objectToInt(Object object){
        return Integer.parseInt(String.valueOf(object));
    }

    public static Date objectToDate(Object object){
        if (object == null)
            return null;
        if (object instanceof Date){
            return (Date) object;
        }
        else if (object instanceof String){
            try {
                return DateUtil.stringToDate((String) object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> PageResult<T> listToPage(List<T> list, int page, int row){
        Long total = BaseUtil.collectionNotNull(list)?list.size():0l;
        int startIndex = (page - 1)*row;
        int endIndex = startIndex + row;
        if (startIndex > total - 1){
            return new PageResult<>(total,new ArrayList<>());
        }else if (endIndex > total - 1){
//            endIndex = (int)(total - 1);
            endIndex = total.intValue();
        }
        return new PageResult<>(total,list.subList(startIndex,endIndex));
    }

    public static String getYMDSelect(String field){
        String select =  "CONCAT(year(field),'-',\n" +
                "IF(LENGTH(month(field))=1,CONCAT('0',month(field)),month(field)),'-',\n" +
                "IF(LENGTH(day  (field))=1,CONCAT('0',day  (field)),day  (field)))";
        return select.replaceAll("field",field);
    }
}
