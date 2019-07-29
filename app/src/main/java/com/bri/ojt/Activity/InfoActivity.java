package com.bri.ojt.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bri.ojt.R;
import com.bri.ojt.Util.BaseActivity;

public class InfoActivity extends BaseActivity {

    private BottomSheetBehavior sheetBehavior;
    private TextView btnLokasiATM, btnLokasiUker, btnContactInfo, btnLogout, btnCall, btnLocationonMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread thread = new Thread(this::setView);
        thread.start();
    }

    private void setView() {
        LinearLayout contactBS = findViewById(R.id.bottom_sheet);
        btnLokasiATM = findViewById(R.id.btn_atm_loc);
        btnLokasiUker = findViewById(R.id.btn_uker_loc);
        btnContactInfo = findViewById(R.id.btn_contact_info);
        btnLogout = findViewById(R.id.btn_logout);
        btnCall = findViewById(R.id.btn_contact);
        btnLocationonMap = findViewById(R.id.btn_location_onmap);

        sheetBehavior = BottomSheetBehavior.from(contactBS);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        btnLokasiATM.setOnClickListener(onClickListener);
        btnLokasiUker.setOnClickListener(onClickListener);
        btnContactInfo.setOnClickListener(onClickListener);
        btnLogout.setOnClickListener(onClickListener);
        btnCall.setOnClickListener(onClickListener);
        btnLocationonMap.setOnClickListener(onClickListener);


    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_contact_info:
                sheetBehavior.setState(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ?
                        BottomSheetBehavior.STATE_HIDDEN : BottomSheetBehavior.STATE_EXPANDED);
            break;
            case R.id.btn_atm_loc:
                startActivity(new Intent(InfoActivity.this, LocationActivity.class).putExtra("from", 100));
                break;
            case R.id.btn_uker_loc:
                startActivity(new Intent(InfoActivity.this, LocationActivity.class).putExtra("from", 200));
                break;
            case R.id.btn_logout:
                doLogout(false);
                break;
            case R.id.btn_contact:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", btnCall.getText().toString(), null)));
                break;
            case R.id.btn_location_onmap:
                Uri briHeadquarterLoc = Uri.parse(getResources().getString(R.string.info_location));
                Intent intent = new Intent(Intent.ACTION_VIEW, briHeadquarterLoc);
                startActivity(intent);
                break;
        }
    };

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_information;
    }

    @Override
    protected String getActivityTitle() {
        return "";
    }
}
