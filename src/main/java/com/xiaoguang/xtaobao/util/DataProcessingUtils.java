package com.xiaoguang.xtaobao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户输入数据的处理类
 * 判断用户输入的用户名是为手机号或者email
 * Created by 11655 on 2016/10/20.
 */

public class DataProcessingUtils {

    /**
     * 判断是否为手机号码
     * @param inputText 输入的数据
     * @return true 为手机号码
     */
    public static boolean isPhone(String inputText) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }

    /**
     * 判断是否为email
     * @param email 输入的数据
     * @return true 为邮箱 false 为其他数据
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
