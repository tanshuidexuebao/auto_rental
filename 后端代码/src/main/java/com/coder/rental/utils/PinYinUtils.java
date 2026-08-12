package com.coder.rental.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.pinyin.PinyinUtil;

/**
 * 汉字转拼音工具类（首字母大写）
 */
public class PinYinUtils {

    /**
     * 将中文文本转换为大写拼音（不带声调），无法识别的非中文字符保留原样
     *
     * @param str 中文字符串
     * @return 转换后的大写拼音
     */
    public static String getPinYin(String str) {
        if (StrUtil.isBlank(str)) {
            return str;
        }
        return PinyinUtil.getPinyin(str, "").toUpperCase();
    }
}
