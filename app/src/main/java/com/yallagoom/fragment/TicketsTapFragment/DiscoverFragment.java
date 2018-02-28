package com.yallagoom.fragment.TicketsTapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.activity.SearchCountryActivity;
import com.yallagoom.adapter.RecycleViewDiscoverByCategory;
import com.yallagoom.adapter.RecycleViewOffers;
import com.yallagoom.adapter.RecycleViewRecommendations;
import com.yallagoom.api.DiscoverAsyncTask;
import com.yallagoom.api.GetCountryCodeAsyncTask;
import com.yallagoom.entity.Discover;
import com.yallagoom.interfaces.DiscoverCallback;
import com.yallagoom.interfaces.GetCountryCodeCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import java.util.Date;
import java.util.Locale;


public class DiscoverFragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private RecyclerView recommendations_list;
    private RecyclerView discover_by_category_list;
    private RecyclerView special_offers_list;
    private ImageView country_image;
    private ImageLoader imageLoader;
    private TextView country_name;
    private TextView date_today;
    private DateFormat dateFormat;
    private RecycleViewRecommendations recycleViewRecommendations;
    private RecycleViewDiscoverByCategory recycleViewDiscoverByCategory;
    private RecycleViewOffers recycleViewOffers;
    private RelativeLayout discover_other_country;
    private int country_id;
    private String code_3;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        imageLoader = ImageLoader.getInstance();
        dateFormat = new DateFormat();

        discover_other_country = (RelativeLayout) view.findViewById(R.id.discover_other_country);
        country_image = (ImageView) view.findViewById(R.id.country_image);
        country_name = (TextView) view.findViewById(R.id.country_name);
        date_today = (TextView) view.findViewById(R.id.date_today);
        recommendations_list = (RecyclerView) view.findViewById(R.id.recommendations_list);
        recommendations_list.setNestedScrollingEnabled(false);

        discover_by_category_list = (RecyclerView) view.findViewById(R.id.discover_by_category_list);
        discover_by_category_list.setNestedScrollingEnabled(false);
        special_offers_list = (RecyclerView) view.findViewById(R.id.special_offers_list);
        special_offers_list.setNestedScrollingEnabled(false);
        getDataCode();
        discover_other_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiscoverFragment.this.getContext(), SearchCountryActivity.class);

                startActivityForResult(intent, 104);
            }
        });
        return view;

    }

    private void getDataCode() {
        GetCountryCodeAsyncTask getCountryCodeAsyncTask = new GetCountryCodeAsyncTask(DiscoverFragment.this.getActivity(), new GetCountryCodeCallback() {
            @Override
            public void processFinish(String code, KProgressHUD progress) {
                String alpha3Country = new Locale("en", code).getISO3Country();
                Constant.alpha3Country=alpha3Country;
                Log.e("alpha3Country", "" + alpha3Country);
                getDiscover(alpha3Country, progress);
            }
        });
        getCountryCodeAsyncTask.execute();

     /*   */
    }

    private void getDiscover(String code_3, KProgressHUD progress) {
        DiscoverAsyncTask discoverAsyncTask = new DiscoverAsyncTask(DiscoverFragment.this.getActivity(), progress, new DiscoverCallback() {
            @Override
            public void processFinish(Discover discover) {
                ToolUtils.setImage(Constant.imageUrl + discover.getCountry_data().getImg_url(), country_image, imageLoader);
                country_name.setText(discover.getCountry_data().getName_en());
                date_today.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, new Date()));
                recycleViewRecommendations = new RecycleViewRecommendations(discover.getRecommended().getData());
                recommendations_list.setAdapter(recycleViewRecommendations);
                recycleViewDiscoverByCategory = new RecycleViewDiscoverByCategory(discover.getCategory_list(),discover.getCountry_data());
                discover_by_category_list.setAdapter(recycleViewDiscoverByCategory);
                recycleViewOffers = new RecycleViewOffers(discover.getOffers().getData());
                special_offers_list.setAdapter(recycleViewOffers);
            }
        });
        discoverAsyncTask.execute(code_3.toUpperCase());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 104:
                if (resultCode == 102) {
                    country_id = data.getExtras().getInt("country_id");
                    code_3 = data.getExtras().getString("code_3");
                    getDiscover(code_3, null);
                }
                break;

        }
    }
}
