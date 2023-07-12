package com.publicapi.apimanage.common.utils;

import cn.hutool.core.util.IdUtil;

public class CodeUtil {

        public static String generate() {
            return IdUtil.nanoId(6);
        }
}
