package com.oxygen.yallagoom.fragment.scoresTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewCompetitionsSettings;
import com.oxygen.yallagoom.adapter.RecycleViewMyFavourite;
import com.oxygen.yallagoom.api.GetCategoryLeagueApiAsyncTask;
import com.oxygen.yallagoom.entity.Matches.MatchCategory;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import com.oxygen.yallagoom.widget.segmented.SegmentedGroup;

import java.util.ArrayList;


public class CompetitionsSettingsFragment extends Fragment {


    private IndexFastScrollRecyclerView country_inter_list;
    private SegmentedGroup segmented_check;
    private RadioButton my_favourite;
    private LinearLayout update_favourite_layout;
    private LinearLayout my_favourite_layout;
    private RecyclerView my_favourite_list;

    public CompetitionsSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comp_settings, container, false);
        my_favourite_list = (RecyclerView) view.findViewById(R.id.my_favourite_list);
        country_inter_list = (IndexFastScrollRecyclerView) view.findViewById(R.id.country_inter_list);

        country_inter_list.setIndexBarTransparentValue(0);
        // listView.setIndexBarColor(ContextCompat.getColor(AddFriendsUsingMobileActivity.this, R.color.transparent));
        country_inter_list.setIndexBarTextColor("#6b6a6a");
        segmented_check = (SegmentedGroup) view.findViewById(R.id.segmented_check);
        my_favourite = (RadioButton) view.findViewById(R.id.my_favourite);
        my_favourite.setChecked(true);
        update_favourite_layout=(LinearLayout)view.findViewById(R.id.update_favourite_layout);
        my_favourite_layout=(LinearLayout)view.findViewById(R.id.my_favourite_layout);
        segmented_check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = segmented_check.getCheckedRadioButtonId();
                View radioButton = segmented_check.findViewById(radioButtonID);
                int idx = segmented_check.indexOfChild(radioButton);
                switch (idx) {
                    case 0:
                        update_favourite_layout.setVisibility(View.GONE);
                        my_favourite_layout.setVisibility(View.VISIBLE);
                        getFavourite();
                        break;
                    case 1:
                        update_favourite_layout.setVisibility(View.VISIBLE);
                        my_favourite_layout.setVisibility(View.GONE);
                        break;
                }
            }
        });
        getData();
        getFavourite();
        return view;

    }

    private void getData() {
        GetCategoryLeagueApiAsyncTask getCategoryLeagueApiAsyncTask = new GetCategoryLeagueApiAsyncTask(getActivity(), new StringResultCallback() {
            @Override
            public void processFinish(String result, final KProgressHUD progress) {
                MatchCategory matchCategory = new Gson().fromJson(result, MatchCategory.class);
                RecycleViewCompetitionsSettings recycleViewCompetitionsSettingsInternational = new RecycleViewCompetitionsSettings(getActivity(), matchCategory, 0);
                country_inter_list.setAdapter(recycleViewCompetitionsSettingsInternational);

              /*  country_inter_list.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //At this point the layout is complete and the
                        //dimensions of recyclerView and any child views are known.
                        progress.dismiss();
                    }
                });*/

                progress.dismiss();
            }
        });
        getCategoryLeagueApiAsyncTask.execute();

    }
    private void getFavourite(){
        ArrayList<String> allData = ToolUtils.getArrayOfCompAndClub(CompetitionsSettingsFragment.this.getActivity());
        ArrayList<String> comp=new ArrayList<>();
        ArrayList<String> club=new ArrayList<>();
        for (int i=0;i<allData.size();i++){
            if (allData.get(i).split("-")[1].split(":")[0].equalsIgnoreCase("competitions")){
                comp.add(allData.get(i));
            }else {
                club.add(allData.get(i));
            }

        }

        RecycleViewMyFavourite  recycleViewMyFavourite=new RecycleViewMyFavourite(CompetitionsSettingsFragment.this.getActivity(),comp,club,allData);
        my_favourite_list.setAdapter(recycleViewMyFavourite);
    }


}
