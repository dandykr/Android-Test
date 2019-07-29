package com.bri.ojt.Widget;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.bri.ojt.R;

public class ToggleButton extends AppCompatTextView {


    public ToggleButton(Context context) {
        super(context);
        init(context);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackground(context.getResources().getDrawable(R.drawable.bg_toggle, null));
        setClickable(true);
        setFocusable(true);
    }

    public void setToggled(boolean toggled) {
        setTextColor(toggled ? 0xFFFFFF : 0x616161);
        setSelected(toggled);
    }
}
