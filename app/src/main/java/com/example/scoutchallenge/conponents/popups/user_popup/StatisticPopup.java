package com.example.scoutchallenge.conponents.popups.user_popup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.DateHelper;
import com.example.scoutchallenge.helpers.NumberHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.utils.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticPopup extends HeadComponents {
    AnyChartView anyChartView;
    JSONArray mActivitiesList;
    HashMap<String, String> mStatisticMap;

    public StatisticPopup(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatisticPopup(@NonNull Context context) {
        super(context);
    }


    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);

        anyChartView = new AnyChartView(getContext());
        addView(anyChartView);


        mStatisticMap = new HashMap<>();
        layoutViews();
    }

    private void layoutViews() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Tools.getScreenHeight(Tools.isPortrait()) - D.TAB_BAR_HEIGHT);
        setLayoutParams(params);
    }

    @Override
    public void setData(JSONObject data) {
        super.setData(data);
        if (data != null) {
            mActivitiesList = data.optJSONArray("data");
            runOnUiThread(() -> {
                drawGraphe();

            });
        }
    }


    private void drawGraphe() {
        List<DataEntry> data = new ArrayList<>();
        if (mActivitiesList != null) {
            for (int i = 0; i < mActivitiesList.length(); i++) {
                JSONObject currentObj = mActivitiesList.optJSONObject(i);
                if (currentObj != null) {
                    JSONArray users = currentObj.optJSONArray("users");
                    if (users != null) {
                        for (int j = 0; j < users.length(); j++) {
                            JSONObject currentUser = users.optJSONObject(j);
                            if (currentUser != null) {
                                if (currentUser.optString("userId").
                                        equalsIgnoreCase(LocalStorage.getString(LocalStorage.SELF_ID))) {
                                    mStatisticMap.put(currentObj.optString("date"), currentUser.optString("rate"));
                                    int note = NumberHelper.parseStringToNumber(currentUser.optString("rate"));
                                    String date = DateHelper.convertMongoDate(currentObj.optString("date"));
                                    data.add(new ValueDataEntry(date, note));

                                }
                            }
                        }
                    }
                }
            }
            data.add(new ValueDataEntry("2023/01/07", 13));
            data.add(new ValueDataEntry("2023/01/08", 15));
            data.add(new ValueDataEntry("2023/01/09", 18));
            data.add(new ValueDataEntry("2023/01/10", 4));
            data.add(new ValueDataEntry("2023/01/11", 20));
            data.add(new ValueDataEntry("2023/01/12", 12));


            Cartesian cartesian = AnyChart.column();


            Column column = cartesian.column(data);

            column.tooltip()
                    .titleFormat("{%X}")
                    .position(Position.CENTER_BOTTOM)
                    .anchor(Anchor.CENTER_BOTTOM)
                    .offsetX(0d)
                    .offsetY(5d)
                    .format("{%Value}{groupsSeparator: }");

            cartesian.animation(true);
            cartesian.title("توزع تقييمات تسميع القرآن");

            cartesian.yScale().minimum(0d);

            cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
            cartesian.interactivity().hoverMode(HoverMode.BY_X);

            cartesian.xAxis(0).title("التاريخ");
            cartesian.yAxis(0).title("العلامات");

            anyChartView.setChart(cartesian);
        }
    }
}
