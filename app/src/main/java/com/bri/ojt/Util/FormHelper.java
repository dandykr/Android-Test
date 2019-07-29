package com.bri.ojt.Util;

import java.util.Locale;

public class FormHelper {

    public FormHelper() {
    }

    public static String addThousandSeparator (double value) {
        return String.format(new Locale("id"),"%,.0f", value);
    }

    public static String trimPeriod(String string){
        String returnString;
        if (string.contains(".")){
            return string.replace(".","");
        } else {
            return string;
        }
    }
}
