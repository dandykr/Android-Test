package com.bri.ojt.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bri.ojt.Model.Response.MutasiVAResponse;
import com.bri.ojt.R;
import com.bri.ojt.Util.FormHelper;

public class MutasiAdapter extends RecyclerView.Adapter<MutasiAdapter.MutasiViewHolder> {

    private MutasiVAResponse dataList;

    public MutasiAdapter(MutasiVAResponse dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MutasiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mutasi, viewGroup, false);
        return new MutasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MutasiViewHolder holder, int i) {
        MutasiVAResponse.DetailMutasi detailMutasi = dataList.getData().get(i);

        holder.tglTrx.setText(detailMutasi.getTglTrxi());
        holder.channel.setText(detailMutasi.getChannel());
        if (detailMutasi.getKeterangan().isEmpty()) {
            holder.keterangan.setVisibility(View.GONE);
        } else {
            holder.keterangan.setText(detailMutasi.getKeterangan());
        }
        if (detailMutasi.getAmountDebet() == 0.00) {
            // transaksi kredit
            holder.imgTrx.setImageResource(R.drawable.ic_in);
            String nominal = String.format("IDR %s", FormHelper.addThousandSeparator(detailMutasi.getAmountKredit()));
            holder.amount.setText(nominal);
        } else {
            // transaksi debet
            holder.imgTrx.setImageResource(R.drawable.ic_out);
            String nominal = String.format("IDR %s", FormHelper.addThousandSeparator(detailMutasi.getAmountDebet()));
            holder.amount.setText(nominal);
        }
        holder.circleDate.setText(detailMutasi.getTrxDay());

        if (i == getItemCount()-1) {
            float density = holder.itemView.getContext().getResources().getDisplayMetrics().density;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0, (int) (80* density));
            holder.itemView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.getData().size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MutasiViewHolder extends RecyclerView.ViewHolder {

        public TextView tglTrx, channel, keterangan, amount, circleDate;
        public ImageView imgTrx;

        public MutasiViewHolder(View v) {
            super(v);

            tglTrx = v.findViewById(R.id.tgl_trx);
            channel = v.findViewById(R.id.channel);
            keterangan = v.findViewById(R.id.keterangan);
            amount = v.findViewById(R.id.amount);
            circleDate = v.findViewById(R.id.circle_date);
            imgTrx = v.findViewById(R.id.img_trx);
        }
    }

}
