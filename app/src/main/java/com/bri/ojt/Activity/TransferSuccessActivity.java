package com.bri.ojt.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bri.ojt.Model.Request.ReqTransferInternal;
import com.bri.ojt.R;
import com.bri.ojt.Util.BaseActivity;
import com.bri.ojt.Util.BitmapHelper;
import com.bri.ojt.Util.Consts;
import com.bri.ojt.Util.FileHelper;
import com.bri.ojt.Util.NotificationUtil;

public class TransferSuccessActivity extends BaseActivity implements NotificationUtil.NotificationInterface {

    private TextView nominal, tglTransfer, vaSource, vaBeneficiary, namaBeneficiary, noReferensi, deskripsi, btnShare, btnSave;
    private Button btnDone;
    private CardView cardView;
    private Consts consts;
    private Bitmap image;
    private String imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReqTransferInternal reqTransferInternal = (ReqTransferInternal) getIntent().getSerializableExtra(Consts.KEY_TRANSFER_INFO_DIALOG);

        consts = Consts.getInstance();

        Thread thread = new Thread(() -> setView(reqTransferInternal));
        thread.start();

    }

    private void setView(ReqTransferInternal reqTransferInternal) {
        nominal = findViewById(R.id.nominal);
        tglTransfer = findViewById(R.id.tgl_transfer);
        vaSource = findViewById(R.id.va_source);
        vaBeneficiary = findViewById(R.id.va_beneficiary);
        namaBeneficiary = findViewById(R.id.nama_beneficiary);
        noReferensi = findViewById(R.id.no_ref);
        deskripsi = findViewById(R.id.deskripsi);
        cardView = findViewById(R.id.cardview);
        btnDone = findViewById(R.id.btn_done);
        btnShare = findViewById(R.id.btn_share);
        btnSave = findViewById(R.id.btn_save);
        btnDone.setOnClickListener(onClickListener);
        btnShare.setOnClickListener(onClickListener);
        btnSave.setOnClickListener(onClickListener);

        String format = String.format("IDR %s", reqTransferInternal.getAmount());
        nominal.setText(format);
        tglTransfer.setText(reqTransferInternal.getTransactionDateTime());
        vaSource.setText(consts.getVAEncrypt());
        vaBeneficiary.setText(reqTransferInternal.getBeneficiaryAccount());
        namaBeneficiary.setText(reqTransferInternal.getBeneficiaryAccountName());
        noReferensi.setText(reqTransferInternal.getNoReferral());
        deskripsi.setText(reqTransferInternal.getRemark());

        runOnUiThread(() -> {
            NotificationUtil notificationUtil = new NotificationUtil(TransferSuccessActivity.this);
            notificationUtil.sendNotification(consts.getDeviceId(), "Transfer Success", "Transfer to " + reqTransferInternal.getBeneficiaryAccountName());
        });

    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_done:
                startActivity(new Intent(TransferSuccessActivity.this, HomeActivity.class));
                TransferSuccessActivity.this.finish();
                break;
            case R.id.btn_share:
                if (checkAccessStorage(REQ_ACCESS_STORAGE_SHARE_FILE)) {
                    shareImage();
                }
                break;
            case R.id.btn_save:
                if (checkAccessStorage(REQ_ACCESS_STORAGE_SAVE_FILE)) {
                    saveImage();
                }
                break;
        }
    };

    private void shareImage() {
        if (image == null && imagePath == null) {
            image = BitmapHelper.getBitmapfromView(cardView);
            imagePath = FileHelper.saveFile(image);
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imagePath));
        startActivity(intent);
    }

    private void saveImage() {
        if (image == null && imagePath == null) {
            image = BitmapHelper.getBitmapfromView(cardView);
            imagePath = FileHelper.saveFile(image);
        }

        showMessage("Berhasil disimpan di" + imagePath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQ_ACCESS_STORAGE_SAVE_FILE:
                    saveImage();
                    break;
                case REQ_ACCESS_STORAGE_SHARE_FILE:
                    shareImage();
                    break;
            }
        } else {
            showMessage("Akses Storage dibutuhkan untuk menyimpan file.");
        }


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_transfer_success;
    }

    @Override
    protected String getActivityTitle() {
        return "Transfer Berhasil";
    }

    @Override
    public void onSuccess() {
        showMessage("Notification Send Successfully");
    }

    @Override
    public void onFailed(String message) {
        showErrorMessage(message);
    }
}
