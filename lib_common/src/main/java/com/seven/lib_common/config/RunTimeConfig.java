package com.seven.lib_common.config;



import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.application.SevenApplication;

import java.io.File;

/**
 * 基础配置
 *
 * @author seven
 */
public class RunTimeConfig {

    /**
     * 默认路径配置
     */
    public static class PathConfig {

        /**
         * App目录
         */
        public static final String APP_PATH = File.separator + "sdcard" + File.separator + "SSDK" + File.separator;

        /**
         * 应用的data目录[/data/data/[package]/files]，此目录下的文件只有本应用可访问，用于存放一些重要的配置文件
         */
        public static final String DATA_PATH_INNER = SSDK.getInstance().getContext().getFilesDir().getAbsolutePath() + File.separator;

        /**
         * 文件下载的存放路径
         */
        public static final String DOWN_LOAD_PATH = APP_PATH + "download" + File.separator;

        public static final String DOWN_LOAD_VIDEO_PATH=DOWN_LOAD_PATH+"video"+File.separator;
        public static final String DOWN_LOAD_IMAGE_PATH=DOWN_LOAD_PATH+"image"+File.separator;

        /**
         * LOG信息保存路径
         */
        public static final String LOG_SAVE_PATH = APP_PATH + "log" + File.separator;

        /**
         *
         */
        public static final String CAMERA_PATH=APP_PATH + "camera" + File.separator;

        /**
         * 视频存放的路径
         */
        public static final String CAMERA_VIDEO_PATH = CAMERA_PATH + "video" + File.separator;
        /**
         * 图片存放路径
         */
        public static final String CAMERA_IMAGE_PATH = CAMERA_PATH + "image" + File.separator;

        /**
         * 图片存放的路径
         */
        public static final String PICTURE_PATH = APP_PATH + "picture" + File.separator;

        /**
         * 压缩图片临时保存路径
         */
        public static final String IMG_UPLOAD_TEMP_PATH = APP_PATH
                + "Image" + File.separator + "temp" + File.separator;

        /**
         * 缓存存放的路径
         */
        public static final String CACHE_PATH = APP_PATH + "cache" + File.separator;

        /**
         * web view cache
         */
        public static final String CACHE_WEB = CACHE_PATH + "webCache";

        /**
         * 临时文件存放的路径
         */
        public static final String TEMP_PATH = APP_PATH + "temp" + File.separator;

        /**
         * 插件下载的的本地保存路径
         */
        public static final String PLUG_IN_DOWNLOAD_PATH = APP_PATH + "plug_in" + File.separator;

        /**
         * 推荐应用下载的的本地保存路径
         */
        public static final String RECOMMEND_APP_DOWNLOAD_PATH = APP_PATH + "recommend_app" + File.separator;

        /**
         * 升级apk下载保存路径
         */
        public static final String APK_DOWNLOAD_PATH = APP_PATH + "apk" + File.separator;
    }

    /**
     * 默认数据库配置
     */
    public static class DbConfig {

        /**
         * 数据库名称
         */
        public static final String DB_NAME = "seven.db";

        //        public static final String DB_PATH=PathConfig.DATA_PATH_INNER;
        public static final String DB_PATH = PathConfig.APP_PATH;

        /**
         * 数据库版本
         */
        public static final int DB_VERSION = 1;

    }

    public static class EventConfig{
        public static final int LOGIN_INVALID=-9090;
        public static final int LOGIN_OUT=-8080;
    }

    /**
     * 通用配置
     */
    public static class CommonConfig {

        /**
         * 相机请求码
         */
        public static final int CAMERA_CODE = 100;

        /**
         * 男
         */
        public static final int SEX_MAN = 1;

        /**
         * 女
         */
        public static final int SEX_WOMAN = 2;

        /**
         * 年
         */
        public static final int DATE_Y = 1;

        /**
         * 年、月、日
         */
        public static final int DATE_YMD = 2;

        /**
         * 年、月、日、时、分
         */
        public static final int DATE_YMDHS = 3;

    }
}
