package com.yallagoom.activity;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tumblr.permissme.PermissMe;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yalantis.ucrop.UCrop;
import com.yallagoom.R;
import com.yallagoom.api.UpdateUseImagerApiAsyncTask;
import com.yallagoom.api.UpdateUserApiAsyncTask;
import com.yallagoom.entity.User;
import com.yallagoom.fragment.SettingsFragment;
import com.yallagoom.interfaces.ClickPopUpCallback;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CameraGalleryChoicePopup;
import com.yallagoom.widget.CircularImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;

public class SettingsHomeClickProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final int RESULT_LOAD_IMAGE = 202;
    private static final int REQUEST_IMAGE_CAPTURE = 203;
    private ScrollView parent;
    private TextView name_header;
    private TextView left_text;
    private TextView right_text;
    private ImageLoader imageLoader;
    private TextView user_name;
    private CircularImageView user_image;
    private User user;
    private EditText first_name;
    private EditText last_name;
    private TextView date_of_birth;
    private TextView country;
    private EditText mobile;
    private EditText email;
    private LinearLayout date_of_birth_lay;
    private LinearLayout country_lay;
    private int country_id;
    private CameraGalleryChoicePopup photoPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_home_click_profile);
        parent = (ScrollView) findViewById(R.id.parent);
       /* ToolUtils.hideStatus(SettingsHomeClickProfileActivity.this); */
        ToolUtils.setLightStatusBar(parent, SettingsHomeClickProfileActivity.this);

        imageLoader = ImageLoader.getInstance();
        user_name = (TextView) findViewById(R.id.user_name);
        user_image = (CircularImageView) findViewById(R.id.user_image);
        user = new Gson().fromJson(ToolUtils.getSharedPreferences(this, Constant.userData).getString(Constant.allUserData, null), User.class);
        ToolUtils.setImage(Constant.imageUrl + user.getImg_url(), user_image, imageLoader);
        user_name.setText(user.getFirst_name() + " " + user.getLast_name());
        name_header = (TextView) findViewById(R.id.name_header);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(getString(R.string.back));
        right_text = (TextView) findViewById(R.id.right_text);
        right_text.setText(getString(R.string.save));
        name_header.setText(getString(R.string.profile));
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        date_of_birth_lay = (LinearLayout) findViewById(R.id.date_of_birth_lay);
        date_of_birth = (TextView) findViewById(R.id.date_of_birth);
        //date_of_birth.setFocusable(false);
        country_lay = (LinearLayout) findViewById(R.id.country_lay);
        country = (TextView) findViewById(R.id.country);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        first_name.setText(user.getFirst_name());
        last_name.setText(user.getLast_name());
        date_of_birth.setText(user.getBirth_date());
        mobile.setText(user.getMobile());
       /* mobile.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                mobile.requestLayout();
               getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);

                return false;
            }
        });*/
        email.setText(user.getEmail());
        date_of_birth_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SettingsHomeClickProfileActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(ContextCompat.getColor(SettingsHomeClickProfileActivity.this,R.color.color_df488a));
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });
        country.setText(user.getGet_country_data().getName_en());
        country_id = user.getGet_country_data().getId();
        country_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsHomeClickProfileActivity.this, SearchCountryActivity.class);
                intent.putExtra("id", country_id);
                startActivityForResult(intent, 102);
            }
        });
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first_name.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SettingsHomeClickProfileActivity.this, getString(R.string.check_first_name));
                } else if (last_name.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SettingsHomeClickProfileActivity.this, getString(R.string.check_last_name));
                } else if (email.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SettingsHomeClickProfileActivity.this, getString(R.string.check_email));
                } else if (country.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SettingsHomeClickProfileActivity.this, getString(R.string.check_country));
                } else if (mobile.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SettingsHomeClickProfileActivity.this, getString(R.string.check_phone));
                } else if (date_of_birth.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(SettingsHomeClickProfileActivity.this, getString(R.string.check_date));
                } else {
                    //  ArrayList<HashMap<String, String>> hashMapsData = new ArrayList<>();
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("email", email.getText().toString());
                    stringStringHashMap.put("first_name", first_name.getText().toString());
                    stringStringHashMap.put("last_name", last_name.getText().toString());
                    stringStringHashMap.put("birth_date", date_of_birth.getText().toString());
                    stringStringHashMap.put("mobile", mobile.getText().toString());
                    stringStringHashMap.put("country_id", country_id + "");
                    //   hashMapsData.add(stringStringHashMap);
                    //   stringStringHashMap.
                    UpdateUserApiAsyncTask updateUserApiAsyncTask = new UpdateUserApiAsyncTask(SettingsHomeClickProfileActivity.this,
                            stringStringHashMap, new StringResultCallback() {
                        @Override
                        public void processFinish(String result, KProgressHUD progress) {
                            SharedPreferences.Editor shared = ToolUtils.setSharedPrefernce(SettingsHomeClickProfileActivity.this, Constant.userData);
                            shared.putString(Constant.allUserData, result);
                            shared.apply();
                            ToolUtils.viewToast(SettingsHomeClickProfileActivity.this, getString(R.string.update_status));
                        }
                    });
                    updateUserApiAsyncTask.execute();
                }
            }
        });
        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissMe.with(SettingsHomeClickProfileActivity.this)
                        .setRequiredPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setRequiredPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .listener(new PermissMe.PermissionListener() {
                            @Override
                            public void onSuccess() {
                                photoPopup = new CameraGalleryChoicePopup(SettingsHomeClickProfileActivity.this, getString(R.string.camera_gallery_choose), new ClickPopUpCallback() {
                                    @Override
                                    public void processFinish(final int check) {
                                        photoPopup.dismiss();
                                        if (check == 0) {
                                            dispatchTakePictureIntent();
                                        } else {
                                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                            photoPickerIntent.setType("image/*");
                                            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
                                        }
                                    }
                                });
                                photoPopup.showAtLocation(((Activity) SettingsHomeClickProfileActivity.this).findViewById(R.id.parent),
                                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                            }

                            @Override
                            public void onRequiredPermissionDenied(String[] deniedPermissions,
                                                                   boolean[] isAutoDenied) {

                            }

                            @Override
                            public void onOptionalPermissionDenied(String[] deniedPermissions,
                                                                   boolean[] isAutoDenied) {

                            }
                        })
                        .verifyPermissions();

            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void Back(View view) {
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        date_of_birth.setText(ToolUtils.changeDateFormat(date, "yyyy-MM-dd", "yyyy-MM-dd"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 102:
                if (resultCode == 102) {
                    country_id = data.getExtras().getInt("country_id");
                    country.setText(data.getExtras().getString("country_name"));
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    String destinationFileName = "crop";
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    UCrop.of(ToolUtils.getImageUri(SettingsHomeClickProfileActivity.this, imageBitmap)
                            , Uri.fromFile(new File(getCacheDir(), destinationFileName)))
                            .start(SettingsHomeClickProfileActivity.this);
                }
                break;
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    String destinationFileName = "crop";
                    UCrop.of(data.getData(), Uri.fromFile(new File(getCacheDir(), destinationFileName)))
                            .withMaxResultSize(500, 500)
                            .start(SettingsHomeClickProfileActivity.this);
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    final Uri resultUri = UCrop.getOutput(data);
                    try {
                        final InputStream imageStream = getContentResolver().openInputStream(resultUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        UpdateUseImagerApiAsyncTask updateUseImagerApiAsyncTask = new UpdateUseImagerApiAsyncTask(SettingsHomeClickProfileActivity.this,
                                selectedImage, new StringResultCallback() {
                            @Override
                            public void processFinish(String result, KProgressHUD progress) {
                                user_image.setImageBitmap(selectedImage);

                            }
                        });
                        updateUseImagerApiAsyncTask.execute();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
