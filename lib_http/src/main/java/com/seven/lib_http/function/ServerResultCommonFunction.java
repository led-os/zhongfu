package com.seven.lib_http.function;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.seven.lib_http.retrofit.HttpResponse;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */
public class ServerResultCommonFunction implements Function<HttpResponse, Object> {
    @Override
    public Object apply(@NonNull HttpResponse response) throws Exception {
        Logger.i(response.toString());
//        if (response.getCode()!=0) {
//            throw new ServerException(response.getCode(), response.getMessage());
//        }
        return new Gson().toJson(response.toString());
    }
}
