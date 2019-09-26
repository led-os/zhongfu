package com.seven.lib_common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2018/7/24
 */

public class GsonUtils {

    public static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}
