package com.yallagoom.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tumblr.permissme.PermissMe;
import com.yallagoom.R;
import com.yallagoom.adapter.SplashPagerAdapter;
import com.yallagoom.api.GetChannelsAsyncTask;
import com.yallagoom.api.GetCountriesAsyncTask;
import com.yallagoom.api.GetCountryCodeAsyncTask;
import com.yallagoom.api.GetMyDataProfileApiAsyncTask;
import com.yallagoom.api.RegisterUserTokenToFirebaseAsyncTask;
import com.yallagoom.app.MainApplication;
import com.yallagoom.entity.Country;
import com.yallagoom.entity.News.Channels;
import com.yallagoom.entity.User;
import com.yallagoom.interfaces.CheckGPSCallback;
import com.yallagoom.interfaces.GetCountriesCallback;
import com.yallagoom.interfaces.GetCountryCodeCallback;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.FirebaseUtils;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.animator.ViewAnimator;

import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;

import static io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider.REQUEST_CHECK_SETTINGS;

public class SplashScreenActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ViewPager viewpager;
    private CircleIndicator indicator;
    private TextView next;
    private TextView skip;
    private TextView logo_text;
    private TextView done;
    private RelativeLayout splash_imag;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
          /*  if(getIntent().hasExtra("type")){
                Log.e("type",""+ getIntent().getExtras().getString("type"));
            }else{
                // Do something else
            }*/

        ToolUtils.hideStatus(SplashScreenActivity.this);

        splash_imag = (RelativeLayout) findViewById(R.id.splash_imag);
        logo_text = (TextView) findViewById(R.id.logo_text);
        logo = (ImageView) findViewById(R.id.logo);
        skip = (TextView) findViewById(R.id.skip);
        done = (TextView) findViewById(R.id.done);
        next = (TextView) findViewById(R.id.next);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        SplashPagerAdapter mAdapter = new SplashPagerAdapter();
        viewpager.setAdapter(mAdapter);
        indicator.setViewPager(viewpager);
        mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("position", "" + position);
                if (viewpager.getCurrentItem() == 3) {
                    done.setVisibility(View.VISIBLE);
                } else {
                    done.setVisibility(View.GONE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewpager.setCurrentItem(getItem(+1), true);

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

      /*  if (getIntent().hasExtra("type")) {
            Intent intent = new Intent(this, NotificationActivity.class);
            switch (getIntent().getExtras().getString("type")) {
                case "friend_request":
                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("ActionNotification","friend_request");
                    break;
                case "you_got_new_friend":
                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("ActionNotification","you_got_new_friend");
                    break;

            }
            startActivity(intent);
            finish();
        } else {*/
            PermissMe.with(SplashScreenActivity.this)
                    .setRequiredPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)
                    .listener(new PermissMe.PermissionListener() {
                        @Override
                        public void onSuccess() {
                     /*   logo_text.startAnimation(animationDown);
                        logo.startAnimation(animation);*/
                            //  Log.e("test1", "test1");

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                ToolUtils.buildAlertMessageNoGps2(SplashScreenActivity.this, new CheckGPSCallback() {
                                    @Override
                                    public void processFinish(boolean b, Status status) {
                                        excNext();
                                    }
                                });
                            } else {
                                ToolUtils.buildAlertMessageNoGps(SplashScreenActivity.this, SplashScreenActivity.this, SplashScreenActivity.this, new CheckGPSCallback() {
                                    @Override
                                    public void processFinish(boolean check, Status status) {
                                        if (!check) {
                                            try {
                                                status.startResolutionForResult(
                                                        SplashScreenActivity.this, REQUEST_CHECK_SETTINGS);
                                            } catch (IntentSender.SendIntentException e) {

                                            }
                                        } else {

                                            excNext();
                                        }
                                    }
                                });

                            }
                        }

                        @Override
                        public void onRequiredPermissionDenied(String[] deniedPermissions,
                                                               boolean[] isAutoDenied) {
                            finish();
                        }

                        @Override
                        public void onOptionalPermissionDenied(String[] deniedPermissions,
                                                               boolean[] isAutoDenied) {
                            Log.e("test4", "test4");

                        }
                    })
                    .verifyPermissions();
     //   }


    }

    private int getItem(int i) {
        return viewpager.getCurrentItem() + i;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void excNext() {
        Log.e("token1", "" + FirebaseInstanceId.getInstance().getToken());
        logo.setVisibility(View.VISIBLE);
        logo_text.setVisibility(View.VISIBLE);
        ViewAnimator
                .animate(logo_text)
                .duration(2000)
                .slideBottom()
                .start();
        ViewAnimator
                .animate(logo)
                .duration(2000)
                .slideTop()
                .start();
        new CountDownTimer(2000, 2000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                GetCountryCodeAsyncTask getCountryCodeAsyncTask = new GetCountryCodeAsyncTask(SplashScreenActivity.this, new GetCountryCodeCallback() {
                    @Override
                    public void processFinish(String code, KProgressHUD progress) {
                        String alpha3Country = new Locale("en", code).getISO3Country();
                        Constant.alpha3Country = alpha3Country;
                        GetChannelsAsyncTask getChannelsAsyncTask = new GetChannelsAsyncTask(SplashScreenActivity.this, progress, new StringResultCallback() {
                            @Override
                            public void processFinish(String result, final KProgressHUD progress) {
                                Constant.ChannelsList = new Gson().fromJson(result, Channels[].class);
                                GetCountriesAsyncTask getCountriesAsyncTask = new GetCountriesAsyncTask(SplashScreenActivity.this, progress, new GetCountriesCallback() {
                                    @Override
                                    public void processFinish(Country country) {
                                        Constant.countriesData = country;
                                        if (MainApplication.verification_check) {
                                            GetMyDataProfileApiAsyncTask getMyDataProfileApiAsyncTask = new GetMyDataProfileApiAsyncTask(SplashScreenActivity.this, progress, new StringResultCallback() {
                                                @Override
                                                public void processFinish(String result, KProgressHUD progress) {
                                                    if (getIntent().hasExtra("type")) {
                                                        Intent intent = new Intent(SplashScreenActivity.this, NotificationActivity.class);
                                                        switch (getIntent().getExtras().getString("type")) {
                                                            case "friend_request":
                                                                intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                                                                intent.putExtra("ActionNotification","friend_request");
                                                                intent.putExtra("active",true);

                                                                break;
                                                            case "you_got_new_friend":
                                                                intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                                                                intent.putExtra("ActionNotification","you_got_new_friend");
                                                                intent.putExtra("active",true);
                                                                break;

                                                        }
                                                        startActivity(intent);
                                                        finish();
                                                    }else {
                                                        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }

                                                }
                                            });
                                            getMyDataProfileApiAsyncTask.execute();


                                        } else {
                                            splash_imag.setVisibility(View.GONE);

                                        }

                                    }
                                });
                                getCountriesAsyncTask.execute();


                            }
                        });
                        getChannelsAsyncTask.execute(Constant.alpha3Country);

                    }
                });
                getCountryCodeAsyncTask.execute();


            }

        }.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        excNext();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }

}
