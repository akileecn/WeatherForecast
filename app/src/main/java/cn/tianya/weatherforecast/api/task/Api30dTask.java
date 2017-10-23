package cn.tianya.weatherforecast.api.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.tianya.weatherforecast.api.ApiCallBack;
import cn.tianya.weatherforecast.api.Result;
import cn.tianya.weatherforecast.api.entity.BaseForecast;
import cn.tianya.weatherforecast.api.entity.Forecast40d;

/**
 * 请求30天气象数据, 通过多次调用日历接口实现
 * Created by Administrator on 2017/10/23.
 */
public class Api30dTask extends ApiCalendarTask {
    private static final int NEED_SIZE = 30; // 需要的数量

    public Api30dTask(String areaId, ApiCallBack<List<BaseForecast>> callBack) {
        super(areaId, new ApiCallBackWrapper(areaId, callBack), 0);
    }

    private static class ApiCallBackWrapper implements ApiCallBack<List<BaseForecast>> {
        private ApiCallBack<List<BaseForecast>> callBack;
        private String areaId;

        private ApiCallBackWrapper(String areaId, ApiCallBack<List<BaseForecast>> callBack) {
            this.callBack = callBack;
            this.areaId = areaId;
        }

        @Override
        public void execute(Result<List<BaseForecast>> result) {
            if (callBack == null) {
                return;
            }
            if (!result.getSuccess()) {
                callBack.execute(result);
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            String now = sdf.format(new Date());
            final List<BaseForecast> list = new ArrayList<>();
            String itemDate = now; // 记录遍历元素最大时间
            for (BaseForecast item : result.getData()) {
                itemDate = ((Forecast40d) item).getRawDate();
                if (itemDate.compareTo(now) >= 0) {
                    list.add(item);
                }
            }

            final int finalMore = NEED_SIZE - list.size();
            final String finalItemDate = itemDate;
            if (finalMore > 0) {
                // 再次请求
                new ApiCalendarTask(areaId, moreResult -> {
                    int more = finalMore;
                    if (result.getSuccess()) {
                        for (BaseForecast item : moreResult.getData()) {
                            if (more == 0) {
                                break;
                            }
                            String rawDate = ((Forecast40d) item).getRawDate();
                            if (rawDate.compareTo(finalItemDate) > 0) {
                                list.add(item);
                                more--;
                            }
                        }
                    }
                    callBack.execute(Result.success(list));
                }, 1).execute();
            } else {
                callBack.execute(Result.success(list));
            }
        }

    }
}
