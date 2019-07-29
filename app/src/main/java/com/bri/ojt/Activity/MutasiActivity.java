package com.bri.ojt.Activity;

import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bri.ojt.Adapter.MutasiAdapter;
import com.bri.ojt.Model.Request.GetVAbyVA;
import com.bri.ojt.Model.Request.ReqMutasi;
import com.bri.ojt.Model.Response.MutasiVAResponse;
import com.bri.ojt.Model.Response.VAbyGroupResponseCashcard;
import com.bri.ojt.Network.API;
import com.bri.ojt.Network.RetrofitClient;
import com.bri.ojt.R;
import com.bri.ojt.Util.BRISignature;
import com.bri.ojt.Util.BaseActivity;
import com.bri.ojt.Util.Consts;
import com.bri.ojt.Util.FileHelper;
import com.bri.ojt.Util.PDFHelper;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MutasiActivity extends BaseActivity {

    private TextView noVA, saldo, dateStart, dateEnd, btnSave;
    private LinearLayout llDateStart, llDateEnd;
    private RecyclerView recyclerView;
    private ProgressBar progressBar, progressBarRV;
    private MutasiAdapter adapter;
    private MutasiVAResponse mutasiVAResponse = new MutasiVAResponse();
    private Consts consts = Consts.getInstance();
    private API api;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread thread = new Thread(this::setView);
        thread.start();

        api = RetrofitClient.getInstance(this).getAPI();
    }

    private void setView() {
        noVA = findViewById(R.id.txt_no_va);
        saldo = findViewById(R.id.txt_saldo);
        dateStart = findViewById(R.id.date_start);
        llDateStart = findViewById(R.id.ll_date_start);
        llDateStart.setOnClickListener(onClickListener);
        dateEnd = findViewById(R.id.date_end);
        llDateEnd = findViewById(R.id.ll_date_end);
        llDateEnd.setOnClickListener(onClickListener);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        progressBarRV = findViewById(R.id.progress_bar_recycler);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(onClickListener);
        adapter = new MutasiAdapter(mutasiVAResponse);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        runOnUiThread(() -> noVA.setText(consts.getVAEncrypt()));

        getDataVA();
    }


    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.ll_date_start:
                new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(year, month, dayOfMonth);
                    dateStart.setText(dateFormat.format(calendar1.getTime()));
                    verifyDate();
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.ll_date_end:
                new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(year, month, dayOfMonth);
                    dateEnd.setText(dateFormat.format(calendar1.getTime()));
                    verifyDate();
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btn_save:
                saveData();
                break;
        }
    };

    private void saveData() {
        if (checkAccessStorage(REQ_ACCESS_STORAGE_SAVE_FILE)) {
            new TaskHelper(this, mutasiVAResponse.getData()).execute();
        }
    }

    private void verifyDate() {
        if (dateStart.length() > 1 && dateEnd.length() > 1) {
            progressBarRV.setVisibility(View.VISIBLE);
            getDataMutasi(dateStart.getText().toString(), dateEnd.getText().toString());
        }
    }

    private void getDataVA() {
        final GetVAbyVA vAbyVA = new GetVAbyVA(consts.getVAEncrypt());

        Map<String, String> map = BRISignature.getHeader(Consts.PATH_VA_BY_VA, "POST", consts.getToken()
                , consts.getGson().toJson(vAbyVA));

        Call<VAbyGroupResponseCashcard> call = api.getVAbyVA(map, vAbyVA);

        call.enqueue(new Callback<VAbyGroupResponseCashcard>() {
            @Override
            public void onResponse(Call<VAbyGroupResponseCashcard> call, Response<VAbyGroupResponseCashcard> response) {
                VAbyGroupResponseCashcard vAbyGroupResponse = response.body();
                if (vAbyGroupResponse != null) {
                    if(response.code() == 200 && vAbyGroupResponse.getResponseCode().equalsIgnoreCase("00")) {
                        runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE);
                            String _saldo = String.format("Rp. %s", vAbyGroupResponse.getDT().get(0).getSisaPlafon());
                            saldo.setText(_saldo);
                        });
                    } else {
                        showErrorMessage(vAbyGroupResponse.getResponseException());
                    }
                }
            }

            @Override
            public void onFailure(Call<VAbyGroupResponseCashcard> call, Throwable t) {
                showErrorMessage(t.getMessage());
            }
        });

    }

    private void getDataMutasi(String dateStart, String dateEnd) {
        final ReqMutasi reqMutasi = new ReqMutasi(consts.getVAEncrypt(), dateStart, dateEnd);

        Map<String, String> header = BRISignature.getHeader(Consts.PATH_MUTASI, "POST", consts.getToken(), consts.getGson().toJson(reqMutasi));

        Call<MutasiVAResponse> call = api.reqMutasi(header, reqMutasi);

        call.enqueue(new Callback<MutasiVAResponse>() {
            @Override
            public void onResponse(Call<MutasiVAResponse> call, Response<MutasiVAResponse> response) {
                progressBarRV.setVisibility(View.GONE);

                MutasiVAResponse mVAresponse = response.body();
                if (mVAresponse != null) {
                    if (response.code() == Consts.CODE_SUCCESS && mVAresponse.getResponseCode().equalsIgnoreCase("00")) {
                        mutasiVAResponse.getData().clear();
                        mutasiVAResponse.getData().addAll(mVAresponse.getData());
                        adapter.notifyDataSetChanged();
                        btnSave.setVisibility(View.VISIBLE);
                    } else {
                        showErrorMessage(mVAresponse.getResponseException());
                    }
                }
            }

            @Override
            public void onFailure(Call<MutasiVAResponse> call, Throwable t) {
                progressBarRV.setVisibility(View.GONE);
                showErrorMessage(t.getMessage());
            }
        });
    }


    private static class TaskHelper extends AsyncTask<String, Void, Void> {

        private WeakReference<MutasiActivity> activityReference;
        private List<MutasiVAResponse.DetailMutasi> dataList;
        private String path;

        // only retain a weak reference to the activity
        TaskHelper(MutasiActivity context, List<MutasiVAResponse.DetailMutasi> dataList) {
            activityReference = new WeakReference<>(context);
            this.dataList = dataList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activityReference.get().progressBarRV.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            path = FileHelper.savePDFFile(PDFHelper.generateReport(activityReference.get(), dataList));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activityReference.get().progressBarRV.setVisibility(View.GONE);
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
        return R.layout.activity_mutasi;
    }

    @Override
    protected String getActivityTitle() {
        return "Mutasi";
    }
}
