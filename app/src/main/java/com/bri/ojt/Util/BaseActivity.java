package com.bri.ojt.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bri.ojt.Activity.LoginActivity;
import com.bri.ojt.Network.ResponseInterface;
import com.bri.ojt.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

public abstract class BaseActivity extends AppCompatActivity implements ResponseInterface {

    private ImageButton btnBack;
    private TextView title;
    private View constraintLayout;

    protected static final int REQ_ACCESS_STORAGE_SAVE_FILE = 100;
    protected static final int REQ_ACCESS_STORAGE_SHARE_FILE = 200;
    protected static final int REQ_ACCESS_FINE_LOCATION = 300;
    protected static final int REQ_SETTINGS_LOCATION = 400;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(FLAG_SECURE);

        setContentView(getLayoutResourceId());
        btnBack = findViewById(R.id.btn_back);
        title = findViewById(R.id.txt_title);
        constraintLayout = findViewById(R.id.constraint_layout);
        btnBack.setOnClickListener(onClickListener);
        if (title!=null) title.setText(getActivityTitle());

    }

    private View.OnClickListener onClickListener = v -> this.onBackPressed();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // disable transition animation
//        overridePendingTransition(0,0);
    }

    @Override
    public void startActivity(Intent intent,@Nullable Bundle options) {
        super.startActivity(intent, options);
        // disable transition animation
//        overridePendingTransition(0,0);
    }

    @Override
    public void finish() {
        super.finish();
        // disable transition animation
//        overridePendingTransition(0,0);
    }

    protected abstract int getLayoutResourceId();
    protected abstract String getActivityTitle();
    protected void showMessage(String message){
        Snackbar snackbar = SnackbarUtil.getSnackbar(this, constraintLayout , message);
        snackbar.show();
    }

    protected void showErrorMessage(String message) {
        Snackbar snackbar = SnackbarUtil.getSnackbarWarning(this, constraintLayout , message);
        snackbar.show();
    }

    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    protected void showKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    protected void doLogout(boolean logout) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (logout) {
            intent.putExtra(Consts.KEY_SESSION_END, true);
            Consts.getInstance().setTokenEncrypt("");
            Consts.getInstance().setIsLogin(false);
        }

        startActivity(intent);
    }

    protected boolean checkAccessStorage(int REQUEST_CODE) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            return false;
        }
        return true;
    }

    protected boolean checkAccessLocation(int REQUEST_CODE) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void OnBadRequest(String message) {
        showErrorMessage(message);
    }

    @Override
    public void OnUnauthorized(String message) {
        doLogout(false);
    }

}
