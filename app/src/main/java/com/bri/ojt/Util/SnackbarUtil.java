package com.bri.ojt.Util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bri.ojt.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class SnackbarUtil {

    public static Snackbar getSnackbar(Context context, View view, String message) {
        Snackbar snackbar;

        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", v -> snackbar.dismiss());
        View view1 = snackbar.getView();
        try {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view1.getLayoutParams();
            params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
            params.width = FrameLayout.LayoutParams.MATCH_PARENT;
            view1.setLayoutParams(params);
        } catch (Exception ignored) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view1.getLayoutParams();
            params.height = CoordinatorLayout.LayoutParams.WRAP_CONTENT;
            params.width = CoordinatorLayout.LayoutParams.MATCH_PARENT;
            view1.setLayoutParams(params);
        }


        view1.setBackgroundColor(context.getColor(R.color.colorPrimary));
        TextView textView = view1.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setMaxLines(5);
        Button button = view1.findViewById(com.google.android.material.R.id.snackbar_action);
        button.setTextColor(Color.WHITE);
        button.setTypeface(null, Typeface.BOLD);

        return snackbar;
    }

    public static Snackbar getSnackbarWarning(Context context, View view, String message) {
        Snackbar snackbar;

        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", v -> snackbar.dismiss());
        View view1 = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view1.getLayoutParams();
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        view1.setLayoutParams(params);

        view1.setBackgroundColor(context.getColor(R.color.red800));
        TextView textView = view1.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setMaxLines(5);
        Button button = view1.findViewById(com.google.android.material.R.id.snackbar_action);
        button.setTextColor(Color.WHITE);
        button.setTypeface(null, Typeface.BOLD);

        return snackbar;
    }
}
