package com.bri.ojt.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bri.ojt.Model.Response.TarikTunaiResponse;
import com.bri.ojt.R;
import com.bri.ojt.Util.BaseActivity;
import com.bri.ojt.Util.Consts;

import java.util.Locale;

public class TarikTunaiSuccessActivity extends BaseActivity {

    private TextView txtOTP, txtTimer;
    private Button btnDone;
    private TarikTunaiResponse tartunData;
    private int from;
    private int minutes, seconds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tartunData = (TarikTunaiResponse) getIntent().getSerializableExtra(Consts.KEY_TARTUN_OTP);
        from = getIntent().getIntExtra(Consts.KEY_TARTUN_FROM, 0);

        Thread thread = new Thread(this::setView);
        thread.start();
    }

    private void setView() {
        txtOTP = findViewById(R.id.txt_no_otp);
        txtTimer = findViewById(R.id.txt_timer);
        btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(onClickListener);

        txtOTP.setText(tartunData.getOneTimePass());

        long expiredTime;

        if (from == 0) {
            // 0 from echannel, 1 from Teller
            expiredTime = 1000 * (Long.parseLong(tartunData.getExpired()));
        } else {
            expiredTime = tartunData.getExpiredMillisTeller() - System.currentTimeMillis();
        }

        runOnUiThread(() -> {
            CountDownTimer countDownTimer = new CountDownTimer(expiredTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    txtTimer.setText("Masa waktu habis");
                }
            };

            countDownTimer.start();
        });
    }

    public  void updateTimer(long timer){
        minutes = (int) timer / 60000;
        seconds = (int) timer / 1000 % 60;

        String timeLifeText = "";

        if (seconds < 10) timeLifeText += "0";
        timeLifeText += seconds;

        String expired = String.format(Locale.US,"%d Menit %s Detik", minutes, timeLifeText);

        txtTimer.setText(expired);
    }

    private View.OnClickListener onClickListener = v -> {
        startActivity(new Intent(TarikTunaiSuccessActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    };

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_tarik_tunai_success;
    }

    @Override
    protected String getActivityTitle() {
        return "Tarik Tunai";
    }
}
