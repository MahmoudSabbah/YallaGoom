package com.oxygen.yallagoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.oxygen.yallagoom.interfaces.ClickPopUpCallback;
import com.oxygen.yallagoom.widget.CameraGalleryChoicePopup;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.api.NewEventAsyncTask;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.compactcalendarview.CompactCalendarView;
import com.oxygen.yallagoom.widget.segmented.SegmentedGroup;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vn.tungdx.mediapicker.MediaItem;
import vn.tungdx.mediapicker.MediaOptions;
import vn.tungdx.mediapicker.activities.MediaPickerActivity;

public class HomeCreateNewEventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final int REQUEST_IMAGE_CAPTURE = 222;
    private static final int RESULT_LOAD_IMAGE = 223;
    private static final int GET_CATEGORY_REQUEST = 204;
    private static final int GET_LOCATION_REQUEST=205;

    private LinearLayout parent;
    private TextView left_text;
    private TextView right_text;
    private TextView header_title;
    private TextView title;
    private EditText add_description;
    private CompactCalendarView compactcalendar_view_start;
    private TextView date_value;
    private DateFormat dateFormat;
    private TextView date_value__end;
    private CompactCalendarView compactcalendar_end;
    private RelativeLayout location_name;
    private TextView name_location;
    private double lat = -1;
    private double lng = -1;
    private TextView previous;
    private TextView next;
    private TextView previous_end;
    private TextView next_end;
    private TextView start_time;
    private TextView end_time;
    private RelativeLayout cat_layout;
    private TextView cat_name;
    private int categoryId = -1;
    private SegmentedGroup segmented_cost;
    private RadioButton free;
    private RadioButton paid;
    private EditText cost_edit;
    private SegmentedGroup public_private_seg;
    private RadioButton private_;
    private RadioButton public_;
    private String startTimeValue = "";
    private String endTimeValue = "";
    private Date startDate;
    private Date endDate;
    private RoundedImageView event_image;
    private boolean checkLocation = true;
    private EditText organizer_description;
    private EditText organizer_name;
    private RelativeLayout my_event_image;
    private CameraGalleryChoicePopup photoPopup;
    private String TAG="HomeCreateNewEventActivity";
    private Bitmap selectedImage=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_create_new_event);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(HomeCreateNewEventActivity.this);
        ToolUtils.setLightStatusBar(parent, HomeCreateNewEventActivity.this);
        left_text = (TextView) findViewById(R.id.left_text);
        right_text = (TextView) findViewById(R.id.right_text);
        header_title = (TextView) findViewById(R.id.name_header);
        header_title.setText(R.string.create_new_event);
        right_text.setText(getString(R.string.save));
        left_text.setText(getString(R.string.cancel));


        event_image = (RoundedImageView) findViewById(R.id.event_image);
        title = (TextView) findViewById(R.id.title);
        add_description = (EditText) findViewById(R.id.add_description);
        organizer_description = (EditText) findViewById(R.id.organizer_description);
        organizer_name = (EditText) findViewById(R.id.organizer_name);
        compactcalendar_view_start = (CompactCalendarView) findViewById(R.id.compactcalendar_view_start);
        compactcalendar_end = (CompactCalendarView) findViewById(R.id.compactcalendar_end);
        my_event_image = (RelativeLayout) findViewById(R.id.my_event_image);
        location_name = (RelativeLayout) findViewById(R.id.location_name);
        name_location = (TextView) findViewById(R.id.name_location);

        date_value = (TextView) findViewById(R.id.date_value);
        date_value__end = (TextView) findViewById(R.id.date_value__end);
        previous = (TextView) findViewById(R.id.previous);
        next = (TextView) findViewById(R.id.next);
        previous_end = (TextView) findViewById(R.id.previous_end);
        next_end = (TextView) findViewById(R.id.next_end);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        cat_layout = (RelativeLayout) findViewById(R.id.cat_layout);
        cat_name = (TextView) findViewById(R.id.cat_name);
        segmented_cost = (SegmentedGroup) findViewById(R.id.segmented_cost);
        public_private_seg = (SegmentedGroup) findViewById(R.id.public_private_seg);
        free = (RadioButton) findViewById(R.id.free);
        paid = (RadioButton) findViewById(R.id.paid);
        private_ = (RadioButton) findViewById(R.id.private_);
        public_ = (RadioButton) findViewById(R.id.public_);

        cost_edit = (EditText) findViewById(R.id.cost_edit);
        startDate = new Date();
        endDate = new Date();
        date_value.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, startDate) + "");
        date_value__end.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, endDate) + "");

        compactcalendar_view_start.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                startDate = dateClicked;
                date_value.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, dateClicked) + "");

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });
        compactcalendar_end.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                endDate = dateClicked;
                date_value__end.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, dateClicked) + "");

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });

        location_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCreateNewEventActivity.this, ChooseLocationActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("check", checkLocation);
                startActivityForResult(intent, GET_LOCATION_REQUEST);

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactcalendar_view_start.showPreviousMonth();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactcalendar_view_start.showNextMonth();
            }
        });
        previous_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactcalendar_end.showPreviousMonth();
            }
        });
        next_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactcalendar_end.showNextMonth();
            }
        });


        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog
                        = TimePickerDialog.newInstance(
                        HomeCreateNewEventActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        true
                );
                timePickerDialog.show(getFragmentManager(), "Start");
            }
        });
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog
                        = TimePickerDialog.newInstance(
                        HomeCreateNewEventActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        true
                );
                timePickerDialog.show(getFragmentManager(), "End");
            }
        });
        end_time.setText("   -   ");
        start_time.setText("   -   ");
        cat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCreateNewEventActivity.this, SearchCategoryActivity.class);
                startActivityForResult(intent, GET_CATEGORY_REQUEST);
            }
        });

        segmented_cost.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonID = segmented_cost.getCheckedRadioButtonId();
                View radioButton = segmented_cost.findViewById(radioButtonID);
                int idx = segmented_cost.indexOfChild(radioButton);
                if (idx == 0) {
                    cost_edit.setVisibility(View.GONE);
                } else {
                    cost_edit.setVisibility(View.VISIBLE);
                }
            }
        });


        right_text.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                newEvent();
            }
        });
        my_event_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPopup = new CameraGalleryChoicePopup(HomeCreateNewEventActivity.this, getString(R.string.camera_gallery_choose), new ClickPopUpCallback() {
                    @Override
                    public void processFinish(final int check) {
                        photoPopup.dismiss();
                        if (check == 0) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            }
                        } else {
                            MediaOptions options = MediaOptions.createDefault();
                            MediaPickerActivity.open(HomeCreateNewEventActivity.this, RESULT_LOAD_IMAGE, options);

                        }
                    }
                });
                photoPopup.showAtLocation(((Activity) HomeCreateNewEventActivity.this).findViewById(R.id.parent),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }

    public void Back(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_LOCATION_REQUEST:
                if (resultCode == 102) {
                    lat = data.getExtras().getDouble("lat");
                    lng = data.getExtras().getDouble("lng");
                    checkLocation = false;
                    if (data.getExtras().getString("address").equalsIgnoreCase("")) {
                        name_location.setText(lat + " , " + lng);

                    } else {
                        name_location.setText(data.getExtras().getString("address"));
                    }

                }
                break;
            case GET_CATEGORY_REQUEST:
                if (resultCode == 102) {
                    categoryId = data.getExtras().getInt("cat_id");
                    cat_name.setText(data.getExtras().getString("cat_name"));

                }
                break;
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    String destinationFileName = "crop";
                    ArrayList<MediaItem> mMediaSelectedList = MediaPickerActivity
                            .getMediaItemSelected(data);
                    Log.e("mMediaSelectedList",""+mMediaSelectedList.size());
                    if (mMediaSelectedList != null) {
                        for (MediaItem mediaItem : mMediaSelectedList) {
                            UCrop.of(mediaItem.getUriOrigin()
                                    , Uri.fromFile(new File(getCacheDir(), destinationFileName)))
                                    .start(HomeCreateNewEventActivity.this);
                        }
                    } else {
                        Log.e(TAG, "Error to get media, NULL");
                    }
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    String destinationFileName = "crop";
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    UCrop.of(ToolUtils.getImageUri(HomeCreateNewEventActivity.this, imageBitmap)
                            , Uri.fromFile(new File(getCacheDir(), destinationFileName)))
                            .start(HomeCreateNewEventActivity.this);
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    final Uri resultUri = UCrop.getOutput(data);
                    try {
                        final InputStream imageStream = getContentResolver().openInputStream(resultUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);

                        event_image.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        Date date = cal.getTime();
        DateFormat dateFormat = new DateFormat();
        if (view.getTag().equalsIgnoreCase("Start")) {
            startTimeValue = dateFormat.format(Constant.HH_mm_ss, date) + "";
            start_time.setText(dateFormat.format(Constant.hh_mm_aa, date));
        } else {
            if (startTimeValue.equalsIgnoreCase("")) {
                ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.enter_start_time));
            } else {
                String[] st_time = startTimeValue.split(":");
                String[] en_time = (dateFormat.format(Constant.HH_mm_ss, date) + "").split(":");
                double finishStartTime = Double.parseDouble(st_time[0]) + (Double.parseDouble(st_time[1]) / 60);
                double finishEndTime = Double.parseDouble(en_time[0]) + (Double.parseDouble(en_time[1]) / 60);
                if (startTimeValue != null && finishStartTime < finishEndTime) {
                    end_time.setText(dateFormat.format(Constant.hh_mm_aa, date));
                    endTimeValue = dateFormat.format(Constant.HH_mm_ss, date) + "";
                } else {
                    ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.time_check));

                }
            }
        }
    }

    private void newEvent() {
        int radioButtonID = segmented_cost.getCheckedRadioButtonId();
        View radioButton = segmented_cost.findViewById(radioButtonID);
        int idx = segmented_cost.indexOfChild(radioButton);

        int radioButtonID2 = public_private_seg.getCheckedRadioButtonId();
        View radioButton2 = public_private_seg.findViewById(radioButtonID2);
        int pub_pri = public_private_seg.indexOfChild(radioButton2);
        if (pub_pri == 0) {
            pub_pri = 1;
        } else if (pub_pri == 1) {
            pub_pri = 0;
        }
        if (title.getText().toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.please_enter_title));
        } else if (lat == -1) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.enter_event_location));
        } else if (startTimeValue.toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.select_start_time));
        } else if (categoryId == -1) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.select_cat));
        } else if (idx == -1) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.select_event_price));
        } else if (idx == 1 && cost_edit.getText().toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.select_cost));
        } else if (pub_pri == -1) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.select_event_type));
        } else {
            // Bitmap bitmap = ((BitmapDrawable) event_image.getDrawable()).getBitmap();

            NewEventAsyncTask newEventAsyncTask = new NewEventAsyncTask(HomeCreateNewEventActivity.this, categoryId,
                    title.getText().toString(), dateFormat.format(Constant.yyyy_MM_dd, startDate) + ""
                    , dateFormat.format(Constant.yyyy_MM_dd, endDate) + "", startTimeValue,
                    endTimeValue,
                    selectedImage, add_description.getText().toString(), idx, cost_edit.getText().toString(), organizer_name.getText().toString(),
                    organizer_description.getText().toString(), pub_pri, "", "", "", ""
                    , "", "", lat, lng, null);
            newEventAsyncTask.execute();

        }
    }

}
