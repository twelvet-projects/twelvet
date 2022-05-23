package com.twelvet.server.dfs.Util;

import com.twelvet.framework.utils.text.UUID;

public class StringUtil {

    public static String getRandomImgName(String fileName) throws IllegalAccessException {
        int index = fileName.lastIndexOf(".");
        if((fileName == null || fileName.isEmpty() || index == -1)){
            throw new IllegalAccessException();
        }

        String suffix  = fileName.substring(index);

        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        String path = uuid + suffix;
        return path;

    }
}
