package com.oxygen.yallagoom.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewWhoGoing;
import com.oxygen.yallagoom.api.DeleteEventAsyncTask;
import com.oxygen.yallagoom.entity.Event;
import com.oxygen.yallagoom.entity.InvitationRecord;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CameraGalleryChoicePopup;

import java.util.ArrayList;
import java.util.HashMap;

public class MyEventListClickActivity extends AppCompatActivity {

    private LinearLayout parent;
    private RecyclerView whw_going_list;
    private Bundle bundle;
    private TextView name_header;
    private TextView edit;
    private ScrollView scrollView;
    private RoundedImageView my_event_image;
    private ImageLoader imageLoader;
    private TextView title;
    private TextView description;
    private TextView date_text;
    private RelativeLayout location_layout;
    private TextView location_name_label;
    private TextView second_location;
    private TextView start_time;
    private TextView end_time;
    private Event.DataEvent eventData;
    private TextView delete;
    private DateFormat dateFormat;
    private TextView sat_value;
    private TextView sun_value;
    private TextView mon_value;
    private TextView tue_value;
    private TextView thu_value;
    private TextView wen_value;
    private TextView fri_value;
    private CameraGalleryChoicePopup photoPopup;
    private static final int RESULT_LOAD_IMAGE = 202;
    private static final int REQUEST_IMAGE_CAPTURE = 204;
    private LinearLayout list_of_status;
    private HashMap<String, ArrayList<InvitationRecord>> invitedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_main_click);
        imageLoader = ImageLoader.getInstance();
        dateFormat = new DateFormat();
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(MyEventListClickActivity.this);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        edit = (TextView) findViewById(R.id.right_text);
        ToolUtils.setLightStatusBar(parent, MyEventListClickActivity.this);
        bundle = getIntent().getExtras();
        eventData = (Event.DataEvent) bundle.getSerializable("event_data");
        location_layout = (RelativeLayout) findViewById(R.id.location_layout);
        my_event_image = (RoundedImageView) findViewById(R.id.my_event_image);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        date_text = (TextView) findViewById(R.id.date_text);
        location_name_label = (TextView) findViewById(R.id.location_name_label);
        second_location = (TextView) findViewById(R.id.second_location);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);

        delete = (TextView) findViewById(R.id.delete);
        name_header = (TextView) findViewById(R.id.name_header);
        name_header.setText(eventData.getEventTitle());
        list_of_status = (LinearLayout) findViewById(R.id.list_of_status);
        if (getIntent().hasExtra("Invited_list")) {
            ArrayList<InvitationRecord> Invited_list = (ArrayList<InvitationRecord>) bundle.getSerializable("Invited_list");
            invitedList = new HashMap<>();
            for (int i = 0; i < Invited_list.size(); i++) {
                switch (Invited_list.get(i).getInvitation_status()) {
                    case "going":
                        if (invitedList.containsKey("going")) {
                            invitedList.get("going").add(Invited_list.get(i));
                        } else {
                            ArrayList<InvitationRecord> invitationRecords = new ArrayList<>();
                            invitationRecords.add(Invited_list.get(i));
                            invitedList.put("going", invitationRecords);
                        }
                        break;
                    case "maybe":
                        if (invitedList.containsKey("maybe")) {
                            invitedList.get("maybe").add(Invited_list.get(i));
                        } else {
                            ArrayList<InvitationRecord> invitationRecords = new ArrayList<>();
                            invitationRecords.add(Invited_list.get(i));
                            invitedList.put("maybe", invitationRecords);
                        }
                        break;
                    case "not interested":
                        if (invitedList.containsKey("not interested")) {
                            invitedList.get("not interested").add(Invited_list.get(i));
                        } else {
                            ArrayList<InvitationRecord> invitationRecords = new ArrayList<>();
                            invitationRecords.add(Invited_list.get(i));
                            invitedList.put("not interested", invitationRecords);
                        }
                        break;
                }
            }
            for (int j = 0; j < invitedList.size(); j++) {
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View inflatedLayout = inflater.inflate(R.layout.who_respond_invitation, null, false);
                RecyclerView who_going_list_data = (RecyclerView) inflatedLayout.findViewById(R.id.who_going_list);
                TextView title = (TextView) inflatedLayout.findViewById(R.id.title);
                RecycleViewWhoGoing recycleViewWhoGoing = new RecycleViewWhoGoing(invitedList.keySet().toArray()[j] + "", invitedList.get(invitedList.keySet().toArray()[j]));
                who_going_list_data.setAdapter(recycleViewWhoGoing);
                switch (invitedList.keySet().toArray()[j] + "") {
                    case "going":
                        title.setText(getString(R.string.respond_going));
                        break;
                    case "maybe":
                        title.setText(getString(R.string.respond_maybe));
                        break;
                    case "not interested":
                        title.setText(getString(R.string.respond_not_interested));
                        break;
                }
                list_of_status.addView(inflatedLayout);
            }
          /*  ToolUtils.viewToast(getApplicationContext(),""+invitedList.size());
            Log.e("invitedList",""+invitedList.size());*/
        }

      /*  RecycleViewWhoGoing recycleViewWhoGoing = new RecycleViewWhoGoing(invitedList.keySet().toArray()[j], invitedList.get(invitedList.keySet().toArray()[j]));
        whw_going_list.setAdapter(recycleViewWhoGoing);*/
        // ToolUtils.next7Days();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyEventListClickActivity.this, EditMyEventItemActivity.class);
                intent.putExtra("eventInformation", eventData);
                startActivityForResult(intent, 203);
            }
        });
        sat_value = (TextView) findViewById(R.id.sat_value);
        sun_value = (TextView) findViewById(R.id.sun_value);
        mon_value = (TextView) findViewById(R.id.mon_value);
        tue_value = (TextView) findViewById(R.id.tue_value);
        wen_value = (TextView) findViewById(R.id.wen_value);
        thu_value = (TextView) findViewById(R.id.thu_value);
        fri_value = (TextView) findViewById(R.id.fri_value);

        getData();
    }

    public void Back(View view) {
        finish();
    }

    private void getData() {
        if (eventData.getEventImage() != null) {
            imageLoader.loadImage(Constant.imageUrl + "" + eventData.getEventImage(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    my_event_image.setImageBitmap(loadedImage);
                }
            });
        }
        title.setText(eventData.getEventTitle());
        description.setText(eventData.getEventDescription());
        date_text.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, ToolUtils.converStringToDate(eventData.getStartEventDate(), "yyyy-MM-dd")) + "");
        ToolUtils.getCalenderView(ToolUtils.converStringToDate(eventData.getStartEventDate(), Constant.yyyy_MM_dd),
                sat_value,sun_value,mon_value,tue_value,wen_value,thu_value,fri_value);
       // date_text.setText(eventData.getStartEventDate());
        start_time.setText(ToolUtils.convert24TimeTo12(eventData.getStartEventTime()));
        if (eventData.getEndEventTime() != null) {
            end_time.setText(ToolUtils.convert24TimeTo12(eventData.getEndEventTime()));

        } else {
            end_time.setVisibility(View.INVISIBLE);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteEventAsyncTask deleteEventAsyncTask=new DeleteEventAsyncTask(MyEventListClickActivity.this);
                deleteEventAsyncTask.execute(eventData.getId()+"");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 203:
                if (resultCode == 102) {
                    Bundle bundle=data.getExtras();
                    Event.DataEvent updateData = (Event.DataEvent) bundle.getSerializable("UpdateEvent");
                 //   eventData=updateData;
                    ToolUtils.getCalenderView(ToolUtils.converStringToDate(updateData.getStartEventDate(), Constant.yyyy_MM_dd),
                            sat_value,sun_value,mon_value,tue_value,wen_value,thu_value,fri_value);
                    if (updateData.getEventImage() != null) {
                        imageLoader.loadImage(Constant.urlImage + "" + updateData.getEventImage(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                my_event_image.setImageBitmap(loadedImage);
                            }
                        });
                    }
                    title.setText(updateData.getEventTitle());
                    description.setText(updateData.getEventDescription());
                    date_text.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, ToolUtils.converStringToDate(updateData.getStartEventDate(), "yyyy-MM-dd")) + "");

                    //     date_text.setText(updateData.getStartEventDate());
                    start_time.setText(ToolUtils.convert24TimeTo12(updateData.getStartEventTime()));
                    if (updateData.getEndEventTime() != null) {
                        end_time.setText(ToolUtils.convert24TimeTo12(updateData.getEndEventTime()));

                    } else {
                        end_time.setVisibility(View.INVISIBLE);
                    }
                }
                break;
        }
    }
}
