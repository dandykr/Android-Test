package com.bri.ojt.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bri.ojt.Model.Request.ReqTarikTunai;
import com.bri.ojt.Model.Response.TarikTunaiResponse;
import com.bri.ojt.Network.API;
import com.bri.ojt.Network.RetrofitClient;
import com.bri.ojt.R;
import com.bri.ojt.Util.BRISignature;
import com.bri.ojt.Util.BaseActivity;
import com.bri.ojt.Util.Consts;
import com.bri.ojt.Util.FormHelper;
import com.bri.ojt.Util.ThousandSeparatorTextWatcher;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarikTunaiActivity extends BaseActivity {

    private TextView noVA, errorMsg;
    private EditText etNominal;
    private Spinner spNominal;
    private ProgressBar progressBar;
    private Button btnLanjut;
    private Consts consts = Consts.getInstance();
    private API api;
    private int from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread thread = new Thread(this::setView);
        thread.start();

        api = RetrofitClient.getInstance(this).getAPI();

    }

    private void setView() {
        noVA = findViewById(R.id.txt_no_va);
        errorMsg = findViewById(R.id.txt_error_msg);
        etNominal = findViewById(R.id.et_nominal);
        etNominal.addTextChangedListener(new ThousandSeparatorTextWatcher(etNominal));
        spNominal = findViewById(R.id.sp_nominal);
        btnLanjut = findViewById(R.id.btn_lanjut);
        btnLanjut.setOnClickListener(onClickListener);
        progressBar = findViewById(R.id.progress_bar);

        from = getIntent().getIntExtra(Consts.KEY_TARTUN_FROM, 0);

        noVA.setText(consts.getVAEncrypt());

        runOnUiThread(() -> {
            if (from == Consts.TARTUN_ECHANNEL) {
                etNominal.setVisibility(View.GONE);
                spNominal.setVisibility(View.VISIBLE);
            } else {
                etNominal.setVisibility(View.VISIBLE);
                spNominal.setVisibility(View.GONE);
            }
        });

    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_lanjut:
                hideKeyboard();
                String amount = from == Consts.TARTUN_ECHANNEL ? FormHelper.trimPeriod(spNominal.getSelectedItem().toString())
                        : FormHelper.trimPeriod(etNominal.getText().toString());

                if (from == Consts.TARTUN_TELLER && etNominal.length() < 10 ) {
                    final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
                    etNominal.startAnimation(animShake);

                    errorMsg.setVisibility(View.VISIBLE);
                    final Animation animMove = AnimationUtils.loadAnimation(this, R.anim.anim_move);
                    errorMsg.startAnimation(animMove);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    reqTarikTunai(consts.getVAEncrypt(), amount);
                }

                break;
        }
    };


    private void reqTarikTunai(String va, String nominal) {
        ReqTarikTunai reqTarikTunai = new ReqTarikTunai(new ReqTarikTunai.DataTarikTunai(va, nominal));

        Map<String, String> headers = BRISignature.getHeader(from == 0 ? Consts.PATH_OTP_ECHANNEL : Consts.PATH_OTP_TELLER,
                "POST", consts.getToken(), consts.getGson().toJson(reqTarikTunai));

        Call<TarikTunaiResponse> call = from == 0 ? api.reqOTPEchannel(headers, reqTarikTunai) : api.reqOTPTeller(headers, reqTarikTunai);

        call.enqueue(new Callback<TarikTunaiResponse>() {
            @Override
            public void onResponse(Call<TarikTunaiResponse> call, Response<TarikTunaiResponse> response) {
                progressBar.setVisibility(View.GONE);
                TarikTunaiResponse tarikTunaiResponse = response.body();
                if (tarikTunaiResponse != null) {
                    if (response.code() == Consts.CODE_SUCCESS && tarikTunaiResponse.reqValid()) {
                        startActivity(new Intent(TarikTunaiActivity.this, TarikTunaiSuccessActivity.class)
                                .putExtra(Consts.KEY_TARTUN_FROM, from)
                                .putExtra(Consts.KEY_TARTUN_OTP, tarikTunaiResponse));
                    } else {
                        showErrorMessage(tarikTunaiResponse.getErrorMessage());
                    }
                } else {
                    showErrorMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<TarikTunaiResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showErrorMessage(t.getMessage());
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_tarik_tunai;
    }

    @Override
    protected String getActivityTitle() {
        return "Tarik Tunai";
    }
}
