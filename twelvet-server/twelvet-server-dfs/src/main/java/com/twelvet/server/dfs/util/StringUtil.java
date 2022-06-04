package com.twelvet.server.dfs.util;

import com.twelvet.framework.utils.text.UUID;

public class StringUtil {

    public static String getRandomImgName(String fileName) throws IllegalAccessException {
        int index = fileName.lastIndexOf(".");
        if (fileName.isEmpty() || index == -1) {
            throw new IllegalAccessException();
        }

        String suffix = fileName.substring(index);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        return uuid + suffix;

    }
}
