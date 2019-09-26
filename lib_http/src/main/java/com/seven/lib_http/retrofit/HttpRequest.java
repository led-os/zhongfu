package com.seven.lib_http.retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class HttpRequest {

    public static final String appKey = "1889b37351288";
    private static final String k_key = "key";

    /**
     * get base request
     */
    public static final Map<String, Object> getRequest() {
        Map<String, Object> map = new HashMap<>();
        map.put(k_key, appKey);
        return map;
    }


}
