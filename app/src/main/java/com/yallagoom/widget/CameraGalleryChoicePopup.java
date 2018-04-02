package com.yallagoom.widget;


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

import com.yallagoom.R;
import com.yallagoom.interfaces.ClickPopUpCallback;

public class CameraGalleryChoicePopup extends PopupWindow {

    private  TextView camera_bt;
    private  TextView gallery_bt;
    private  TextView name;
    private LinearLayout parent;
    private ClickPopUpCallback clickPopUpCallback;
    private TextView  editBtn, cancelBtn;

    public CameraGalleryChoicePopup(Context context, String title, final ClickPopUpCallback clickPopUpCallback) {
        this.clickPopUpCallback = clickPopUpCallback;
        final RelativeLayout popupView = (RelativeLayout) LayoutInflater.from(
                context).inflate(R.layout.camera_gallery_choice_popup, null);

        name = (TextView) popupView.findViewById(R.id.name);
        name.setText(title);
        parent = (LinearLayout) popupView.findViewById(R.id.parent);
        camera_bt = (TextView) popupView.findViewById(R.id.camera_bt);
        gallery_bt = (TextView) popupView.findViewById(R.id.gallery_bt);
        camera_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPopUpCallback.processFinish(0);

            }
        });
        gallery_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPopUpCallback.processFinish(1);

            }
        });
        parent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dismiss();
                return false;
            }
        });
    /*    editBtn = (TextView) popupView.findViewById(R.id.edit_bt);
        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPopUpCallback.processFinish(1);
            }
        });
        editBtn.setOnClickListener(clickListener);*/
        cancelBtn = (TextView) popupView.findViewById(R.id.choose_cancel);
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

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

    public CameraGalleryChoicePopup(Context context) {
        super(context);
    }

}
