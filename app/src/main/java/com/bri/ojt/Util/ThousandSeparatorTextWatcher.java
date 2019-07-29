package com.bri.ojt.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Locale;

public class ThousandSeparatorTextWatcher implements TextWatcher {

    private EditText editText;

    public ThousandSeparatorTextWatcher(EditText editText){
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);

        try {
            String originalString = s.toString();

            Double doubval;
            if (originalString.contains(".")){
                originalString = originalString.replaceAll("\\.","");
            }
            doubval = Double.parseDouble(originalString);

            String format = String.format(new Locale("id"),"%,.0f", doubval);

            //setting text after format to EditText
            editText.setText(format);
            editText.setSelection(editText.getText().length());
        } catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }

        editText.addTextChangedListener(this);

    }

    public static String trimPeriodofString(String string){
        String returnString;
        if (string.contains(".")){
            return string.replace(".","");
        } else {
            return string;
        }
    }

    public static String AddThousandSeparator(int text){
        Double doubval = (double) text;
        return String.format(new Locale("id"), "%,.0f", doubval);
    }
}
