package com.bri.ojt.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bri.ojt.Dialog.BankListInterface;
import com.bri.ojt.Model.Response.BankListResponse;
import com.bri.ojt.R;

import java.util.ArrayList;
import java.util.List;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankListViewHolder> implements Filterable {

    private List<BankListResponse.Data> dataList;
    private List<BankListResponse.Data> filteredData;
    private BankListInterface actionListener;

    public BankListAdapter(List<BankListResponse.Data> dataList, BankListInterface actionListener) {
        this.dataList = dataList;
        this.filteredData = dataList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public BankListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_banklist, viewGroup, false);
        return new BankListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankListViewHolder holder, int i) {
        BankListResponse.Data data = filteredData.get(i);

        holder.bankName.setText(data.getBankName());
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keyword = constraint.toString();
                List<BankListResponse.Data> result = new ArrayList<>();

                if (keyword.isEmpty()) {
                    result.addAll(dataList);
                } else {
                    for (BankListResponse.Data item : dataList) {
                        if (item.getBankName().toLowerCase().contains(keyword.toLowerCase())) {
                            result.add(item);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = result;
                filterResults.count = result.size();
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<BankListResponse.Data>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class BankListViewHolder extends RecyclerView.ViewHolder {

        TextView bankName;

        BankListViewHolder(View v) {
            super(v);

            bankName = v.findViewById(R.id.txt_bank_name);
            itemView.setOnClickListener(v1 -> actionListener.selectedBank(filteredData.get(getAdapterPosition())));
        }
    }
}
