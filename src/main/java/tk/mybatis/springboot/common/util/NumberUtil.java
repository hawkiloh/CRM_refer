package tk.mybatis.springboot.common.util;


import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NumberUtil {
    private static Logger logger = Logger.getLogger(NumberUtil.class);

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;
    private static final int DEF_DECIMAL_SCALE = 2;
    private static int max = Integer.MAX_VALUE;   // 求出整型的最大值
    private static int min = Integer.MIN_VALUE;   // 求出整型的最小值

    // 私有化构造方法，不能实例化
    private NumberUtil() {
    }

    /**
     * 判断是否能转化为数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 求数组中的最大值
     *
     * @param arg
     */
    public static int getMaxToArray(int[] arg) {
        int max = arg[0];
        for (int i = 1; i < arg.length; i++) {
            if (max < arg[i]) {
                max = arg[i];
            }
        }
        return max;
    }

    /**
     * 求数组中的最小值
     *
     * @param arg
     */
    public static int getMinToArray(int[] arg) {
        int min = arg[0];
        for (int i = 1; i < arg.length; i++) {
            if (min > arg[i]) {
                min = arg[i];
            }
        }
        return min;
    }

    /**
     * 获取int整数最大值
     *
     * @param
     */
    public int getMaxIntNumber() {
        return max;
    }

    /**
     * 获取int整数最小值
     *
     * @param
     */
    public int getMinIntNumber() {
        return min;
    }


    /**
     * 相加
     *
     * @param values
     * @return
     */
    public static BigDecimal add(BigDecimal... values) {

        BigDecimal result = initBigDecimal(BigDecimal.ZERO, 4);
        if (values != null && values.length > 0) {
            for (BigDecimal value : values) {
                value = initBigDecimal(value, 4);
                if (value != null) {
                    result = result.add(value);
                }
            }
        }

        return initBigDecimal(result, 4);
    }

    /**
     * 相加
     *
     * @param values
     * @return
     */
    public static BigDecimal add(int scale, Object... values) {

        BigDecimal result = initBigDecimal(BigDecimal.ZERO, scale);
        if (values != null && values.length > 0) {
            for (Object value : values) {

                result = result.add(toDecimal(value, scale));
            }
        }

        return initBigDecimal(result, scale);
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        if (!isNumeric(String.valueOf(v1))) {
            v1 = 0.0;
        }
        if (!isNumeric(String.valueOf(v2))) {
            v2 = 0.0;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(String v1, String v2) {
        BigDecimal b1 = checkBigDecimalString(v1);
        BigDecimal b2 = checkBigDecimalString(v2);
        return b1.add(b2);
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        if (!isNumeric(String.valueOf(v1))) {
            v1 = 0.0;
        }
        if (!isNumeric(String.valueOf(v2))) {
            v2 = 0.0;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(String v1, String v2) {
        BigDecimal b1 = checkBigDecimalString(v1);
        BigDecimal b2 = checkBigDecimalString(v2);
        return b1.subtract(b2);
    }

    /**
     * 相减
     *
     * @param values
     * @return
     */
    public static BigDecimal sub(int scale, BigDecimal... values) {

        BigDecimal result = null;
        if (values != null && values.length > 0) {
            result = initBigDecimal(BigDecimal.ZERO, scale);
            boolean isFirst = true; // 是否第一个数

            for (BigDecimal value : values) {
                value = initBigDecimal(value, scale);
                if (value != null) {
                    if (isFirst) {
                        result = result.add(value);
                        isFirst = false;
                    } else {
                        result = result.subtract(value);
                    }
                }

            }
        }

        return result;
    }

    public static BigDecimal mul(Object... objs) {

        BigDecimal result = null;
        if (objs != null && objs.length > 0) {
            result = new BigDecimal(1L);
            for (Object obj : objs) {
                result = result.multiply(toDecimal(obj, 4));
            }

        } else {
            result = initBigDecimal(BigDecimal.ZERO, 4);
        }

        return result;
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        if (!isNumeric(String.valueOf(v1))) {
            v1 = 0.0;
        }
        if (!isNumeric(String.valueOf(v2))) {
            v2 = 0.0;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(String v1, String v2) {
        BigDecimal b1 = checkBigDecimalString(v1);
        BigDecimal b2 = checkBigDecimalString(v2);
        return b1.multiply(b2);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale（小数点后保留几位）必须是一个正整数或零");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div2(Object v1, Object v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale（小数点后保留几位）必须是一个正整数或零");
        }
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return initBigDecimal(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP), scale);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale（小数点后保留几位）必须是一个正整数或零");
        }
        BigDecimal b1 = checkBigDecimalString(v1);
        BigDecimal b2 = checkBigDecimalString(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale（小数点后保留几位）必须是一个正整数或零");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double roundString(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale（小数点后保留几位）必须是一个正整数或零");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将指点的数字对象按照指点的规则格式化, 例如:###.00,
     *
     * @param format String 格式
     * @param obj    Double 需转换的Double类型数据
     * @return 格式化后的字符串
     */
    public static String format(String format, double obj) {
        DecimalFormat formater = new DecimalFormat(format);
        return formater.format(obj);
    }

    /**
     * 将指点的数字对象按照指点的规则格式化, 例如:###.00,
     *
     * @param format String
     * @param obj    Float 需转换的Float类型数据
     * @return 格式化后的字符串
     */
    public static String format(String format, float obj) {
        DecimalFormat formater = new DecimalFormat(format);
        return formater.format(obj);
    }

    /**
     * 获取指定位数的随机数
     *
     * @param size int 位数
     * @return 指定位数的随机数 long
     */
    public static long getRandom(int size) {
        Double value = (Math.random() * Math.pow(10, size));
        return value.longValue();
    }

    /**
     * @return 5位随机数的号码，补零原则
     */
    public static String getRandomString() {
        Double value = (Math.random() * Math.pow(10, 5));
        String str = value.longValue() + "";
        if (str.length() != 5) {
            String temp = "";
            for (int i = 0; i < 5 - str.length(); i++) {
                temp = temp + "0";
            }
            str = temp + str;
        }
        return str;
    }

    /**
     * 判断是否为整数
     *
     * @param value
     * @return
     */
    public static boolean isInteger(String value) {
        try {
            Integer.valueOf(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /* */

    /**
     * 判断是否为数字
     *
     * @param
     * @return
     *//*
        public static boolean isNumeric(String value) {
            try {
                Double.valueOf(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }*/
    public static Long createLong(String longValue) {
        Long result;
        try {
            result = Long.parseLong(longValue);
        } catch (NumberFormatException e) {
            return null;
        }
        return result;
    }

    public static Double createDouble(String doubleValue) {
        Double result;
        try {
            result = Double.parseDouble(doubleValue);
        } catch (NumberFormatException e) {
            return 0D;
        }
        return result;
    }

    public static Integer createInteger(String intValue) {
        Integer result;
        try {
            result = Integer.parseInt(intValue);
        } catch (NumberFormatException e) {
            return null;
        }
        return result;
    }

    public static Short createShort(String shortValue) {
        Short result;
        try {
            result = Short.parseShort(shortValue);
        } catch (NumberFormatException e) {
            return null;
        }
        return result;
    }

    public static String formatInteger(Integer integerValue, String defaultValue) {
        if (integerValue == null) {
            return defaultValue;
        } else {
            return String.valueOf(integerValue);
        }
    }

    public static String formatLong(Long longValue, String defaultValue) {
        if (longValue == null) {
            return defaultValue;
        } else {
            return String.valueOf(longValue);
        }
    }

    public static String formatShort(Short shortValue, String defaultValue) {
        if (shortValue == null) {
            return defaultValue;
        } else {
            return String.valueOf(shortValue);
        }

    }

    public static BigDecimal toDecimal(String value) {
        return toDecimal(value, 2);
    }

    public static Double toDouble(BigDecimal value) {
        if (value == null) {
            return 0D;
        }
        return value.doubleValue();
    }

    public static Double toDouble(String value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return 0D;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            //logger.info(">>>toDouble value: " + value);
            return 0D;
        }

    }

    public static Double toDouble(Object obj) {
        if (obj == null || StringUtil.isNullOrEmpty(obj.toString())) {
            return null;
        }
        try {
            return Double.parseDouble(obj.toString());
        } catch (NumberFormatException e) {
            //logger.info(">>>toDouble value: " + obj.toString());
            return null;
        }
    }

    public static Long toLong(Object obj) {
        if (obj == null || StringUtil.isNullOrEmpty(obj.toString())) {
            return null;
        }
        try {
            return Long.parseLong(obj.toString());
        } catch (NumberFormatException e) {
            // logger.info(">>>toLong value: " + obj.toString());
            return null;
        }
    }

    public static float toFloat(String value) {

        Double result = toDouble(value);
        return result.floatValue();
    }

    public static int toInteger(String value) {
        Double result = toDouble(value);
        return result.intValue();
    }

    /**
     * 初始化BigDeicmal
     *
     * @param value
     * @param scale
     * @return
     */
    public static BigDecimal initBigDecimal(BigDecimal value, int scale) {
        if (value == null) {
            return new BigDecimal(0L);
        } else {
            // 当小数点位数大于指定位数时，作四舍五入
            String valueStr = value.toString();
            if (valueStr.indexOf(".") > -1) {
                // 去掉小数后面多余的零
                String scaleStr = valueStr.substring(valueStr.indexOf(".") + 1);
                while (scaleStr.endsWith("0")) {
                    scaleStr = scaleStr.substring(0, scaleStr.length() - 1);
                }
                // 若指定的位数长度大于值的小数点的位数长度时，
                scale = scale > scaleStr.length() ? scaleStr.length() : scale;

                if (valueStr.length() - valueStr.indexOf(".") - 1 > scale) {
                    value = value.setScale(scale, BigDecimal.ROUND_HALF_UP);
                    value = initBigDecimal(value, scale);
                }
            }
            return value;
        }
    }

    public static BigDecimal initBigDecimal(BigDecimal value) {
        return initBigDecimal(value, DEF_DECIMAL_SCALE);
    }

    public static BigDecimal toDecimal(Object obj, int scale) {
        if (obj == null || StringUtil.isNullOrEmpty(obj.toString())) {
            return initBigDecimal(BigDecimal.ZERO, scale);
        } else {
            return initBigDecimal(new BigDecimal(obj.toString()), scale);
        }
    }

    /**
     * 检查BigDecimal是否为空，是则初始化
     *
     * @param value
     * @return
     * @author LuPindong
     */
    public static BigDecimal checkBigDecimal(BigDecimal value) {
        return checkBigDecimal(value, 2);
    }

    /**
     * 检查BigDecimal是否为空，是则初始化
     *
     * @param value
     * @param scale
     * @return
     * @author LuPindong
     */
    public static BigDecimal checkBigDecimal(BigDecimal value, Integer scale) {
        if (value == null) {
            return initBigDecimal(BigDecimal.ZERO, scale);
        } else {
            return initBigDecimal(value, scale);
        }
    }

    public static Boolean equalsBigDecimal(BigDecimal objValue, BigDecimal otherValue) {
        objValue = checkBigDecimal(objValue);
        otherValue = checkBigDecimal(otherValue);
        if (!objValue.equals(otherValue)) {
            return false;
        }
        return true;
    }

    /**
     * 检查BigDecimal String是否为空，是则初始化
     *
     * @param value
     * @return
     * @author LuPindong
     */
    public static BigDecimal checkBigDecimalString(String value) {
        return checkBigDecimalString(value, 2);
    }

    /**
     * 检查BigDecimal String是否为空，是则初始化
     *
     * @param value
     * @param scale
     * @return
     * @author LuPindong
     */
    public static BigDecimal checkBigDecimalString(String value, Integer scale) {
        if (StringUtil.isNullOrEmpty(value)) {
            return initBigDecimal(BigDecimal.ZERO, scale);
        } else {
            return initBigDecimal(new BigDecimal(value), scale);
        }
    }

    /**
     * 初始化String类型的数字
     *
     * @param value
     * @return
     * @author LuPindong
     */
    public static String initStringNum(String value) {
        return initStringNum(value, 2);
    }

    /**
     * 初始化String类型的数字
     *
     * @param value
     * @param scale
     * @return
     * @author LuPindong
     */
    public static String initStringNum(String value, Integer scale) {
        if (StringUtil.isNullOrEmpty(value)) {
            return String.valueOf(new BigDecimal(0L).setScale(scale, BigDecimal.ROUND_HALF_UP));
        } else {
            return String.valueOf(new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP));
        }
    }

    public static Boolean equalsBigDecimalString(String objValue, String otherValue) {
        BigDecimal objValue1 = checkBigDecimalString(objValue);
        BigDecimal otherValue1 = checkBigDecimalString(otherValue);
        if (!objValue1.equals(otherValue1)) {
            return false;
        }
        return true;
    }

    public static String getPercentStr(BigDecimal decValue) {

        if (decValue != null) {
            String StrValue = NumberUtil.format("###.00", decValue.multiply(new BigDecimal(100.0000D)).doubleValue());
            return StrValue + "%";
        } else {
            return "";
        }
    }

    public static String percentFormat(Object value, int scale) {
        if (value != null && StringUtil.isNullOrEmpty(value.toString())) {
            if (value.toString().indexOf("%") > -1) {
                return value.toString();
            }
            int newScale = scale < 2 ? 0 : scale - 2;
            String result = toDecimal(value, scale).multiply(new BigDecimal(100)).setScale(newScale, BigDecimal.ROUND_HALF_UP).toString();
            return result + "%";
        } else {
            return "";
        }
    }

    public static int toInteger(Object value) {
        Double result = toDouble(value);
        return result.intValue();
    }


    public static void main(String[] arg) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 56, 324, -5};
        int ma = NumberUtil.getMinToArray(a);
        //int a=max;
        //int b=Integer.parseInt(a);
        System.out.println(ma);
    }


}
