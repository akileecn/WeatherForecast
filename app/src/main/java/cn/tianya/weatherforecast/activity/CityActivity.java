package cn.tianya.weatherforecast.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.dao.CityDao;
import cn.tianya.weatherforecast.entity.City;
import cn.tianya.weatherforecast.utils.BaseListAdapter;

import static cn.tianya.weatherforecast.utils.Constants.SP.KEY_COMPANY_AREA_ID;
import static cn.tianya.weatherforecast.utils.Constants.SP.KEY_HOME_AREA_ID;

/**
 * 城市管理列表
 */
public class CityActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ADD_CITY = 1;
    private SharedPreferences sp;
    private List<City> mCityList;
    private CityDao mCityDao;
    private BaseAdapter mCityAdapter;
    private boolean isDataChanged = false; // 数据是否修改,删除或添加时置true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        initView();
        initData();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_city, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        City city = mCityList.get(info.position);
        switch (item.getItemId()) {
            case R.id.home_menu:
                sp.edit().putString(KEY_HOME_AREA_ID, city.getAreaId()).apply();
                mCityAdapter.notifyDataSetChanged();
                return true;
            case R.id.company_menu:
                sp.edit().putString(KEY_COMPANY_AREA_ID, city.getAreaId()).apply();
                mCityAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_CITY && resultCode == RESULT_OK) {
            isDataChanged = true;
            loadCity();
        }
    }

    @Override
    public void onBackPressed() {
        if (isDataChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    private void initView() {
        setContentView(R.layout.activity_city);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivityForResult(new Intent(CityActivity.this, AddCityActivity.class), REQUEST_CODE_ADD_CITY));

        ListView cityLv = findViewById(R.id.city_lv);
        // 注册菜单
        registerForContextMenu(cityLv);
        mCityList = new ArrayList<>();
        mCityAdapter = new BaseListAdapter<City>(mCityList) {
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                City city = getItem(i);
                Log.i("mCityAdapter", city.toString());
                ItemViewHolder holder;
                if (view == null) {
                    view = LayoutInflater.from(CityActivity.this).inflate(R.layout.item_selected_city, viewGroup, false);
                    holder = new ItemViewHolder(view);
                    view.setTag(holder);
                } else {
                    holder = (ItemViewHolder) view.getTag();
                }
                holder.setData(city);
                return view;
            }
        };
        cityLv.setAdapter(mCityAdapter);
    }

    private void initData() {
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
            case R.id.delete_iv:
                deleteCity(city);
                isDataChanged = true;
                break;
            default:
                break;
        }
    }

    /**
     * 列表条目view
     */
    private class ItemViewHolder {
        private TextView cityTv;
        private ImageView deleteIv;
        private ImageView homeIv;
        private ImageView companyIv;

        private ItemViewHolder(View view) {
            cityTv = view.findViewById(R.id.city_tv);
            deleteIv = view.findViewById(R.id.delete_iv);
            deleteIv.setOnClickListener(CityActivity.this);
            homeIv = view.findViewById(R.id.home_iv);
            companyIv = view.findViewById(R.id.company_iv);
        }

        private void setData(City city) {
            deleteIv.setTag(city);
            cityTv.setText(city.getArea());
            String homeAreaId = sp.getString(KEY_HOME_AREA_ID, "");
            homeIv.setVisibility(homeAreaId.equals(city.getAreaId()) ? View.VISIBLE : View.GONE);
            String companyAreaId = sp.getString(KEY_COMPANY_AREA_ID, "");
            companyIv.setVisibility(companyAreaId.equals(city.getAreaId()) ? View.VISIBLE : View.GONE);
        }
    }

    private void deleteCity(City city) {
        mCityList.remove(city);
        mCityDao.update(city.getAreaId(), false);
        mCityAdapter.notifyDataSetChanged();
    }
}
