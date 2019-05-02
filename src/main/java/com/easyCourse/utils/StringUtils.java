package com.easyCourse.utils;

import java.security.MessageDigest;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isEmpty(String s){
        return s == null || "".equals(s.trim());
    }

    public static boolean isEmpty(Object o){
        if(o == null) {
            return true;
        }
        if(o instanceof Integer)
            return (Integer) o == 0;
        else{
            return isEmpty(o.toString());
        }
    }

    public static String UUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String MD5(String str) {
        String result = null;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");

            md5.update((str).getBytes("UTF-8"));
            byte[] b = md5.digest();

            int i;
            StringBuffer buf = new StringBuffer();

            for(int offset=0; offset<b.length; offset++){
                i = b[offset];
                if(i<0){
                    i+=256;
                }
                if(i<16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     * @param sourceDate
     * @param formatLength
     * @return 重组后的数据
     */
    public static String frontCompWithZore(int sourceDate,int formatLength)
    {
        /*
         * 0 指前面补充零
         * formatLength 字符总长度为 formatLength
         * d 代表为正数。
         */
        String newString = String.format("%0"+formatLength+"d", sourceDate);
        return  newString;
    }

    public String numberToChar(int number) {
        return String.valueOf((char) number);
    }

    /**
     * 去除字符串中的特殊符号
     *
     * @param str
     * @return
     */
    public static String filtSymbol(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}:;\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’,.。，、？]";
        return Pattern.compile(regEx).matcher(str).replaceAll("").trim();
    }

    public static String getRandom() {
        String num = "";
        for (int i = 0 ; i < 6 ; i ++) {
            num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
        }
        return num;
    }


    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }
}


