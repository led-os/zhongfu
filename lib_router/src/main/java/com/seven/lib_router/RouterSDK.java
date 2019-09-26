package com.seven.lib_router;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.seven.greendao.gen.DaoMaster;
import com.seven.greendao.gen.DaoSession;
import com.seven.lib_router.db.shard.SharedData;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/8
 */

public class RouterSDK {

    private Context context;

    private static RouterSDK instance = null;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private RouterSDK() {
    }

    public static RouterSDK getInstance() {
        if (instance == null) {
            synchronized (RouterSDK.class) {
                if (instance == null) {
                    instance = new RouterSDK();
                }
            }
        }

        return instance;
    }

    public void initSDK(Context context) {
        this.context = context;
//        setDatabase();
    }

    public void SQLite(Context context){
        setDatabase();
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this.context, "seven-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public void getShareData(){
        if (TextUtils.isEmpty(SharedData.getInstance().getUserInfo())){
            return;
        }
        //Variable.getInstance().setTokenCount();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
