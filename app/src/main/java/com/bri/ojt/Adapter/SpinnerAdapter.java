package com.bri.ojt.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bri.ojt.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private final LayoutInflater inflater;
    private final int mResource;


    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
        this.mResource = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return rowView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return rowView(position, convertView, parent);
    }

    private View rowView (int position, View convertView, ViewGroup viewGroup) {
        String item = getItem(position);
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(mResource, viewGroup, false);
        }

        TextView textView = view.findViewById(R.id.spinner_text);
        textView.setText(item);

        return view;
    }
}
