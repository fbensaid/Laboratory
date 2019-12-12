package com.mtp.laboproject.view.ui.view;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.TextViewCompat;

import com.mtp.laboproject.global.Constants;


/**
 * Created on 2/2/18.
 */

public class CustomButton extends AppCompatButton {

    private static final String TAG = CustomButton.class.getSimpleName();

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomButton(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomButton(context, attrs);
    }

    private void initCustomButton(Context context, AttributeSet attrs) {

        int maxLines = TextViewCompat.getMaxLines(this);

        if (maxLines == 1) {
            setSingleLine(true);
        }

        setAllCaps(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        float x = event.getX();
        float y = event.getY();
        if (rectF.contains(x, y) && (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE)) {
            setAlpha(Constants.Variants.CLICK_OPACITY);
        } else {
            setAlpha(1f);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}

