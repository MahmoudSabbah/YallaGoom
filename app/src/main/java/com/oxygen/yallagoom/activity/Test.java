package com.oxygen.yallagoom.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devlomi.record_view.OnRecordListener;
import com.devlomi.record_view.RecordButton;
import com.devlomi.record_view.RecordView;
import com.oxygen.yallagoom.R;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by Mahmoud Sabbah on 2/15/2018.
 */

public class Test extends AppCompatActivity implements View.OnClickListener {


    private EmojiconEditText edit_chat;
    private TextView fa_camera;
    private TextView fa_image;
    private TextView fa_microphone;
    private ImageView send_bt;
    private ImageView like_bt;
    private View contentRoot;
    private EmojIconActions emojIcon;
    private ImageView smile_icon;
    private RelativeLayout send_mes_lay;
    private MediaPlayer mediaPlayer;
    private Chronometer counter_tv;
    private RelativeLayout counter_lay;
    private TextView fa_close;
    private TextView fa_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        contentRoot = findViewById(R.id.contentRoot);

        send_mes_lay = (RelativeLayout) findViewById(R.id.send_mes_lay);
        counter_lay = (RelativeLayout) findViewById(R.id.counter_lay);
        counter_tv = (Chronometer) findViewById(R.id.counter_tv);
        fa_camera = (TextView) findViewById(R.id.fa_camera);
        fa_image = (TextView) findViewById(R.id.fa_image);
        fa_microphone = (TextView) findViewById(R.id.fa_microphone);
        fa_close = (TextView) findViewById(R.id.close);
        fa_ok = (TextView) findViewById(R.id.ok);
        //   recordView = (RecordView) findViewById(R.id.record_view);
        // fa_microphone.setRecordView(recordView);

        smile_icon = (ImageView) findViewById(R.id.buttonEmoji);
        send_bt = (ImageView) findViewById(R.id.send_bt);
        like_bt = (ImageView) findViewById(R.id.like_bt);
        edit_chat = (EmojiconEditText) findViewById(R.id.editTextMessage);
        emojIcon = new EmojIconActions(this, contentRoot, edit_chat, smile_icon);
        emojIcon.ShowEmojIcon();
        edit_chat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                fa_camera.setVisibility(View.GONE);
                fa_image.setVisibility(View.GONE);
                 fa_microphone.setVisibility(View.GONE);
            }
        });
        edit_chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("toString", "" + s.toString());
                if (s.toString().equalsIgnoreCase("")) {
                    fa_camera.setVisibility(View.VISIBLE);
                    fa_image.setVisibility(View.VISIBLE);
                    fa_microphone.setVisibility(View.VISIBLE);
                    send_bt.setVisibility(View.GONE);
                    like_bt.setVisibility(View.VISIBLE);
                } else {
                    fa_camera.setVisibility(View.GONE);
                    fa_image.setVisibility(View.GONE);
                    fa_microphone.setVisibility(View.GONE);
                    like_bt.setVisibility(View.GONE);
                    send_bt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fa_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fa_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final Animation blink = AnimationUtils.loadAnimation(this, R.anim.blink);

        fa_microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer   mediaPlayer = MediaPlayer.create(Test.this, R.raw.record_start);
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
                fa_microphone.startAnimation(blink);
                counter_lay.setVisibility(View.VISIBLE);
                counter_tv.setBase(SystemClock.elapsedRealtime());
                counter_tv.start();
                fa_microphone.setTextColor(ContextCompat.getColor(Test.this,R.color.red_light));

            }
        });
        counter_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fa_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null){
                    mediaPlayer.stop();
                }
                fa_microphone.clearAnimation();
                fa_microphone.setTextColor(ContextCompat.getColor(Test.this,R.color.color_929292));
                counter_lay.setVisibility(View.GONE);
                counter_tv.stop();
            }
        });
        fa_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }
}
