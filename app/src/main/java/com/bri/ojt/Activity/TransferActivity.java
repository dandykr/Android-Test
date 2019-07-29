package com.bri.ojt.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.bri.ojt.Dialog.TransferInfo;
import com.bri.ojt.Dialog.TransferInfoDialog;
import com.bri.ojt.Model.Request.ReqTransferInternal;
import com.bri.ojt.Model.Response.AccInfoInternalResponseCashcard;
import com.bri.ojt.Model.Response.TransferInternalResponse;
import com.bri.ojt.Network.API;
import com.bri.ojt.Network.RetrofitClient;
import com.bri.ojt.R;
import com.bri.ojt.Util.BRISignature;
import com.bri.ojt.Util.BaseActivity;
import com.bri.ojt.Util.Consts;
import com.bri.ojt.Util.DateTimeHelper;
import com.bri.ojt.Util.ThousandSeparatorTextWatcher;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferActivity extends BaseActivity implements TransferInfo {

    private API api;
    private Consts consts;
    private EditText etRekening, etNominal, etReferal, etKeterangan;
    private TextInputLayout llRekening, llNominal, llReferal, llKeterangan;
    private Button btnKonfirmasi;
    private ReqTransferInternal reqTransferInternal;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread thread = new Thread(this::setView);
        thread.start();

        api = RetrofitClient.getInstance(this).getAPI();
        consts = Consts.getInstance();
    }

    private void setView() {
        etRekening = findViewById(R.id.et_beneficiary);
        etNominal = findViewById(R.id.et_amount);
        etReferal = findViewById(R.id.et_referensi);
        etKeterangan = findViewById(R.id.et_keterangan);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi);
        etNominal.addTextChangedListener(new ThousandSeparatorTextWatcher(etNominal));
        btnKonfirmasi.setOnClickListener(onClickListener);
        progressBar = findViewById(R.id.progress_bar);
        llRekening = findViewById(R.id.ll_beneficiary);
        llNominal = findViewById(R.id.ll_amount);
        llReferal = findViewById(R.id.ll_referensi);
        llKeterangan = findViewById(R.id.ll_keterangan);

        reqTransferInternal = new ReqTransferInternal();
    }

    private View.OnClickListener onClickListener = v -> {
        hideKeyboard();
        if (verified()) {
            switchProgressVisibility();
            getAccInfo();
        } else if (etRekening.length() < 1) {
            final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
            llRekening.startAnimation(animShake);
        } else if (etNominal.length() < 1) {
            final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
            llNominal.startAnimation(animShake);
        } else if (etReferal.length() < 1) {
            final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
            llReferal.startAnimation(animShake);
        }
    };

    private void getAccInfo() {
        Map<String, String> header = BRISignature.getHeaderv2(Consts.PATH_ACC_INFO_INTERNAL, "GET", consts.getToken(), "");

        Call<AccInfoInternalResponseCashcard> call = api.getAccInfoInternal(header, consts.getVAEncrypt(), etRekening.getText().toString());

        call.enqueue(new Callback<AccInfoInternalResponseCashcard>() {
            @Override
            public void onResponse(Call<AccInfoInternalResponseCashcard> call, Response<AccInfoInternalResponseCashcard> response) {
                switchProgressVisibility();
                if (response.body() != null) {
                    if (response.code() == Consts.CODE_SUCCESS && response.body().getResponseCode().equalsIgnoreCase("0100")) {
                        TransferInfoDialog infoDialog = new TransferInfoDialog();

                        Thread thread = new Thread(() -> {
                            AccInfoInternalResponseCashcard info = response.body();
                            reqTransferInternal.setBeneficiaryAccount(info.getData().getBeneficiaryAccount());
                            reqTransferInternal.setBeneficiaryAccountName(info.getData().getBeneficiaryAccountName());
                            // TODO: ganti source account
                            reqTransferInternal.setSourceAccount("888801000157508");
                            reqTransferInternal.setFeeType(Consts.FEE_INTERNAL_TF);
                            reqTransferInternal.setAmount(etNominal.getText().toString());
                            reqTransferInternal.setNoReferral(etReferal.getText().toString());
                            reqTransferInternal.setRemark(etKeterangan.getText().toString().isEmpty() ? "-" : etKeterangan.getText().toString());
                            reqTransferInternal.setTransactionDateTime(DateTimeHelper.getTimestamp());

                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Consts.KEY_TRANSFER_INFO_DIALOG, reqTransferInternal);
                            bundle.putInt(Consts.KEY_TRANSFER_TYPE, Consts.TRANSFER_INFO_INTERNAL);

                            infoDialog.setArguments(bundle);

                            runOnUiThread(() -> {
                                infoDialog.show(getSupportFragmentManager(), "TAG");
                            });
                        });
                        thread.start();


                    } else {
                        showErrorMessage(response.body().getResponseException());
                    }
                }
            }

            @Override
            public void onFailure(Call<AccInfoInternalResponseCashcard> call, Throwable t) {
                showErrorMessage(t.getMessage());
                switchProgressVisibility();
            }
        });
    }

    private void doTransferInternal() {
        Map<String, String> map = BRISignature.getHeader(Consts.PATH_TRANSFER_INTERNAL, "POST", consts.getToken(),
                consts.getGson().toJson(reqTransferInternal));

        Call<TransferInternalResponse> call = api.reqTransferInternal(map, reqTransferInternal);

        call.enqueue(new Callback<TransferInternalResponse>() {
            @Override
            public void onResponse(Call<TransferInternalResponse> call, Response<TransferInternalResponse> response) {
                switchProgressVisibility();
                if (response.body() != null) {
                    if (response.code() == Consts.CODE_SUCCESS && response.body().getResponseCode().equalsIgnoreCase("0200")) {
                        runOnUiThread(() -> {
                            startActivity(new Intent(TransferActivity.this, TransferSuccessActivity.class)
                                    .putExtra(Consts.KEY_TRANSFER_INFO_DIALOG, reqTransferInternal));
                            TransferActivity.this.finish();
                        });

                    } else {
                        showErrorMessage(response.body().getResponseException());
                    }
                }
            }

            @Override
            public void onFailure(Call<TransferInternalResponse> call, Throwable t) {
                showErrorMessage(t.getMessage());
                switchProgressVisibility();
            }
        });
    }

    private boolean verified() {
        boolean completed = false;

        if (etRekening.length() > 1 && etNominal.length() > 1 && etReferal.length() > 1) {
            completed = true;
        }

        return completed;
    }

    private void switchProgressVisibility() {
        progressBar.setVisibility(progressBar.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
        btnKonfirmasi.setVisibility(btnKonfirmasi.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected String getActivityTitle() {
        return "Transfer";
    }


    @Override
    public void dialogInfoDone() {
        progressBar.setVisibility(progressBar.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
        btnKonfirmasi.setVisibility(btnKonfirmasi.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        doTransferInternal();
    }
}
