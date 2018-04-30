package com.yallagoom.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.api.settings.GetTearmAndPrivacyAsyncTask;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.ToolUtils;

public class SettingsHomeClickPrivacyTermsActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView name_header;
    private TextView left_text;
    private TextView right_text;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_home_click_privacy_terms);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(SettingsHomeClickPrivacyTermsActivity.this);
        ToolUtils.setLightStatusBar(parent, SettingsHomeClickPrivacyTermsActivity.this);

        name_header = (TextView) findViewById(R.id.name_header);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(getString(R.string.back));
        right_text = (TextView) findViewById(R.id.right_text);
        right_text.setVisibility(View.INVISIBLE);
        if (getIntent().getExtras().getString("type").equalsIgnoreCase("privacy")) {
            name_header.setText(getString(R.string.privacy_policy));
        } else {
            name_header.setText(getString(R.string.terms_amp_amp_conditions));
        }
        description = (TextView) findViewById(R.id.description);

        getData();
    }

    public void Back(View view) {
        finish();
    }

    private void getData() {
        GetTearmAndPrivacyAsyncTask getTearmAndPrivacyAsyncTask = new GetTearmAndPrivacyAsyncTask(SettingsHomeClickPrivacyTermsActivity.this, new StringResultCallback() {
            @Override
            public void processFinish(String result, KProgressHUD progress) {
                progress.dismiss();
                ToolUtils.setHtmlToTextView(description, result);
            }
        });
        if (getIntent().getExtras().getString("type").equalsIgnoreCase("privacy")) {
            getTearmAndPrivacyAsyncTask.execute("privacy");
        } else {
            getTearmAndPrivacyAsyncTask.execute("terms");
        }
    }
}
