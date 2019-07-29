package com.bri.ojt.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bri.biometricauth.BiometricUtils;
import com.bri.biometricauth.FingerprintDialog;
import com.bri.biometricauth.FingerprintUiHelper;
import com.bri.ojt.Model.Response.GetTokenResponse;
import com.bri.ojt.Model.Response.VAbyGroupResponseCashcard;
import com.bri.ojt.Network.API;
import com.bri.ojt.Network.APICall;
import com.bri.ojt.Network.ResponseInterface;
import com.bri.ojt.Network.RetrofitClient;
import com.bri.ojt.R;
import com.bri.ojt.Util.Consts;
import com.bri.ojt.Util.SnackbarUtil;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;

import javax.net.ssl.SSLPeerUnverifiedException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ResponseInterface, FingerprintUiHelper.Callback {

    private Button btnLogin;
    private ImageButton btnFingerprint;
    private EditText etVA, etPassword;
    private API api;
    private ProgressBar progressBar;
    private TextInputLayout etValayout, etPwlayout;
    private ConstraintLayout constraintLayout;
    boolean sessionEnd;

    private Consts consts = Consts.getInstance();

    private FingerprintDialog fingerprintDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Thread thread = new Thread(this::setView);
        thread.start();

        sessionEnd = getIntent().getBooleanExtra(Consts.KEY_SESSION_END, false);
    }

    private void setView() {
        api = RetrofitClient.getHInstance(this).getAPI();
        btnLogin = findViewById(R.id.btn_login);
        btnFingerprint = findViewById(R.id.btn_fingerprint);
        etVA = findViewById(R.id.et_va);
        etPassword = findViewById(R.id.et_password);
        progressBar = findViewById(R.id.progress_bar);
        etValayout = findViewById(R.id.input_layout_va);
        etPwlayout = findViewById(R.id.input_layout_pw);
        constraintLayout = findViewById(R.id.constraint_layout);

        btnLogin.setOnClickListener(onClickListener);
        btnFingerprint.setOnClickListener(onClickListener);

        if (BiometricUtils.isFingerprintSupported(this)) {
            fingerprintDialog = new FingerprintDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionEnd) {
            SnackbarUtil.getSnackbar(this, constraintLayout, "Session Anda telah berakhir.").show();
        }
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_login:
                if (etPassword.length() > 1) {
                    doLogin();
                } else {
                    final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
                    etPwlayout.startAnimation(animShake);
                }
                break;
            case R.id.btn_fingerprint:
                fingerprintDialog.show(getSupportFragmentManager(), "Fingerprint");
                break;
                default:
                    break;
        }
    };

    private void doLogin() {
        if (etVA.length() > 1) {
            getToken();
        } else {
            final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
            etValayout.startAnimation(animShake);
        }
    }

    private void getToken() {
        consts.setVAEncrypt(etVA.getText().toString());
        switchProgressbar();
        Call<GetTokenResponse> call = api.getAccessToken(consts.getCIDEncrypt(),consts.getCSecretEncrypt());
        call.enqueue(new Callback<GetTokenResponse>() {
            @Override
            public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                switchProgressbar();
                GetTokenResponse tokenResponse = response.body();
                if (response.code() == 200 && tokenResponse != null) {
                    Consts.getInstance().setTokenEncrypt(tokenResponse.getAccessToken());
                    getVA();
//                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                switchProgressbar();
                if (t instanceof SSLPeerUnverifiedException) {
                    Toast.makeText(LoginActivity.this, "SSL Pinning Fail", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getVA() {
        switchProgressbar();
        APICall call = new APICall(new APICall.APICallback() {
            @Override
            public void onSuccess(Object data) {
                switchProgressbar();
                if (data instanceof VAbyGroupResponseCashcard.DetailVA) {
                    VAbyGroupResponseCashcard.DetailVA detailVA = (VAbyGroupResponseCashcard.DetailVA) data;
                    requestOTP(detailVA);
                }
            }

            @Override
            public void onFailed(String message) {
                switchProgressbar();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        call.getVAbyVA(consts.getVAEncrypt());
    }

    private void requestOTP(VAbyGroupResponseCashcard.DetailVA detailVA) {
        switchProgressbar();
        APICall call = new APICall(new APICall.APICallback() {
            @Override
            public void onSuccess(Object data) {
                switchProgressbar();
                Intent intent = new Intent(LoginActivity.this, VerifyOTPActivity.class);
                intent.putExtra("detail-va", detailVA);
                startActivity(intent);
            }

            @Override
            public void onFailed(String message) {
                switchProgressbar();
                Log.e("LoginActivity", message);
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        call.requestOTP(detailVA, APICall.OTPVIA.EMAIL);
    }

    private void switchProgressbar() {
        btnFingerprint.setVisibility(btnFingerprint.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        progressBar.setVisibility(progressBar.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    @Override
    public void OnBadRequest(String message) {

    }

    @Override
    public void OnUnauthorized(String message) {

    }

    @Override
    public void onFingerprintAuthenticated() {
        fingerprintDialog.dismiss();
        doLogin();
    }

    @Override
    public void onFingerprintError() {
        fingerprintDialog.dismiss();

        SnackbarUtil.getSnackbar(this, constraintLayout, "Error login fingerprint").show();
    }
}
