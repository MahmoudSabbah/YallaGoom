package com.oxygen.yallagoom.fragment.ticketsTapFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.DiscoverCategoryActivity;
import com.oxygen.yallagoom.activity.SearchCountryActivity;
import com.oxygen.yallagoom.adapter.RecycleViewDiscoverByCategory;
import com.oxygen.yallagoom.adapter.RecycleViewOffers;
import com.oxygen.yallagoom.adapter.RecycleViewRecommendations;
import com.oxygen.yallagoom.api.ticket.DiscoverAsyncTask;
import com.oxygen.yallagoom.api.ticket.DiscoverSearchAsyncTask;
import com.oxygen.yallagoom.entity.CountryDetails;
import com.oxygen.yallagoom.entity.TicketClasses.Discover;
import com.oxygen.yallagoom.entity.TicketClasses.LikesContribution;
import com.oxygen.yallagoom.entity.TicketClasses.TicketInfo;
import com.oxygen.yallagoom.interfaces.DiscoverCallback;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class DiscoverFragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private RecyclerView recommendations_list;
    private RecyclerView discover_by_category_list;
  //  private RecyclerView special_offers_list;
  //  private ImageView country_image;
    private ImageLoader imageLoader;
    private TextView country_name;
  //  private TextView date_today;
    private DateFormat dateFormat;
    private RecycleViewRecommendations recycleViewRecommendations;
    private RecycleViewDiscoverByCategory recycleViewDiscoverByCategory;
   // private RecycleViewOffers recycleViewOffers;
    private RelativeLayout discover_other_country;
    private int country_id=-1;
    private String code_3;
    private RelativeLayout recommendations_for_you_lay;
   // private RelativeLayout special_offers_layout;
    private ArrayList<TicketInfo> recommendedList;
    private ArrayList<TicketInfo> offersList;
    private TextView search_home_icon;
    private RelativeLayout search_lay;
    private RelativeLayout search_view;
    private TextView close;
    private EditText search_edit;
    private TextView search_bt;
    private CountryDetails countryData;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        code_3=Constant.alpha3Country;
        imageLoader = ImageLoader.getInstance();
        dateFormat = new DateFormat();
        search_home_icon = (TextView) getActivity().findViewById(R.id.search_home_icon);
        search_lay = (RelativeLayout) view.findViewById(R.id.search_lay);
        search_view = (RelativeLayout) view.findViewById(R.id.search_view);
        search_edit = (EditText) view.findViewById(R.id.search_edit);
        close = (TextView) view.findViewById(R.id.close);
        search_bt = (TextView) view.findViewById(R.id.search_bt);

        discover_other_country = (RelativeLayout) view.findViewById(R.id.discover_other_country);
       // country_image = (ImageView) view.findViewById(R.id.country_image);
        country_name = (TextView) view.findViewById(R.id.country_name);
      //  date_today = (TextView) view.findViewById(R.id.date_today);
        recommendations_for_you_lay = (RelativeLayout) view.findViewById(R.id.recommendations_for_you_lay);
        recommendations_list = (RecyclerView) view.findViewById(R.id.recommendations_list);
       // recommendations_list.setNestedScrollingEnabled(false);

        discover_by_category_list = (RecyclerView) view.findViewById(R.id.discover_by_category_list);
        discover_by_category_list.setNestedScrollingEnabled(false);

      //  special_offers_layout = (RelativeLayout) view.findViewById(R.id.special_offers_layout);
      /*  special_offers_list = (RecyclerView) view.findViewById(R.id.special_offers_list);
        special_offers_list.setNestedScrollingEnabled(false);*/
        getDataCode();
        discover_other_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiscoverFragment.this.getContext(), SearchCountryActivity.class);
                intent.putExtra("id", country_id);
                startActivityForResult(intent, 104);
            }
        });
        search_home_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_view.setVisibility(View.VISIBLE);

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_view.setVisibility(View.GONE);
                search_edit.setText("");
            }
        });
        search_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_edit.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(search_edit, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscoverSearchAsyncTask discoverSearchAsyncTask=new DiscoverSearchAsyncTask(DiscoverFragment.this.getActivity(), new StringResultCallback() {
                    @Override
                    public void processFinish(String result, KProgressHUD progress) {
                        Intent intent = new Intent(DiscoverFragment.this.getActivity(), DiscoverCategoryActivity.class);
                        intent.putExtra("resultData",""+result);
                        intent.putExtra("countryDataEN",""+countryData.getName_en());
                        intent.putExtra("countryDataAR",""+countryData.getName_ar());
                        intent.putExtra("CategoryName",""+search_edit.getText().toString());
                        startActivityForResult(intent,102);
                    }
                });
                discoverSearchAsyncTask.execute(search_edit.getText().toString(),code_3.toUpperCase());
            }
        });
        return view;

    }

    private void getDataCode() {
        getDiscover(code_3, null);
      /*  GetCountryCodeAsyncTask getCountryCodeAsyncTask = new GetCountryCodeAsyncTask(DiscoverFragment.this.getActivity(), new GetCountryCodeCallback() {
            @Override
            public void processFinish(String code, KProgressHUD progress) {
                String alpha3Country = new Locale("en", code).getISO3Country();
                Constant.alpha3Country=alpha3Country;
                Log.e("alpha3Country", "" + alpha3Country);
                getDiscover(alpha3Country, progress);
            }
        });
        getCountryCodeAsyncTask.execute();*/

        /*   */
    }

    private void getDiscover(String code_3, KProgressHUD progress) {
        DiscoverAsyncTask discoverAsyncTask = new DiscoverAsyncTask(DiscoverFragment.this.getActivity(), progress, new DiscoverCallback() {
            @Override
            public void processFinish(Discover discover) {
                recommendedList = discover.getRecommended().getData();
                countryData=discover.getCountry_data();
                offersList = discover.getOffers().getData();
            //    ToolUtils.setImage(Constant.imageUrl + discover.getCountry_data().getImg_url(), country_image, imageLoader);
                country_name.setText(discover.getCountry_data().getName_en());
               // date_today.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, new Date()));
                recycleViewRecommendations = new RecycleViewRecommendations(recommendedList);
                recommendations_list.setAdapter(recycleViewRecommendations);
                if (discover.getRecommended().getData().size() == 0) {
                    recommendations_for_you_lay.setVisibility(View.GONE);
                }
              /*  if (discover.getOffers().getData().size() == 0) {
                    special_offers_layout.setVisibility(View.GONE);
                }*/
                recycleViewDiscoverByCategory = new RecycleViewDiscoverByCategory(discover.getCategory_list(), discover.getCountry_data());
                discover_by_category_list.setAdapter(recycleViewDiscoverByCategory);
        /*        recycleViewOffers = new RecycleViewOffers(offersList);
                special_offers_list.setAdapter(recycleViewOffers);*/
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
            case 102:
                if (resultCode == RESULT_OK) {
                    int ticket_id = data.getExtras().getInt("ticket_id");
                    String type = data.getExtras().getString("type");
                    Log.e("typetype",""+type);
                    if (type.equalsIgnoreCase("like")) {
                        for (int i = 0; i < recommendedList.size(); i++) {
                            if (recommendedList.get(i).getId() == ticket_id) {
                                LikesContribution likesContribution = new LikesContribution();
                                likesContribution.setTicket_id(ticket_id);
                                recommendedList.get(i).getMy_likes_contribution().add(likesContribution);
                                recommendedList.get(i).getTickets_likes_count().get(0).setLikes_count(recommendedList.get(i).getTickets_likes_count().get(0).getLikes_count() + 1);
                                recycleViewRecommendations.notifyDataSetChanged();
                            }
                        }
                      /*  for (int i = 0; i < offersList.size(); i++) {
                            if (offersList.get(i).getId() == ticket_id) {
                                LikesContribution likesContribution = new LikesContribution();
                                likesContribution.setTicket_id(ticket_id);
                                offersList.get(i).getMy_likes_contribution().add(likesContribution);
                                offersList.get(i).getTickets_likes_count().get(0).setLikes_count(offersList.get(i).getTickets_likes_count().get(0).getLikes_count() + 1);
                               recycleViewOffers.notifyDataSetChanged();
                            }
                        }*/
                    } else {
                        for (int i = 0; i < recommendedList.size(); i++) {
                            if (recommendedList.get(i).getId() == ticket_id) {
                                recommendedList.get(i).getMy_likes_contribution().clear();
                                recommendedList.get(i).getTickets_likes_count().get(0).setLikes_count(recommendedList.get(i).getTickets_likes_count().get(0).getLikes_count() - 1);
                                recycleViewRecommendations.notifyDataSetChanged();
                            }
                        }
                       /* for (int i = 0; i < offersList.size(); i++) {
                            if (offersList.get(i).getId() == ticket_id) {
                                offersList.get(i).getMy_likes_contribution().clear();
                                offersList.get(i).getTickets_likes_count().get(0).setLikes_count(offersList.get(i).getTickets_likes_count().get(0).getLikes_count() - 1);
                                recycleViewOffers.notifyDataSetChanged();
                            }
                        }*/
                    }
                }
                break;
        }
    }
}
