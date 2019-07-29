package com.bri.ojt.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bri.ojt.Util.Consts;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Consts consts = Consts.getInstance();

        if (consts.isLogin()) {
            startActivity(new Intent(SplashscreenActivity.this, HomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
            finish();
        }


    }
}
