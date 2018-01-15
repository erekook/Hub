package com.example.erek.hub;

import android.app.Application;

import com.example.erek.hub.entity.DaoMaster;
import com.example.erek.hub.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by erek on 2018/1/12
 */

public class App extends Application {
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"users-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
