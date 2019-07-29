package com.bri.ojt.Widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bri.ojt.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;

public class DashboardView extends LinearLayout {

    private TextView totalDebit, totalKredit;
    private PieChart pieChart;

    public DashboardView(Context context) {
        super(context);
        init(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.content_dashboard, this);

    }

    public void setPieChartData(List<PieEntry> chartData) {
        PieDataSet dataSet = new PieDataSet(chartData, "Summary");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //add color
        final int[] chartColor = {ColorTemplate.rgb("#4FC3F7"), ColorTemplate.rgb("#FFB74D")};
        dataSet.setColors(chartColor);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);

        pieChart.highlightValues(null);
        pieChart.postInvalidate();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> pieChart.animateY(1000, Easing.EaseInOutQuad));

    }

    public void setTotalDebit(int debit) {
        totalDebit.setText(String.format(Locale.getDefault(),"IDR %d", debit));
    }

    public void setTotalKredit(int kredit) {
        totalKredit.setText(String.format(Locale.getDefault(),"IDR %d", kredit));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        totalDebit = findViewById(R.id.total_debit);
        totalKredit = findViewById(R.id.total_kredit);
        pieChart = findViewById(R.id.pie_chart);

        Thread thread = new Thread(this::setPieChart);
        thread.start();
    }

    private void setPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(48f);
        pieChart.setRotationAngle(90);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(11f);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
    }
}
