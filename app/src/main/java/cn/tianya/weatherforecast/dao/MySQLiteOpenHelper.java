package cn.tianya.weatherforecast.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite帮助类
 * Created by Administrator on 2017/8/28.
 */
class MySQLiteOpenHelper extends SQLiteOpenHelper {

    MySQLiteOpenHelper(Context context) {
        super(context, "weatherforecast.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 地区
        sqLiteDatabase.execSQL("create table city (" +
                "id integer primary key autoincrement" +
                ", province varchar(10)" +
                ", city varchar(10)" +
                ", area varchar(10)" +
                ", area_id varchar(20)" +
                ", selected boolean)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
