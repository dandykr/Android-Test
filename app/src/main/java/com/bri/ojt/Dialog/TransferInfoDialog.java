package com.bri.ojt.Dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bri.ojt.Model.Request.ReqTransferExternal;
import com.bri.ojt.Model.Request.ReqTransferInternal;
import com.bri.ojt.R;
import com.bri.ojt.Util.Consts;

public class TransferInfoDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private TextView vaSource, vaBeneficiary, namaBeneficiary, noReferensi, nominal, deskripsi;
    private Button btnLanjut;
    private ReqTransferInternal transferInternal;
    private ReqTransferExternal transferExternal;
    private int transferType;
    private TransferInfo mListener;

    public TransferInfoDialog() {
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        transferType = getArguments().getInt(Consts.KEY_TRANSFER_TYPE);
        if (transferType == Consts.TRANSFER_INFO_INTERNAL) {
            transferInternal = (ReqTransferInternal) getArguments().getSerializable(Consts.KEY_TRANSFER_INFO_DIALOG);
        } else {
            transferExternal = (ReqTransferExternal) getArguments().getSerializable(Consts.KEY_TRANSFER_INFO_DIALOG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_transferinfo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vaSource = view.findViewById(R.id.va_source);
        vaBeneficiary = view.findViewById(R.id.va_beneficiary);
        namaBeneficiary = view.findViewById(R.id.nama_beneficiary);
        nominal = view.findViewById(R.id.nominal);
        noReferensi = view.findViewById(R.id.no_ref);
        deskripsi = view.findViewById(R.id.deskripsi);
        btnLanjut = view.findViewById(R.id.btn_lanjut);

        vaSource.setText(transferType == Consts.TRANSFER_INFO_INTERNAL ? transferInternal.getSourceAccount() : transferExternal.getSourceAcc());
        vaBeneficiary.setText(transferType == Consts.TRANSFER_INFO_INTERNAL ? transferInternal.getBeneficiaryAccount() : transferExternal.getBeneficiaryAcc());
        namaBeneficiary.setText(transferType == Consts.TRANSFER_INFO_INTERNAL ? transferInternal.getBeneficiaryAccountName() : transferExternal.getBeneficiaryAccName());
        nominal.setText(transferType == Consts.TRANSFER_INFO_INTERNAL ? transferInternal.getAmount() : transferExternal.getAmount());
        noReferensi.setText(transferType == Consts.TRANSFER_INFO_INTERNAL ? transferInternal.getNoReferral() : transferExternal.getNoReferral());
        deskripsi.setText(transferType == Consts.TRANSFER_INFO_INTERNAL ? transferInternal.getRemark() : "-");
        btnLanjut.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lanjut:
                this.dismiss();
                mListener.dialogInfoDone();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (TransferInfo) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TransferInfo interface");
        }
    }
}
