package com.bri.ojt.Dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bri.ojt.R;
import com.bri.ojt.Util.Consts;

public class HomeDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private TarikTunai mTartunListener;
    private TransferOption mTransferListener;
    private int mDialogType;

    public HomeDialog() {
    }

    public HomeDialog newInstance(int option) {
        HomeDialog homeDialog = new HomeDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("option", option);
        homeDialog.setArguments(bundle);
        return homeDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mDialogType == Consts.DIALOG_TARTUN) {
            return inflater.inflate(R.layout.dialog_tariktunai, container, false);
        }
        return inflater.inflate(R.layout.dialog_transfer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView btnOption1 = view.findViewById(R.id.btn_option1);
        TextView btnOption2 = view.findViewById(R.id.btn_option2);

        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_option1:
                this.dismiss();
                if (mDialogType == Consts.DIALOG_TARTUN) {
                    mTartunListener.tarikEchannel();
                } else {
                    mTransferListener.tfInternal();
                }
                break;
            case R.id.btn_option2:
                this.dismiss();
                if (mDialogType == Consts.DIALOG_TARTUN) {
                    mTartunListener.tarikTeller();
                } else {
                    mTransferListener.tfExternal();
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mDialogType = getArguments() != null ? getArguments().getInt("option", Consts.DIALOG_TARTUN) : 0;

        if (mDialogType == Consts.DIALOG_TARTUN) {
            try {
                mTartunListener = (TarikTunai) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement TarikTunai interface");
            }
        } else {
            try {
                mTransferListener = (TransferOption) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement TransferOption interface");
            }
        }

    }
}
