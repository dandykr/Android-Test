package com.bri.ojt.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bri.ojt.Model.Response.VAbyGroupResponseCashcard;
import com.bri.ojt.Network.APICall;
import com.bri.ojt.R;
import com.bri.ojt.Util.BaseActivity;
import com.bri.ojt.Util.Consts;
import com.bri.otplayout.OTPView;
import com.bri.otplayout.OnOTPCompletionListener;

import java.util.Locale;
import java.util.Objects;

import androidx.annotation.Nullable;

public class VerifyOTPActivity extends BaseActivity {

    private OTPView otpView;
    private ProgressBar progressBar;
    private Button btnVerify;
    private TextView btnResend;
    private VAbyGroupResponseCashcard.DetailVA detailVA;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailVA = (VAbyGroupResponseCashcard.DetailVA) getIntent().getSerializableExtra("detail-va");

        Thread thread = new Thread(this::setView);
        thread.start();
    }

    private void setView() {
        btnVerify = findViewById(R.id.btn_verifikasi);
        btnVerify.setOnClickListener(onClickListener);
        btnResend = findViewById(R.id.btn_resend);
        btnResend.setOnClickListener(onClickListener);
        otpView = findViewById(R.id.otp_view);
        otpView.setOtpCompletionListener(onOTPCompletionListener);
        progressBar = findViewById(R.id.progress_bar);

        runOnUiThread(this::runTimer);
    }

    private void runTimer() {
        btnResend.setEnabled(false);
        btnResend.setTextColor(getResources().getColor(R.color.grey400, getTheme()));
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                btnResend.setTextColor(getResources().getColor(R.color.grey700, getTheme()));
                btnResend.setText("Kirim ulang OTP");
                btnResend.setEnabled(true);
            }
        };
        countDownTimer.start();
    }

    public  void updateTimer(long timer){
        int seconds = (int) timer / 1000 % 60;

        String timeLifeText = "";

        if (seconds < 10) timeLifeText += "0";
        timeLifeText += seconds;

        String expired = String.format(Locale.US,"Kirim ulang OTP (%s detik)", timeLifeText);

        btnResend.setText(expired);
    }

    private OnOTPCompletionListener onOTPCompletionListener = this::verifyOTP;

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_verifikasi:
                if (Objects.requireNonNull(otpView.getText()).toString().length() == 6) {
                    verifyOTP(otpView.getText().toString());
                } else {
                    final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
                    otpView.startAnimation(animShake);
                }
                break;
            case R.id.btn_resend:
                runTimer();
                requestOTP(detailVA);
                break;
        }
    };

    private void verifyOTP(String otp) {
        hideKeyboard();

        switchProgress();

        APICall call = new APICall(new APICall.APICallback() {
            @Override
            public void onSuccess(Object data) {
                switchProgress();
                Consts.getInstance().setIsLogin(true);
                Intent intent = new Intent(VerifyOTPActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailed(String message) {
                switchProgress();
                otpView.setText("");
                showErrorMessage(message);
            }
        });

        call.verifyOTP(otp);
    }

    private void requestOTP(VAbyGroupResponseCashcard.DetailVA detailVA) {
        switchProgress();
        APICall call = new APICall(new APICall.APICallback() {
            @Override
            public void onSuccess(Object data) {
                switchProgress();
                runTimer();
            }

            @Override
            public void onFailed(String message) {
                switchProgress();
                Log.e("Verify Activity", message);
                showErrorMessage(message);
            }
        });

        call.requestOTP(detailVA, APICall.OTPVIA.EMAIL);
    }

    private void switchProgress() {
        progressBar.setVisibility(progressBar.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        btnVerify.setVisibility(btnVerify.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_verify_otp;
    }

    @Override
    protected String getActivityTitle() {
        return "Verifikasi";
    }

}
