package cn.tianya.weatherforecast.utils;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * 基础List适配器
 * Created by Administrator on 2017/8/25.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    private List<T> list;

    public BaseListAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
