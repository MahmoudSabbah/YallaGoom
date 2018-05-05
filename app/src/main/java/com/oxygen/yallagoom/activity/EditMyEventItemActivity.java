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
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.yalantis.ucrop.UCrop;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.api.UpdateEventAsyncTask;
import com.oxygen.yallagoom.entity.Event;
import com.oxygen.yallagoom.interfaces.ClickPopUpCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CameraGalleryChoicePopup;
import com.oxygen.yallagoom.widget.compactcalendarview.CompactCalendarView;
import com.oxygen.yallagoom.widget.segmented.SegmentedGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class EditMyEventItemActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private LinearLayout parent;
    private TextView left_text;
    private TextView right_text;
    private TextView header_title;
    private Bundle bundle;
    private Event.DataEvent eventInformation;
    private TextView title;
    private EditText add_description;
    private CompactCalendarView compactcalendar_view_start;
    private TextView date_value;
    private DateFormat dateFormat;
    private TextView date_value__end;
    private CompactCalendarView compactcalendar_end;
    private RelativeLayout location_name;
    private TextView name_location;
    private double lat;
    private double lng;
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
    private String startTimeValue;
    private String endTimeValue = "";
    private Date startDate;
    private Date endDate;
    private RoundedImageView event_image;
    private RelativeLayout my_event_image;
    private static final int RESULT_LOAD_IMAGE = 205;
    private static final int REQUEST_IMAGE_CAPTURE = 204;
    private CameraGalleryChoicePopup photoPopup;
    private Bitmap selectedImage=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_edit_event);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(EditMyEventItemActivity.this);
        ToolUtils.setLightStatusBar(parent, EditMyEventItemActivity.this);
        bundle = getIntent().getExtras();
        dateFormat = new DateFormat();

        eventInformation = (Event.DataEvent) bundle.getSerializable("eventInformation");
        left_text = (TextView) findViewById(R.id.left_text);
        right_text = (TextView) findViewById(R.id.right_text);
        header_title = (TextView) findViewById(R.id.name_header);
        header_title.setText(eventInformation.getEventTitle());
        right_text.setText(getString(R.string.ok));
        my_event_image = (RelativeLayout) findViewById(R.id.my_event_image);
        event_image = (RoundedImageView) findViewById(R.id.event_image);
        title = (TextView) findViewById(R.id.title);
        add_description = (EditText) findViewById(R.id.add_description);
        compactcalendar_view_start = (CompactCalendarView) findViewById(R.id.compactcalendar_view_start);
        compactcalendar_end = (CompactCalendarView) findViewById(R.id.compactcalendar_end);
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

        title.setText(eventInformation.getEventTitle());
        if (eventInformation.getEventDescription() != null) {
            add_description.setText(eventInformation.getEventDescription());
        }
        if (ToolUtils.converStringToDate(eventInformation.getStartEventDate(), Constant.yyyy_MM_dd) != null) {
            startDate = ToolUtils.converStringToDate(eventInformation.getStartEventDate(), Constant.yyyy_MM_dd);
            compactcalendar_view_start.setCurrentDate(ToolUtils.converStringToDate(eventInformation.getStartEventDate(), Constant.yyyy_MM_dd));
        }
        if (eventInformation.getEndEventDate() != null) {
            endDate = ToolUtils.converStringToDate(eventInformation.getEndEventDate(), Constant.yyyy_MM_dd);
            compactcalendar_end.setCurrentDate(ToolUtils.converStringToDate(eventInformation.getEndEventDate(), Constant.yyyy_MM_dd));
        } else {
            compactcalendar_end.setCurrentDate(new Date());
        }
        if (eventInformation.getStartEventDate() != null) {
            date_value.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, ToolUtils.converStringToDate(eventInformation.getStartEventDate(), "yyyy-MM-dd")) + "");

        }
        if (eventInformation.getEndEventDate() != null) {
            date_value__end.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, ToolUtils.converStringToDate(eventInformation.getEndEventDate(), "yyyy-MM-dd")) + "");

        } else {
            date_value__end.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, new Date()) + "");

        }
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
        if (eventInformation.getEventLat() != null && eventInformation.getEventLong() != null) {
            lng = Double.parseDouble(eventInformation.getEventLong());
            lat = Double.parseDouble(eventInformation.getEventLat());

        } else {
            lat = -1;
            lng = -1;
        }
        location_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditMyEventItemActivity.this, ChooseLocationActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("check", false);
                startActivityForResult(intent, 202);

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

        start_time.setText(ToolUtils.convert24TimeTo12(eventInformation.getStartEventTime()));
        Log.e("converStringToDate", "" + ToolUtils.converStringToDate(eventInformation.getStartEventTime(), Constant.yyyy_MM_dd));
        startTimeValue = eventInformation.getStartEventTime();
        if (eventInformation.getEndEventTime()!=null){
            endTimeValue=eventInformation.getEndEventTime();
        }
        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog
                        = TimePickerDialog.newInstance(
                        EditMyEventItemActivity.this,
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
                        EditMyEventItemActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        true
                );
                timePickerDialog.show(getFragmentManager(), "End");
            }
        });
        if (eventInformation.getEndEventTime() != null) {
            end_time.setText(ToolUtils.convert24TimeTo12(eventInformation.getEndEventTime()));

        } else {
            end_time.setText("   -   ");
        }
        cat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditMyEventItemActivity.this, SearchCategoryActivity.class);
                intent.putExtra("cat_id", categoryId);
                startActivityForResult(intent, 203);
            }
        });
        categoryId = eventInformation.getCategory().getId();
        cat_name.setText(eventInformation.getCategory().getCategoryName());
        if (eventInformation.getIsFree().equalsIgnoreCase("0")) {
            free.setChecked(true);
            cost_edit.setVisibility(View.GONE);
        } else {
            paid.setChecked(true);
            cost_edit.setText(eventInformation.getCost());
        }
        if (eventInformation.getPrivateOrPublic().equalsIgnoreCase("public")) {
            public_.setChecked(true);
        } else {
            private_.setChecked(true);
        }
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
            public String checkstatus;
            public String valueOfEndDate;

            @Override
            public void onClick(View view) {
                if (endDate != null) {
                    valueOfEndDate = dateFormat.format(Constant.yyyy_MM_dd, endDate) + "";

                } else {
                    valueOfEndDate = "";
                }
              //  Bitmap event_imageBitmap = ((BitmapDrawable) event_image.getDrawable()).getBitmap();
                int radioButtonID = segmented_cost.getCheckedRadioButtonId();
                View radioButton = segmented_cost.findViewById(radioButtonID);
                int indexCheckPaid = segmented_cost.indexOfChild(radioButton);
                int radioButtonIDPublic_private_seg = public_private_seg.getCheckedRadioButtonId();
                View radioButtonPublic_private_seg = public_private_seg.findViewById(radioButtonIDPublic_private_seg);
                int indexCheckPrivate_public = public_private_seg.indexOfChild(radioButtonPublic_private_seg);

                if (!title.getText().toString().equalsIgnoreCase("")) {
                    if (indexCheckPaid == 1 && cost_edit.getText().toString().equalsIgnoreCase("")) {
                        ToolUtils.showSnak(EditMyEventItemActivity.this, getString(R.string.add_cost));
                    } else {
                        UpdateEventAsyncTask updateEventAsyncTask = new UpdateEventAsyncTask(EditMyEventItemActivity.this,
                                eventInformation.getId(), categoryId, title.getText().toString(), dateFormat.format(Constant.yyyy_MM_dd, startDate) + "",
                                valueOfEndDate, startTimeValue, endTimeValue,null,
                                add_description.getText().toString(), indexCheckPaid, cost_edit.getText().toString(), "", "",
                                indexCheckPrivate_public, "", "", "", "", "", "",
                                lat, lng);
                        updateEventAsyncTask.execute();
                    }
                } else {
                    ToolUtils.showSnak(EditMyEventItemActivity.this, getString(R.string.please_enter_even_title));
                }
            }
        });
        my_event_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPopup = new CameraGalleryChoicePopup(EditMyEventItemActivity.this, getString(R.string.camera_gallery_choose), new ClickPopUpCallback() {
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
                photoPopup.showAtLocation(((Activity) EditMyEventItemActivity.this).findViewById(R.id.parent),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 202:
                if (resultCode == 102) {
                    lat = data.getExtras().getDouble("lat");
                    lng = data.getExtras().getDouble("lng");
                    if (data.getExtras().getString("address").equalsIgnoreCase("")) {
                        name_location.setText(lat + " , " + lng);

                    } else {
                        name_location.setText(data.getExtras().getString("address"));
                    }

                }
                break;
            case 203:
                if (resultCode == 102) {
                    categoryId = data.getExtras().getInt("cat_id");
                    cat_name.setText(data.getExtras().getString("cat_name"));

                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    String destinationFileName = "crop";
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    UCrop.of(ToolUtils.getImageUri(EditMyEventItemActivity.this, imageBitmap)
                            , Uri.fromFile(new File(getCacheDir(), destinationFileName)))
                            .start(EditMyEventItemActivity.this);
                }
                break;
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    String destinationFileName = "crop";
                    UCrop.of(data.getData(), Uri.fromFile(new File(getCacheDir(), destinationFileName)))
                            .withMaxResultSize(500, 500)
                            .start(EditMyEventItemActivity.this);
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

            String[] st_time = startTimeValue.split(":");
            String[] en_time = (dateFormat.format(Constant.HH_mm_ss, date) + "").split(":");
            double finishStartTime = Double.parseDouble(st_time[0]) + (Double.parseDouble(st_time[1]) / 60);
            double finishEndTime = Double.parseDouble(en_time[0]) + (Double.parseDouble(en_time[1]) / 60);
            if (startTimeValue != null && finishStartTime<= finishEndTime) {
                end_time.setText(dateFormat.format(Constant.hh_mm_aa, date));
                endTimeValue = dateFormat.format(Constant.HH_mm_ss, date) + "";
            } else {
                ToolUtils.showSnak(EditMyEventItemActivity.this, getString(R.string.time_check));

            }

        }
    }
}
