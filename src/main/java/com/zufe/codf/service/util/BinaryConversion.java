package com.zufe.codf.service.util;

import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * @author 应涛
 * @date 2021/9/7
 * @function： 进制转换工具类
 */
public class BinaryConversion {


    /**
     * 十六进制转为十进制
     */
    public static String getHexToTen(String hex) {
        return String.valueOf(Integer.parseInt(hex, 16));
    }

    /**
     * 将十六进制字符串转为字符串类型Ascll码
     */
    public static String getHexToAscllString(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
            temp.append(decimal);
        }
        return getFileAddSpace(sb.toString());
    }

    /**
     * 每两位之间插入空格
     */
    public static String getFileAddSpace(String replace) {
        String regex = "(.{2})";
        replace = replace.replaceAll(regex, "$1 ");
        return replace;
    }


}