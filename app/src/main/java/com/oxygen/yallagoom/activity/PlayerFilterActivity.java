package com.oxygen.yallagoom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.RangeSeekBar;
import com.oxygen.yallagoom.widget.segmented.SegmentedGroup;

public class PlayerFilterActivity extends AppCompatActivity {

    private LinearLayout parent;
    private RangeSeekBar seekbar;
    private EditText search_sport_EditText;
    private EditText country_edit_EditText;
    private TextView ok;
    private EditText title_EditText;
    private TextView title_remove;
    private TextView search_sport_remove;
    private TextView country_remove_;
    private SegmentedGroup segmented1;
    private SegmentedGroup segmented2;
    private int country_id;
    private int sport_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_filter);
        ToolUtils.hideStatus(PlayerFilterActivity.this);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.setLightStatusBar(parent, PlayerFilterActivity.this);

        ok = (TextView) findViewById(R.id.ok);
        title_EditText = (EditText) findViewById(R.id.title);
        title_remove = (TextView) findViewById(R.id.title_remove);

        search_sport_EditText = (EditText) findViewById(R.id.search_sport);
        search_sport_remove = (TextView) findViewById(R.id.search_sport_remove);

        country_edit_EditText = (EditText) findViewById(R.id.country_edit);
        country_remove_ = (TextView) findViewById(R.id.country_remove);

        search_sport_EditText.setFocusable(false);
        country_edit_EditText.setFocusable(false);

        segmented1 = (SegmentedGroup) findViewById(R.id.segmented1);
        segmented2 = (SegmentedGroup) findViewById(R.id.segmented2);

        seekbar = (RangeSeekBar) findViewById(R.id.seekbar);
        seekbar.setValue(14f, 65f);
        seekbar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                Log.e("", "min: " + min + " , max: " + max);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
                //do what you want!!
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                //do what you want!!
            }
        });
        search_sport_EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayerFilterActivity.this, SearchSportsActivity.class);
                startActivityForResult(intent, 101);
            }
        });
        country_edit_EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayerFilterActivity.this, SearchCountryActivity.class);
                intent.putExtra("id",-1);
                startActivityForResult(intent, 102);
            }
        });
        search_sport_EditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        country_edit_EditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonIDSegmented1 = segmented1.getCheckedRadioButtonId();
                View radioButtonSegmented1 = segmented1.findViewById(radioButtonIDSegmented1);
                int idSegmented1 = segmented1.indexOfChild(radioButtonSegmented1);

                int radioButtonIDSegmented2 = segmented2.getCheckedRadioButtonId();
                View radioButtonSegmented2 = segmented2.findViewById(radioButtonIDSegmented2);
                int idSegmented2 = segmented2.indexOfChild(radioButtonSegmented2);

                Intent intent = new Intent();
                intent.putExtra("player_name", title_EditText.getText().toString());
                if (search_sport_EditText.getText().toString().equalsIgnoreCase("")) {
                    intent.putExtra("sport_id", -1);
                } else {
                    intent.putExtra("sport_id", sport_id);
                }
                intent.putExtra("sport", search_sport_EditText.getText().toString());

                if (idSegmented1 == 0) {
                    intent.putExtra("gender", "m");

                } else if (idSegmented1 == 1) {
                    intent.putExtra("gender", "f");
                } else if (idSegmented1 == 2) {
                    intent.putExtra("gender", "both");
                } else {
                    intent.putExtra("gender", "");
                }
                if (idSegmented2 == 0) {
                    intent.putExtra("rate", "B");

                } else if (idSegmented2 == 1) {
                    intent.putExtra("rate", "I");
                } else if (idSegmented2 == 2) {
                    intent.putExtra("rate", "P");
                } else {
                    intent.putExtra("rate", "");
                }
                intent.putExtra("max", Math.round(seekbar.getMax()));
                intent.putExtra("min", Math.round(seekbar.getMin()));
                if (country_edit_EditText.getText().toString().equalsIgnoreCase("")) {
                    intent.putExtra("country_id", -1);
                } else {
                    intent.putExtra("country_id", country_id);
                }
                setResult(108, intent);
                finish();
            }
        });
    }

    public void Cancel(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 102:
                if (resultCode == 102) {
                    country_id = data.getExtras().getInt("country_id");
                    country_edit_EditText.setText(data.getExtras().getString("country_name"));
                }
                break;
            case 101:
                if (resultCode == 102) {
                    sport_id = data.getExtras().getInt("sport_id");
                    search_sport_EditText.setText(data.getExtras().getString("sport_name"));
                }
                break;
        }
    }
}
