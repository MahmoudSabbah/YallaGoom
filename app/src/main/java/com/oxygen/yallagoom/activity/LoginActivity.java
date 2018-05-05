package com.oxygen.yallagoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.api.LoginSecondStepAsyncTask;
import com.oxygen.yallagoom.api.LoginfirstStepAsyncTask;
import com.oxygen.yallagoom.entity.User;
import com.oxygen.yallagoom.interfaces.LoginFirstStepCallback;
import com.oxygen.yallagoom.utils.ToolUtils;

public class LoginActivity extends AppCompatActivity {

    private TextView logo_text;
    private TextView sign_up;
    private TextView name_user;
    private EditText password_edit;
    private EditText mobile_edit;
    private TextView skip;
    private int flag = 0;
    private LinearLayout back_lay;
    private TextView login;
    private RelativeLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ToolUtils.hideStatus(LoginActivity.this);
        parent = (RelativeLayout) findViewById(R.id.parent);
        ToolUtils.hideKeyFromScreen(parent,LoginActivity.this);
        skip = (TextView) findViewById(R.id.skip);
        name_user = (TextView) findViewById(R.id.name_user);
        mobile_edit = (EditText) findViewById(R.id.mobile_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        back_lay = (LinearLayout) findViewById(R.id.back_lay);
        login = (TextView) findViewById(R.id.login);
        sign_up = (TextView) findViewById(R.id.sign_up);
        logo_text = (TextView) findViewById(R.id.logo_text);
        logo_text.setTypeface(ToolUtils.font(LoginActivity.this, "Gilroy-Black.ttf"));
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 1) {
                    if (password_edit.getText().toString().equalsIgnoreCase("")) {
                        ToolUtils.showSnak(LoginActivity.this, getString(R.string.check_password));
                    } else if (password_edit.getText().toString().toCharArray().length < 6) {
                        ToolUtils.showSnak(LoginActivity.this, getString(R.string.password_size));
                    } else {
                        LoginSecondStepAsyncTask loginSecondStepAsyncTask = new LoginSecondStepAsyncTask(LoginActivity.this);
                        loginSecondStepAsyncTask.execute(mobile_edit.getText().toString(), password_edit.getText().toString());
                    }


                } else {
                    if (!mobile_edit.getText().toString().equalsIgnoreCase("")) {
                        LoginfirstStepAsyncTask loginfirstStepAsyncTask = new LoginfirstStepAsyncTask(LoginActivity.this, new LoginFirstStepCallback() {
                            @Override
                            public void processFinish(User user) {
                                name_user.setText(user.getName());
                                skip.setVisibility(View.GONE);
                                mobile_edit.setVisibility(View.INVISIBLE);
                                password_edit.setVisibility(View.VISIBLE);
                                back_lay.setVisibility(View.VISIBLE);
                                name_user.setVisibility(View.VISIBLE);
                                login.setText(getString(R.string.log_in));
                                flag = 1;
                            }
                        });
                        loginfirstStepAsyncTask.execute(mobile_edit.getText().toString());
                    } else {
                        ToolUtils.viewToast(LoginActivity.this, getString(R.string.enter_email_mobil));
                    }


                }

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Back(View view) {
        flag = 0;
        skip.setVisibility(View.VISIBLE);
        mobile_edit.setVisibility(View.VISIBLE);
        password_edit.setVisibility(View.GONE);
        back_lay.setVisibility(View.GONE);
        name_user.setVisibility(View.GONE);
        login.setText(getString(R.string.next));
    }
    public static void startLoginActivity(Activity activity){
        Intent intent=new Intent(activity,LoginActivity.class);
        activity.startActivity(intent);
    }
}
