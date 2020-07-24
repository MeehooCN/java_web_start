package com.meehoo.biz.common.util;

/**
 * @author zc
 * @date 2020-01-03
 */
public class StringUtil {
    public static boolean stringNotNull(String string) {
        if ((null == string) || (string.trim().equals(""))) {
            return false;
        }
        return true;
    }

    public static boolean stringIsNull(String string) {
        return !stringNotNull(string);
    }

    public static String getNonnullString(String string){
        if (stringNotNull(string)){
            return string;
        }
        return "";
    }

    // 数字转特定位数的编码
    public static String numberToCode(int number,int codeLength){
        String numberStr = String.valueOf(number);
        if (numberStr.length()<codeLength){
            StringBuilder sb = new StringBuilder();
            for (int i=0;i<codeLength-numberStr.length();i++){
                sb.append("0");
            }
            sb.append(numberStr);
            numberStr = sb.toString();
        }
        return numberStr;
    }
}
