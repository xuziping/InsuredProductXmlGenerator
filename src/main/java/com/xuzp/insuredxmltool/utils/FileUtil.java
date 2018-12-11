package com.xuzp.insuredxmltool.utils;

import java.io.InputStream;

/**
 * @author za-xuzhiping
 * @Date 2018/12/11
 * @Time 12:02
 */
public class FileUtil {

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }
}
