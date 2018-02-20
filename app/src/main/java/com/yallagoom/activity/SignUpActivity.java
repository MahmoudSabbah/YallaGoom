package com.yallagoom.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.suke.widget.SwitchButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yallagoom.R;
import com.yallagoom.api.RegisterAsyncTask;
import com.yallagoom.utils.ToolUtils;

import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView logo_text;
    private TextView condition;
    private TextView condition1;
    private TextInputLayout date_float_label;
    private EditText dateEdite;
    private TextView sign_up_bt;
    private EditText first_name;
    private EditText lastname;
    private EditText email;
    private EditText password;
    private EditText country;
    private TextView code;
    private EditText phone;
    private EditText date;
    private EditText gender;
    private SwitchButton switch_button;
    private ScrollView parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ToolUtils.hideStatus(SignUpActivity.this);
        parent = (ScrollView) findViewById(R.id.parent);
        ToolUtils.hideKeyFromScreen(parent,SignUpActivity.this);
        date_float_label = (TextInputLayout) findViewById(R.id.date_float_label);
        dateEdite = (EditText) findViewById(R.id.date);
        first_name = (EditText) findViewById(R.id.first_name);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        country = (EditText) findViewById(R.id.country);
        country.setFocusable(false);
        code = (TextView) findViewById(R.id.code);
        phone = (EditText) findViewById(R.id.phone);
        date = (EditText) findViewById(R.id.date);
        gender = (EditText) findViewById(R.id.gender);
        gender.setFocusable(false);
        switch_button = (SwitchButton) findViewById(R.id.switch_button);
        dateEdite.setFocusable(false);

        dateEdite.setInputType(InputType.TYPE_CLASS_TEXT);
        dateEdite.requestFocus();
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(dateEdite, InputMethodManager.SHOW_FORCED);

        sign_up_bt = (TextView) findViewById(R.id.sign_up_bt);
        condition1 = (TextView) findViewById(R.id.condition1);
        condition = (TextView) findViewById(R.id.condition);
        condition.setTypeface(ToolUtils.font(SignUpActivity.this, "Roboto-Regular.ttf"));
        condition1.setTypeface(ToolUtils.font(SignUpActivity.this, "Roboto-Regular.ttf"));
        logo_text = (TextView) findViewById(R.id.logo_text);
        logo_text.setTypeface(ToolUtils.font(SignUpActivity.this, "Gilroy-Black.ttf"));
        dateEdite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SignUpActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });
        Link link = new Link(getString(R.string.terms_amp_amp_conditions))
                .setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.color_53c7f2))                  // optional, defaults to holo blue
                .setTextColorOfHighlightedLink(ContextCompat.getColor(SignUpActivity.this, R.color.color_53c7f2)) // optional, defaults to holo blue
                .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                .setUnderlined(true)                                       // optional, defaults to true
                .setBold(true)                                              // optional, defaults to false
                .setOnLongClickListener(new Link.OnLongClickListener() {
                    @Override
                    public void onLongClick(String clickedText) {
                        // long clicked
                    }
                })
                .setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        // single clicked
                    }
                });
        LinkBuilder.on(condition)
                .addLink(link)
                .build(); // create the clickable links
        sign_up_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first_name.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SignUpActivity.this, getString(R.string.check_first_name));
                } else if (lastname.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SignUpActivity.this, getString(R.string.check_last_name));
                } else if (email.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SignUpActivity.this, getString(R.string.check_email));
                }  else if (password.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SignUpActivity.this, getString(R.string.check_password));
                } else if (password.getText().toString().toCharArray().length<6) {
                    ToolUtils.showSnak(SignUpActivity.this, getString(R.string.password_size));
                } else if (country.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SignUpActivity.this, getString(R.string.check_country));
                } else if (phone.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SignUpActivity.this, getString(R.string.check_phone));
                } else if (date.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SignUpActivity.this, getString(R.string.check_date));
                } else if (!switch_button.isChecked()) {
                    ToolUtils.showSnak(SignUpActivity.this, getString(R.string.check_terms));
                } else {
                    RegisterAsyncTask registerAsnc = new RegisterAsyncTask(SignUpActivity.this);
                    registerAsnc.execute(email.getText().toString(), password.getText().toString(), first_name.getText().toString()
                            , lastname.getText().toString(), "1", date.getText().toString(), gender.getText().toString(), "+974" + phone.getText().toString());
                }

            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        dateEdite.setText(date);
    }

    public void Back(View view) {
        finish();
    }
  /*  SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
            "Title example",
            "OK",
            "Cancel",
            "Clean"
    );

                dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
        @Override
        public void onPositiveButtonClick(Date date) {
            Log.e("date",""+date);
            dateEdite.setText(""+date);
        }

        @Override
        public void onNegativeButtonClick(Date date) {
        }

        @Override
        public void onNeutralButtonClick(Date date) {
        }
    });
                dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");*/
}
