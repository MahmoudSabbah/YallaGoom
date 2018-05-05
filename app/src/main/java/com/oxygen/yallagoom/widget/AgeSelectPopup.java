package com.oxygen.yallagoom.widget;


import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.interfaces.AgePopUpCallback;

public class AgeSelectPopup extends PopupWindow {

    private  TextView min;
    private  TextView max;
    private  RangeSeekBar seekbar;
    private  LinearLayout content;
    private  TextView name;
    private LinearLayout parent;
    private AgePopUpCallback agePopUpCallback;
    private TextView  ok_bt, cancelBtn;

    public AgeSelectPopup(Context context, final AgePopUpCallback agePopUpCallback) {
        this.agePopUpCallback = agePopUpCallback;
        final RelativeLayout popupView = (RelativeLayout) LayoutInflater.from(
                context).inflate(R.layout.age_select_popup, null);
        name = (TextView) popupView.findViewById(R.id.name);
        parent = (LinearLayout) popupView.findViewById(R.id.parent);
        content = (LinearLayout) popupView.findViewById(R.id.content);
        cancelBtn = (TextView) popupView.findViewById(R.id.cancel_bt);
        min = (TextView) popupView.findViewById(R.id.min);
        max = (TextView) popupView.findViewById(R.id.max);
        seekbar = (RangeSeekBar) popupView.findViewById(R.id.seekbar);
        seekbar.setValue(16,63);
        seekbar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float minValue, float maxValue, boolean isFromUser) {
                min.setText((int)minValue+"");
                max.setText((int)maxValue+"");
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
        ok_bt = (TextView) popupView.findViewById(R.id.ok_bt);
        ok_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                agePopUpCallback.processFinish(Integer.parseInt(min.getText().toString()),Integer.parseInt(max.getText().toString()));

            }
        });
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        parent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dismiss();
                return false;
            }
        });
        content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

      /*  cancelBtn = (TextView) popupView.findViewById(R.id.choose_cancel);
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });*/

        setContentView(popupView);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setAnimationStyle(R.style.BottomPopupAnimStyle);

        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.photo_choice_popup_bg));


        popupView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = popupView.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    public AgeSelectPopup(Context context) {
        super(context);
    }

}
