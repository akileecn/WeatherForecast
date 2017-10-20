package cn.tianya.weatherforecast.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.dao.CityDao;
import cn.tianya.weatherforecast.entity.City;
import cn.tianya.weatherforecast.utils.BaseListAdapter;
import cn.tianya.weatherforecast.Constants;
import cn.tianya.weatherforecast.utils.Helper;

public class CityActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ADD_CITY = 1;
    private List<City> mCityList;
    private CityDao mCityDao;
    private BaseAdapter mCityAdapter;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_CITY && resultCode == RESULT_OK) {
            loadCity();
        }
    }

    private void initView() {
        setContentView(R.layout.activity_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivityForResult(new Intent(CityActivity.this, AddCityActivity.class), REQUEST_CODE_ADD_CITY));

        ListView cityLv = (ListView) findViewById(R.id.list_city);
        mCityList = new ArrayList<>();
        mCityAdapter = new BaseListAdapter<City>(mCityList) {
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                City city = getItem(i);
                ViewHolder holder;
                if (view == null) {
                    view = LayoutInflater.from(CityActivity.this).inflate(R.layout.item_selected_city, viewGroup, false);
                    holder = new ViewHolder(view);
                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }
                holder.setData(city);
                return view;
            }
        };
        cityLv.setAdapter(mCityAdapter);
    }

    private void initData() {
        mSp = getSharedPreferences(Constants.SP.NAME, MODE_PRIVATE);
        mCityDao = new CityDao(this);
        loadCity();
    }

    private void loadCity() {
        mCityList.clear();
        mCityList.addAll(mCityDao.listSelected());
        mCityAdapter.notifyDataSetChanged();
    }

    /**
     * list_city item点击事件处理
     */
    @Override
    public void onClick(View v) {
        City city = (City) v.getTag();
        switch (v.getId()) {
            case R.id.text_city:
                Helper.setDefaultCity(mSp, city);
                Intent intent = new Intent();
                intent.putExtra(Constants.INTENT_EXTRA_SELECTED_CITY, city);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_delete:
                deleteCity(city);
                break;
        }
    }

    private class ViewHolder {
        private TextView cityTv;
        private ImageButton deleteBtn;

        private ViewHolder(View view) {
            cityTv = (TextView) view.findViewById(R.id.text_city);
            cityTv.setOnClickListener(CityActivity.this);
            deleteBtn = (ImageButton) view.findViewById(R.id.btn_delete);
            deleteBtn.setOnClickListener(CityActivity.this);
        }

        private void setData(City city) {
            deleteBtn.setTag(city);
            cityTv.setTag(city);
            cityTv.setText(city.getArea());
        }
    }

    private void deleteCity(City city) {
        mCityList.remove(city);
        mCityDao.updateAsSelected(city.getAreaId());
        mCityAdapter.notifyDataSetChanged();
    }
}
