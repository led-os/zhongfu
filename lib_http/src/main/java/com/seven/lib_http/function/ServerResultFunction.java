package com.seven.lib_http.function;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.seven.lib_http.exception.ServerException;
import com.seven.lib_http.retrofit.HttpResponse;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */
public class ServerResultFunction implements Function<HttpResponse, Object> {
    @Override
    public Object apply(@NonNull HttpResponse response) throws Exception {
        Logger.i(response.toString());
        if (response.getCode() != 1) {
            if (response.getCode() == SSDK.getInstance().getsConfig().getEventCode()) {
                EventBus.getDefault().post(new MessageEvent(SSDK.getInstance().getsConfig().getEventCode(), ""));
            } else {
                throw new ServerException(response.getCode(), response.getMessage());
            }
        }
        return new Gson().toJson(response.getData());
    }
}
