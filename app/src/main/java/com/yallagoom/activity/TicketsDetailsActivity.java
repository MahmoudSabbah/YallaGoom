package com.yallagoom.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateFormat;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewReviewList;
import com.yallagoom.api.BookingsettingsAsyncTask;
import com.yallagoom.controller.RealmController;
import com.yallagoom.entity.TicketClasses.TicketDetails;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.MapUtils;
import com.yallagoom.utils.ToolUtils;

import io.realm.Realm;

public class TicketsDetailsActivity extends AppCompatActivity {

    private ProgressBar progress1;
    private ProgressBar progress2;
    private ProgressBar progress3;
    private ProgressBar progress4;
    private LinearLayout parent;
    private TextView name_header;
    private TicketDetails ticketDetails;
    private ImageView ticket_image;
    private ImageLoader imageLoader;
    private TextView country_name;
    private TextView ticket_title;
    private TextView date;
    private DateFormat dateFormat;
    private TextView time;
    private TextView price;
    private TextView description;
    private RecyclerView list_review;
    private ImageView static_map;
    private RelativeLayout go_to_map;
    private RelativeLayout more_details_layout;
    private RelativeLayout all_reviews_layout;
    private String mTicketsDetails;
    private RelativeLayout right_text;
    private TextView select_true;
    private TextView select_false;
    private RelativeLayout heart_layout;
    private Realm mRealm;
    private RealmController realmController;
    private RelativeLayout booking_bt1;
    private RelativeLayout booking_bt2;

  //  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_details);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(TicketsDetailsActivity.this);
        ToolUtils.setLightStatusBar(parent, TicketsDetailsActivity.this);
     /*   Transition fade = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setEnterTransition(fade);*/

        imageLoader = ImageLoader.getInstance();
        dateFormat = new DateFormat();
        mRealm = Realm.getDefaultInstance();
        realmController = new RealmController(mRealm);
       /* try {
            mRealm.close();
            Realm.deleteRealm(mRealm.getConfiguration());
        } catch (Exception e) {
        }
*/

        Bundle bundle = getIntent().getExtras();
        mTicketsDetails = bundle.getString("TicketsDetails");
        ticketDetails = new Gson().fromJson(mTicketsDetails, TicketDetails.class);

        right_text = (RelativeLayout) findViewById(R.id.right_text);
        name_header = (TextView) findViewById(R.id.name_header);
        name_header.setText(ticketDetails.getTicket_info().getTitle());
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "YallGoom");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        booking_bt1 = (RelativeLayout) findViewById(R.id.booking_bt1);
        booking_bt2 = (RelativeLayout) findViewById(R.id.booking_bt2);
        booking_bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingNow();
            }
        });
        booking_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingNow();
            }
        });

        heart_layout = (RelativeLayout) findViewById(R.id.heart_layout);
        select_true = (TextView) findViewById(R.id.select_true);
        select_false = (TextView) findViewById(R.id.select_false);
        if (realmController.checkTicketsExists(ticketDetails.getTicket_info().getId())) {
            select_true.setVisibility(View.VISIBLE);
            select_false.setVisibility(View.GONE);
        } else {
            select_true.setVisibility(View.GONE);
            select_false.setVisibility(View.VISIBLE);
        }
        /// realmController.clearAllTicketDetails();
        heart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("getTicketDetailsCkeck", "" + realmController.checkTicketsExists(ticketDetails.getTicket_info().getId()));
                if (!realmController.checkTicketsExists(ticketDetails.getTicket_info().getId())) {
                    mRealm.beginTransaction();
                   // mRealm.copyToRealm(ticketDetails.getReview_list());
                    mRealm.copyToRealmOrUpdate(ticketDetails.getTicket_info());
                    mRealm.commitTransaction();
                } else {
                    mRealm.beginTransaction();
                    realmController.removeTickets(ticketDetails.getTicket_info().getId());
                    mRealm.commitTransaction();
                }

                if (select_true.getVisibility() == View.VISIBLE) {
                    select_true.setVisibility(View.GONE);
                    select_false.setVisibility(View.VISIBLE);

                } else {
                    select_true.setVisibility(View.VISIBLE);
                    select_false.setVisibility(View.GONE);

                }
            }
        });

        ticket_image = (ImageView) findViewById(R.id.ticket_image);
        if (ticketDetails.getTicket_info().getImg_url() != null)
            ToolUtils.setImage(Constant.imageUrl + ticketDetails.getTicket_info().getImg_url(), ticket_image, imageLoader);

        country_name = (TextView) findViewById(R.id.country_name);
        country_name.setText(ticketDetails.getTicket_info().getCountry().getName_en());

        ticket_title = (TextView) findViewById(R.id.ticket_title);
        ticket_title.setText(ticketDetails.getTicket_info().getTitle());

        date = (TextView) findViewById(R.id.date);
        if (ticketDetails.getTicket_info().getDate() != null)
            date.setText(dateFormat.format("EEEE, MMMM dd, yyyy", ToolUtils.converStringToDate(ticketDetails.getTicket_info().getDate(), Constant.yyyy_MM_dd)));

        time = (TextView) findViewById(R.id.time);
        if (ticketDetails.getTicket_info().getTime() != null)
            time.setText(ToolUtils.convert24TimeTo12(ticketDetails.getTicket_info().getTime()));

        price = (TextView) findViewById(R.id.price);
        price.setText(ticketDetails.getTicket_info().getPrice() + " $");

        description = (TextView) findViewById(R.id.description);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            description.setText(Html.fromHtml(ticketDetails.getTicket_info().getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            description.setText(Html.fromHtml(ticketDetails.getTicket_info().getDescription()));
        }

        list_review = (RecyclerView) findViewById(R.id.list_review);
        list_review.setNestedScrollingEnabled(false);

        RecycleViewReviewList recycleViewReviewList = new RecycleViewReviewList(ticketDetails.getReview_list().getData());
        list_review.setAdapter(recycleViewReviewList);

        static_map = (ImageView) findViewById(R.id.static_map);
        ToolUtils.setImage(MapUtils.staticPathMap(TicketsDetailsActivity.this, ticketDetails.getTicket_info().getLat_pos() + "," +
                ticketDetails.getTicket_info().getLong_pos()), static_map, imageLoader);

        more_details_layout = (RelativeLayout) findViewById(R.id.more_details_layout);
        all_reviews_layout = (RelativeLayout) findViewById(R.id.all_reviews_layout);
        go_to_map = (RelativeLayout) findViewById(R.id.go_to_map);
        more_details_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketsDetailsActivity.this, TicketsdetailsMoreActivity.class);
                intent.putExtra("lat", ticketDetails.getTicket_info().getLat_pos());
                intent.putExtra("lng", ticketDetails.getTicket_info().getLong_pos());
                intent.putExtra("ticketDetails", mTicketsDetails);
                intent.putExtra("flag", 0);
                startActivity(intent);
            }
        });
        all_reviews_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketsDetailsActivity.this, TicketsdetailsMoreActivity.class);
                intent.putExtra("lat", ticketDetails.getTicket_info().getLat_pos());
                intent.putExtra("lng", ticketDetails.getTicket_info().getLong_pos());
                intent.putExtra("ticketDetails", mTicketsDetails);
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
        go_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketsDetailsActivity.this, TicketsdetailsMoreActivity.class);
                intent.putExtra("lat", ticketDetails.getTicket_info().getLat_pos());
                intent.putExtra("lng", ticketDetails.getTicket_info().getLong_pos());
                intent.putExtra("ticketDetails", mTicketsDetails);
                intent.putExtra("flag", 2);
                startActivity(intent);
            }
        });

        progress1 = (ProgressBar) findViewById(R.id.progress1);
        progress2 = (ProgressBar) findViewById(R.id.progress2);
        progress3 = (ProgressBar) findViewById(R.id.progress3);
        progress4 = (ProgressBar) findViewById(R.id.progress4);
        progress1.setProgress(70);
        progress2.setProgress(50);
        progress3.setProgress(30);
        progress4.setProgress(10);
    }
    private void BookingNow() {
        BookingsettingsAsyncTask bookingsettingsAsyncTask=new BookingsettingsAsyncTask(TicketsDetailsActivity.this);
        bookingsettingsAsyncTask.execute(ticketDetails.getTicket_info().getId()+"");
    }
    public void Back(View view) {
        finish();
    }
}
