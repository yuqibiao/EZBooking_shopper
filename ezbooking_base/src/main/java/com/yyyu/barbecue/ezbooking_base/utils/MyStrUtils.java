package com.yyyu.barbecue.ezbooking_base.utils;

/**
 * 功能：和String 相关的工具类
 *
 * @author yyyu
 * @date 2016/8/21
 */
public class MyStrUtils {

    public static  String intToWeekNum(int num){
        if (num<=6){
            return intToCNum(num);
        }else{
            return "日";
        }
    }

    /**
     * 将拉伯数字转换成中文
     *
     * @param num
     * @return
     */
    public static  String intToCNum(int num){
        String[] str={"零","一","二","三","四","五","六","七","八","九"};
        String ss[] = new String[]{"","十","百","千","万","十万","百万","千万","亿"};
        String s=String.valueOf(num);
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<s.length();i++){
            String index=String.valueOf(s.charAt(i));
            sb=sb.append(str[Integer.parseInt(index)]);
        }
        String sss=String.valueOf(sb);
        int i=0;
        for(int j=sss.length();j>0;j--){
            sb=sb.insert(j,ss[i++]);
        }
        return sb.toString();
    }

}
