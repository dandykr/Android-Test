package com.bri.ojt.Widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.bri.ojt.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DashboardLineView extends LinearLayout {

    private LineChart chartDebet, chartKredit, chartSaldo;

    public DashboardLineView(Context context) {
        super(context);
        init(context);
    }

    public DashboardLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DashboardLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DashboardLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.content_dashboard_line, this);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        chartDebet = findViewById(R.id.chart_debet);
        chartKredit = findViewById(R.id.chart_kredit);
        chartSaldo = findViewById(R.id.chart_saldo);

        Thread thread = new Thread(this::setLineChart);
        thread.start();
    }

    private void setLineChart() {
        chartDebet.setBackgroundColor(Color.WHITE);
        chartKredit.setBackgroundColor(Color.WHITE);
        chartSaldo.setBackgroundColor(Color.WHITE);
        chartDebet.getDescription().setEnabled(false);
        chartKredit.getDescription().setEnabled(false);
        chartSaldo.getDescription().setEnabled(false);
        chartDebet.setTouchEnabled(true);
        chartKredit.setTouchEnabled(true);
        chartSaldo.setTouchEnabled(true);
        chartDebet.setScaleEnabled(true);
        chartKredit.setScaleEnabled(true);
        chartSaldo.setScaleEnabled(true);
        chartDebet.setPinchZoom(true);
        chartKredit.setPinchZoom(true);
        chartSaldo.setPinchZoom(true);
        chartDebet.getLegend().setForm(Legend.LegendForm.LINE);
        chartKredit.getLegend().setForm(Legend.LegendForm.LINE);
        chartSaldo.getLegend().setForm(Legend.LegendForm.LINE);
    }

    public void setLineChartData(List<Entry> dataDebet, List<Entry> dataKredit, List<Entry> dataSaldo) {
        LineDataSet debetDataSet = new LineDataSet(dataDebet, "Summary Debet");
        LineDataSet kreditDataSet = new LineDataSet(dataKredit, "Summary Kredit");
        LineDataSet saldoDataSet = new LineDataSet(dataSaldo, "Summary Saldo");


        //set debet
        {
//            debetDataSet.enableDashedLine(10f,5f,0f);
            debetDataSet.setColor(0xFF4FC3F7);
            debetDataSet.setCircleColor(0xFF4FC3F7);
            debetDataSet.setLineWidth(1f);
            debetDataSet.setCircleRadius(2f);
            debetDataSet.setDrawCircleHole(false);
            debetDataSet.setValueTextSize(9f);
            debetDataSet.setDrawFilled(true);
            debetDataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fade_blue));

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(debetDataSet);

            LineData data = new LineData(dataSets);
            data.setDrawValues(false);

            chartDebet.setData(data);
            chartDebet.getAxisRight().setEnabled(false);
            chartDebet.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chartDebet.getXAxis().setTextSize(9f);
        }

        //set kredit
        {
//            kreditDataSet.enableDashedLine(10f,5f,0f);
            kreditDataSet.setColor(0xffFFB74D);
            kreditDataSet.setCircleColor(0xffFFB74D);
            kreditDataSet.setLineWidth(1f);
            kreditDataSet.setCircleRadius(2f);
            kreditDataSet.setDrawCircleHole(false);
            kreditDataSet.setValueTextSize(9f);
            kreditDataSet.setDrawFilled(true);
            kreditDataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fade_orange));

            ArrayList<ILineDataSet> dataSets1 = new ArrayList<>();
            dataSets1.add(kreditDataSet);

            LineData data1 = new LineData(dataSets1);

            chartKredit.setData(data1);
            data1.setDrawValues(false);

            chartKredit.getAxisRight().setEnabled(false);
            chartKredit.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chartKredit.getXAxis().setTextSize(9f);
        }

        //set saldo
        {
//            saldoDataSet.enableDashedLine(10f,5f,0f);
            saldoDataSet.setColor(0xffE57373);
            saldoDataSet.setCircleColor(0xffE57373);
            saldoDataSet.setLineWidth(1f);
            saldoDataSet.setCircleRadius(2f);
            saldoDataSet.setDrawCircleHole(false);
            saldoDataSet.setValueTextSize(9f);
            saldoDataSet.setDrawFilled(true);
            saldoDataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fade_red));

            ArrayList<ILineDataSet> dataSets2 = new ArrayList<>();
            dataSets2.add(saldoDataSet);

            LineData data2 = new LineData(dataSets2);

            chartSaldo.setData(data2);
            data2.setDrawValues(false);

            chartSaldo.getAxisRight().setEnabled(false);
            chartSaldo.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chartSaldo.getXAxis().setTextSize(9f);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            chartDebet.animateXY(1000, 1000, Easing.EaseInOutCubic);
            chartKredit.animateXY(1000,1000, Easing.EaseInOutCubic);
            chartSaldo.animateXY(1000,1000,Easing.EaseInOutCubic);
        });
    }
}
