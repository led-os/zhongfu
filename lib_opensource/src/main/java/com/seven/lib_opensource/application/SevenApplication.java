package com.seven.lib_opensource.application;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/24
 */

public class SevenApplication extends Application {

    public static SevenApplication application;

    private static RefWatcher refWatcher;

    public String token;

    public boolean isLoggerDebug = true;

    public static SevenApplication getInstance() {
        return application;
    }

    public static RefWatcher getRefWatcher() {
        return getInstance().refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init();
        initApp();

    }

    private void init() {

        refWatcher = LeakCanary.install(this);
//        refWatcher=installLeakCanary(); release version

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // if the thread information is displayed, the default is true
                .methodCount(2)         // the number of methods shown is 2
                .methodOffset(7)        // hide the internal method call to offset, default is 5
//                .logStrategy(customLog) // change the logging policy to print.
                .tag("===seven===")// setting tag , default PRETTY_LOGGER
                .build();

        if (isLoggerDebug) {
            AndroidLogAdapter logAdapter = new AndroidLogAdapter(formatStrategy);
            Logger.addLogAdapter(logAdapter);
        }
//        Logger.clearLogAdapters();//the clear adapter logger with release version
    }

    protected RefWatcher installLeakCanary() {
        return RefWatcher.DISABLED;
    }

    public void initApp() {

        if (isLoggerDebug) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        } else {

        }

        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }
}
