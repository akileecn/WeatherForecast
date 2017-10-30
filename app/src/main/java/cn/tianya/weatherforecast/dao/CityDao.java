package cn.tianya.weatherforecast.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tianya.weatherforecast.entity.City;
import cn.tianya.weatherforecast.utils.Constants;

/**
 * 城市DAO
 * Created by Administrator on 2017/8/28.
 */
public class CityDao {
    private Context mContext;
    private SQLiteOpenHelper mHelper;
    private SharedPreferences mSp;
    private static final int DEFAULT_SIZE = 99; // 默认显示条数
    private static final String CITY_DATA_FILE = "cityData.js"; // 城市数据文件

    public CityDao(Context context) {
        mContext = context;
        mHelper = new MySQLiteOpenHelper(context);
        mSp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 初始化
     */
    public void init() {
        if (mSp.getBoolean(Constants.SP.KEY_CITY_INIT, false)) {
            return;
        }
        saveData(loadData());
        mSp.edit().putBoolean(Constants.SP.KEY_CITY_INIT, true).apply();
    }

    /**
     * 根据关键词查询
     */
    public List<City> list(String keywords) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String likeArg = "%" + keywords + "%";
        String sql = "SELECT province, city, area, area_id, selected " +
                "FROM city " +
                "WHERE province LIKE ? " +
                "OR city LIKE ? " +
                "OR area LIKE ? " +
                "LIMIT " + DEFAULT_SIZE;
        Cursor cursor = db.rawQuery(sql, new String[]{likeArg, likeArg, likeArg});
        return parseFromCursor(cursor);
    }

    /**
     * 查询选中的数据
     */
    public List<City> listSelected() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sql = "SELECT province, city, area, area_id, selected FROM city WHERE selected = 1";
        Cursor cursor = db.rawQuery(sql, null);
        return parseFromCursor(cursor);
    }

    /**
     * 更新为选中
     */
    public void update(String areaId, boolean isSelected) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "UPDATE city SET selected = " + (isSelected ? 1 : 0) + " WHERE area_id = ?";
        db.execSQL(sql, new String[]{areaId});
    }

    private List<City> parseFromCursor(Cursor cursor) {
        List<City> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            City city = new City();
            city.setProvince(cursor.getString(0));
            city.setCity(cursor.getString(1));
            city.setArea(cursor.getString(2));
            city.setAreaId(cursor.getString(3));
            city.setSelected(cursor.getInt(4) > 0);
            list.add(city);
        }
        cursor.close();
        return list;
    }

    private void saveData(List<City> list) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.beginTransaction();
        for (City city : list) {
            ContentValues values = new ContentValues();
            values.put("province", city.getProvince());
            values.put("city", city.getCity());
            values.put("area", city.getArea());
            values.put("area_id", city.getAreaId());
            values.put("selected", city.getSelected());
            db.insert("city", null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private List<City> loadData() {
        String jsonStr;
        try (InputStream is = mContext.getAssets().open(CITY_DATA_FILE)) {
            jsonStr = CharStreams.toString(new InputStreamReader(is, StandardCharsets.UTF_8));
        } catch (IOException ex) {
            return null;
        }
        JSONObject json = JSON.parseObject(jsonStr);
        List<City> list = new ArrayList<>();
        // 省
        for (Map.Entry<String, Object> province : json.entrySet()) {
            String provinceName = province.getKey();
            // 市
            for (Map.Entry<String, Object> city : ((JSONObject) province.getValue()).entrySet()) {
                String cityName = city.getKey();
                // 地区
                for (Map.Entry<String, Object> area : ((JSONObject) city.getValue()).entrySet()) {
                    String areaName = area.getKey();
                    String areaId = ((JSONObject) area.getValue()).getString("AREAID");
                    City item = new City();
                    item.setArea(areaName);
                    item.setAreaId(areaId);
                    item.setCity(cityName);
                    item.setProvince(provinceName);
                    item.setSelected(false); // 默认未选中
                    list.add(item);
                }
            }
        }
        return list;
    }

}
