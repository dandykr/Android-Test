package com.bri.ojt.Activity;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bri.ojt.Adapter.SpinnerAdapter;
import com.bri.ojt.Model.Request.ReqMutasi;
import com.bri.ojt.Model.Response.MutasiVAResponse;
import com.bri.ojt.Network.API;
import com.bri.ojt.Network.RetrofitClient;
import com.bri.ojt.R;
import com.bri.ojt.Util.BRISignature;
import com.bri.ojt.Util.BaseActivity;
import com.bri.ojt.Util.Consts;
import com.bri.ojt.Util.DateTimeHelper;
import com.bri.ojt.Util.FileHelper;
import com.bri.ojt.Util.PDFHelper;
import com.bri.ojt.Widget.DashboardLineView;
import com.github.mikephil.charting.data.Entry;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatementActivity extends BaseActivity {

    private API api;
    private Consts consts;
    private ProgressBar progressBar;
    private DashboardLineView dashboard;
    private List<Entry> dataDebet, dataKredit, dataSaldo;
    private ConstraintLayout constraintLayout;
    private AppCompatSpinner spMonth, spYear;
    private MutasiVAResponse mutasiVA;
    private Button btnProcess;
    private TextView btnSave;
    private List<String> listMonth = new ArrayList<>(), listYear = new ArrayList<>();
    private int lastDayofMonth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread thread = new Thread(this::setView);
        thread.start();

        api = RetrofitClient.getInstance(this).getAPI();
        consts = Consts.getInstance();
    }

    private void setView() {
        dashboard = findViewById(R.id.dashboard_line);
        progressBar = findViewById(R.id.progress_bar);
        constraintLayout = findViewById(R.id.constraint_layout);
        spMonth = findViewById(R.id.sp_month);
        spYear = findViewById(R.id.sp_year);
        btnProcess = findViewById(R.id.btn_process);
        btnSave = findViewById(R.id.btn_save);
        btnProcess.setOnClickListener(onClickListener);
        btnSave.setOnClickListener(onClickListener);

        listMonth.addAll(Arrays.asList(getResources().getStringArray(R.array.month)));
        listYear.addAll(DateTimeHelper.getLastThreeYear());

        SpinnerAdapter adapterSpMonth = new SpinnerAdapter(this, R.layout.item_spinner, listMonth);
        SpinnerAdapter adapterSpYear = new SpinnerAdapter(this, R.layout.item_spinner, listYear);

        spMonth.setAdapter(adapterSpMonth);
        spYear.setAdapter(adapterSpYear);

        spMonth.setSelection(listMonth.indexOf(DateTimeHelper.getCurrentMonth()));
        spYear.setSelection(listYear.indexOf(String.valueOf(DateTimeHelper.getCurrentYear())));
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_process:
                progressBar.setVisibility(View.VISIBLE);
                String monthSelected = spMonth.getSelectedItem().toString();
                String yearSelected = spYear.getSelectedItem().toString();
                lastDayofMonth = DateTimeHelper.getLastDay(monthSelected + "-" + yearSelected);
                String dateStart = String.format(Locale.US, "%s-%s-01", yearSelected, DateTimeHelper.getMonth(monthSelected));
                String dateEnd = String.format(Locale.US, "%s-%s-%d", yearSelected, DateTimeHelper.getMonth(monthSelected), lastDayofMonth);
                getDataMutasi(dateStart,dateEnd);
                break;
            case R.id.btn_save:
                saveData();
                break;
        }
    };

    private void saveData() {
        if (checkAccessStorage(REQ_ACCESS_STORAGE_SAVE_FILE)) {
            new TaskHelper(this, mutasiVA.getData()).execute();
        }
    }

    private void getDataMutasi(String dateStart, String dateEnd) {
        ReqMutasi reqMutasi = new ReqMutasi(consts.getVAEncrypt(), dateStart, dateEnd);

        Map<String, String> header = BRISignature.getHeader(Consts.PATH_MUTASI, "POST", consts.getToken(), consts.getGson().toJson(reqMutasi));

        Call<MutasiVAResponse> call = api.reqMutasi(header, reqMutasi);

        call.enqueue(new Callback<MutasiVAResponse>() {
            @Override
            public void onResponse(Call<MutasiVAResponse> call, Response<MutasiVAResponse> response) {
                runProgressBar();

//                mutasiVA = response.body();
                mutasiVA = MutasiVAResponse.getExample(StatementActivity.this);
                if (mutasiVA != null) {
                    if (response.code() == Consts.CODE_SUCCESS && mutasiVA.getResponseCode().equalsIgnoreCase("00")) {
                        Handler handler = new Handler();
//                        handler.post(() -> processData(mVAresponse));
                        btnSave.setVisibility(View.VISIBLE);
                        handler.post(() -> processData(mutasiVA));
                    } else {
                        showErrorMessage(mutasiVA.getResponseException());
                    }
                }
            }

            @Override
            public void onFailure(Call<MutasiVAResponse> call, Throwable t) {
                runProgressBar();
                showErrorMessage(t.getMessage());
            }
        });
    }

    private void processData(MutasiVAResponse dataList) {
        runProgressBar();
        List<Entry> entryListDebet = new ArrayList<>(), entryListKredit = new ArrayList<>(), entryListSaldo= new ArrayList<>();

        for (int i = 1; i <= lastDayofMonth ; i++) {
            double totalAmountDebet = 0.0;
            double totalAmountKredit = 0.0;
            double totalAmountSaldo = 0.0;

            for (MutasiVAResponse.DetailMutasi item : dataList.getData()) {
                if (Integer.parseInt(item.getTrxDay()) == i) {
                    totalAmountDebet += item.getAmountDebet();
                    totalAmountKredit += item.getAmountKredit();
                    totalAmountSaldo += item.getSaldoAkhir();
                }
            }

            Entry entry = new Entry((float) i, (float) totalAmountDebet);
            entryListDebet.add(entry);

            Entry entry2 = new Entry((float) i, (float) totalAmountKredit);
            entryListKredit.add(entry2);

            Entry entry3 = new Entry((float) i, (float) totalAmountSaldo);
            entryListSaldo.add(entry3);
        }
        runProgressBar();

        dashboard.setVisibility(View.VISIBLE);
        dashboard.animate().setDuration(250).alpha(1);
        dashboard.setLineChartData(entryListDebet, entryListKredit, entryListSaldo);
    }

    private void runProgressBar() {
        runOnUiThread(() -> progressBar.setVisibility(progressBar.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));
    }

    private static class TaskHelper extends AsyncTask<String, Void, Void> {

        private WeakReference<StatementActivity> activityReference;
        private List<MutasiVAResponse.DetailMutasi> dataList;
        private String path;

        // only retain a weak reference to the activity
        TaskHelper(StatementActivity context, List<MutasiVAResponse.DetailMutasi> dataList) {
            activityReference = new WeakReference<>(context);
            this.dataList = dataList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activityReference.get().runProgressBar();
        }

        @Override
        protected Void doInBackground(String... strings) {
            path = FileHelper.savePDFFile(PDFHelper.generateReport(activityReference.get(), dataList));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activityReference.get().runProgressBar();
            activityReference.get().showMessage(path);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQ_ACCESS_STORAGE_SAVE_FILE:
                    saveData();
                    break;
            }
        } else {
            showMessage("Akses Storage dibutuhkan untuk menyimpan file.");
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_statement;
    }

    @Override
    protected String getActivityTitle() {
        return "";
    }
}
