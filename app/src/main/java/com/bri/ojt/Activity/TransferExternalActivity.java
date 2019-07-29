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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bri.ojt.Adapter.BankListAdapter;
import com.bri.ojt.Dialog.BankListDialog;
import com.bri.ojt.Dialog.BankListInterface;
import com.bri.ojt.Dialog.TransferInfo;
import com.bri.ojt.Dialog.TransferInfoDialog;
import com.bri.ojt.Model.Request.ReqTransferExternal;
import com.bri.ojt.Model.Response.AccInfoExternalResponse;
import com.bri.ojt.Model.Response.BankListResponse;
import com.bri.ojt.Model.Response.TransferExternalResponse;
import com.bri.ojt.Network.API;
import com.bri.ojt.Network.RetrofitClient;
import com.bri.ojt.R;
import com.bri.ojt.Util.BRISignature;
import com.bri.ojt.Util.BaseActivity;
import com.bri.ojt.Util.Consts;
import com.bri.ojt.Util.ThousandSeparatorTextWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferExternalActivity extends BaseActivity implements BankListInterface, TransferInfo {

    private API api;
    private Consts consts;
    private EditText etRekening, etNominal, etReferal;
    private TextView etNamaBank;
    private TextInputLayout llRekening, llNominal, llReferal;
    private Button btnKonfirmasi;
    private ProgressBar progressBar, progressBarBankList;
    private LinearLayout btnBankList;
    private BankListDialog bankListDialog;
    private BankListAdapter bankListAdapter;
    private List<BankListResponse.Data> bankListResponse = new ArrayList<>();
    private BankListResponse.Data selectedBank;

    private ReqTransferExternal reqTransExternal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread thread = new Thread(this::setView);
        thread.start();

        api = RetrofitClient.getInstance(this).getAPI();
        consts = Consts.getInstance();
    }

    private void setView() {
        btnBankList = findViewById(R.id.btn_bank_tujuan);
        btnBankList.setOnClickListener(onClickListener);
        etNamaBank = findViewById(R.id.bank_tujuan);
        etRekening = findViewById(R.id.et_beneficiary);
        etNominal = findViewById(R.id.et_amount);
        etReferal = findViewById(R.id.et_referensi);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi);
        etNominal.addTextChangedListener(new ThousandSeparatorTextWatcher(etNominal));
        btnKonfirmasi.setOnClickListener(onClickListener);
        progressBar = findViewById(R.id.progress_bar);
        progressBarBankList = findViewById(R.id.progress_bar_banklist);
        llRekening = findViewById(R.id.ll_beneficiary);
        llNominal = findViewById(R.id.ll_amount);
        llReferal = findViewById(R.id.ll_referensi);

        bankListDialog = new BankListDialog();

        bankListAdapter = new BankListAdapter(bankListResponse, this);

        reqTransExternal = new ReqTransferExternal();

        getBankList();

    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_bank_tujuan:
                bankListDialog.show(getSupportFragmentManager(), bankListDialog.getTag());
                break;
            case R.id.btn_konfirmasi:
                hideKeyboard();
                if (verified()) {
                    switchProgressVisibility();
                    getAccInfo();
                } else if (etNamaBank.length() < 1) {
                    final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
                    btnBankList.startAnimation(animShake);
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
                break;
        }
    };

    private boolean verified() {
        boolean completed = false;

        if (etNamaBank.length() > 1 && etRekening.length() > 1 && etNominal.length() > 1 && etReferal.length() > 1) {
            completed = true;
        }

        return completed;
    }

    private void getBankList() {
        Map<String, String> header = BRISignature.getHeaderv2(Consts.PATH_LIST_OTHER_BANK, "GET", consts.getToken(), "");

        Call<BankListResponse> call = api.getBankList(header);
        call.enqueue(new Callback<BankListResponse>() {
            @Override
            public void onResponse(Call<BankListResponse> call, Response<BankListResponse> response) {
                progressBarBankList.setVisibility(progressBarBankList.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                etNamaBank.setVisibility(etNamaBank.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                if (response.body() != null) {
                    if (response.code() == Consts.CODE_SUCCESS && response.body().getResponseCode().equalsIgnoreCase("00")) {
                        bankListResponse.addAll(response.body().getData());
                        bankListAdapter.notifyDataSetChanged();
                        bankListDialog.setAdapter(bankListAdapter);
                    } else {
                        showErrorMessage(response.body().getResponseException());
                    }
                } else {
                    showErrorMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<BankListResponse> call, Throwable t) {
                showErrorMessage(t.getMessage());
                progressBarBankList.setVisibility(progressBar.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                etNamaBank.setVisibility(btnKonfirmasi.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void getAccInfo() {
        Map<String, String> header = BRISignature.getHeaderv2(Consts.PATH_ACC_INFO_EXTERNAL, "GET", consts.getToken(), "");

        Call<AccInfoExternalResponse> call = api.getAccInfoExternal(header, selectedBank.getBankCode(), etRekening.getText().toString());

        call.enqueue(new Callback<AccInfoExternalResponse>() {
            @Override
            public void onResponse(Call<AccInfoExternalResponse> call, Response<AccInfoExternalResponse> response) {
                switchProgressVisibility();
                if (response.body() != null) {
                    if (response.code() == Consts.CODE_SUCCESS && response.body().getResponseCode().equalsIgnoreCase("0600")) {
                        TransferInfoDialog infoDialog = new TransferInfoDialog();

                        Thread thread = new Thread(() -> {
                            AccInfoExternalResponse info = response.body();
                            reqTransExternal.setAmount(etNominal.getText().toString());
                            reqTransExternal.setBeneficiaryAcc(etRekening.getText().toString());
                            reqTransExternal.setBankCode(selectedBank.getBankCode());
                            reqTransExternal.setBeneficiaryAccName(info.getName());
                            reqTransExternal.setSourceAcc(consts.getVAEncrypt());
                            reqTransExternal.setNoReferral(etReferal.getText().toString());

                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Consts.KEY_TRANSFER_INFO_DIALOG, reqTransExternal);
                            bundle.putInt(Consts.KEY_TRANSFER_TYPE, 200);

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
            public void onFailure(Call<AccInfoExternalResponse> call, Throwable t) {
                showErrorMessage(t.getMessage());
                switchProgressVisibility();
            }
        });
    }

    private void doTransferExternal() {
        Map<String, String> header = BRISignature.getHeader(Consts.PATH_TRANSFER_EXTERNAL, "POST", consts.getToken(), consts.getGson().toJson(reqTransExternal));

        Call<TransferExternalResponse> call = api.reqTransferExternal(header, reqTransExternal);

        call.enqueue(new Callback<TransferExternalResponse>() {
            @Override
            public void onResponse(Call<TransferExternalResponse> call, Response<TransferExternalResponse> response) {
                switchProgressVisibility();
                if (response.body() != null) {
                    if (response.code() == Consts.CODE_SUCCESS && response.body().getResponseCode().equalsIgnoreCase("0700")) {
                        runOnUiThread(() -> {
                            startActivity(new Intent(TransferExternalActivity.this, TransferSuccessActivity.class)
                                    .putExtra(Consts.KEY_TRANSFER_INFO_DIALOG, reqTransExternal));
                            TransferExternalActivity.this.finish();
                        });
                    } else {
                        showErrorMessage(response.body().getResponseException());
                    }
                }
            }

            @Override
            public void onFailure(Call<TransferExternalResponse> call, Throwable t) {
                showErrorMessage(t.getMessage());
                switchProgressVisibility();
            }
        });
    }

    private void switchProgressVisibility() {
        progressBar.setVisibility(progressBar.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
        btnKonfirmasi.setVisibility(btnKonfirmasi.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_transfer_external;
    }

    @Override
    protected String getActivityTitle() {
        return "Transfer";
    }

    @Override
    public void selectedBank(BankListResponse.Data item) {
        bankListDialog.dismiss();
        selectedBank = item;
        etNamaBank.setText(item.getBankName());
    }

    @Override
    public void dialogInfoDone() {
        switchProgressVisibility();
        doTransferExternal();
    }
}
