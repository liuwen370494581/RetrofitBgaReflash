package star.liuwen.com.endgreendao3.Base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import star.liuwen.com.endgreendao3.Bean.DaoMaster;
import star.liuwen.com.endgreendao3.Bean.DaoSession;

/**
 * Created by liuwen on 2017/2/23.
 */
public class BaseApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //配置数据库
        setupDatabase();

    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
