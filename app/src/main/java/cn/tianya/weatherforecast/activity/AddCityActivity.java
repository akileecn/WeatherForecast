package cn.tianya.weatherforecast.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.dao.CityDao;
import cn.tianya.weatherforecast.entity.City;
import cn.tianya.weatherforecast.utils.BaseListAdapter;

public class AddCityActivity extends AppCompatActivity {
    private List<City> mCityList;
    private BaseListAdapter<City> mCityAdapter;
    private CityDao mCityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        initView();
        initData();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    private void initView() {
        SearchView citySv = (SearchView) findViewById(R.id.search_city);
        citySv.setIconified(false); // 是否图标化
        citySv.setFocusable(true);
        citySv.requestFocusFromTouch(); // 获取焦点
        citySv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadCity(newText);
                return true;
            }
        });

        ListView cityLv = (ListView) findViewById(R.id.list_city);
        mCityList = new ArrayList<>();
        mCityAdapter = new BaseListAdapter<City>(mCityList) {
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                City city = getItem(i);
                if (view == null) {
                    view = LayoutInflater.from(AddCityActivity.this).inflate(R.layout.item_search_city, viewGroup, false);
                }
                ((TextView) view).setText(city.getFullArea());
                view.setTag(city);
                return view;
            }
        };
        cityLv.setAdapter(mCityAdapter);
        cityLv.setOnItemClickListener((parent, view, position, id) -> {
            City city = (City) view.getTag();
            mCityDao.updateAsSelected(city.getAreaId());
            setResult(RESULT_OK);
            finish();
        });
    }

    private void initData() {
        mCityDao = new CityDao(this);
        mCityDao.init();
    }

    private void loadCity(String keywords) {
        mCityList.clear();
        if (Strings.isNullOrEmpty(keywords)) {
            return;
        }
        List<City> list = mCityDao.list(keywords.trim());
        mCityList.addAll(list);
        mCityAdapter.notifyDataSetChanged();
    }
}
