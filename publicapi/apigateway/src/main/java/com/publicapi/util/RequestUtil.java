package com.publicapi.util;

import java.net.URI;

public class RequestUtil {

    public  static String getApiCode(URI uri) {
        String path = uri.getPath();
        int index = path.indexOf('/', 1);
        return path.substring(1,index);
    }
}
