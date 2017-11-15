package cn.tianya.weatherforecast.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import cn.tianya.weatherforecast.utils.DateUtils;

import static cn.tianya.weatherforecast.utils.Constants.SP.DEFAULT_NOTIFY_TIME;

/**
 * 时间属性
 * Created by Administrator on 2017/11/15.
 */
public class TimePreference extends DialogPreference {
    private int lastHour = 0;
    private int lastMinute = 0;
    private TimePicker picker = null;

    public TimePreference(Context ctxt, AttributeSet attrs) {
        super(ctxt, attrs);
        setPositiveButtonText("确认");
        setNegativeButtonText("取消");
    }

    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        return picker;
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        picker.setHour(lastHour);
        picker.setMinute(lastMinute);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            lastHour = picker.getHour();
            lastMinute = picker.getMinute();
            String time = String.valueOf(lastHour) + ":" + String.valueOf(lastMinute);
            if (callChangeListener(time)) {
                persistString(time);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        String time;
        if (restoreValue) {
            if (defaultValue == null) {
                time = getPersistedString(DEFAULT_NOTIFY_TIME);
            } else {
                time = getPersistedString(defaultValue.toString());
            }
        } else {
            time = defaultValue.toString();
        }
        lastHour = DateUtils.getHour(time);
        lastMinute = DateUtils.getMinute(time);
    }

    @Override
    public CharSequence getSummary() {
        if (super.getSummary() == null) {
            return String.valueOf(lastHour) + ":" + String.valueOf(lastMinute);
        }
        return super.getSummary();
    }
}
