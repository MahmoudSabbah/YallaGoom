package com.oxygen.yallagoom.fragment.myEventTapFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.action.RecyclerItemClickListener;
import com.oxygen.yallagoom.activity.InviteToEventActivity;
import com.oxygen.yallagoom.activity.SearchCountryActivity;
import com.oxygen.yallagoom.activity.SearchSportsActivity;
import com.oxygen.yallagoom.adapter.event.findPlayer.RecycleViewFindPlayer;
import com.oxygen.yallagoom.adapter.RecycleViewGenderLevelAge;
import com.oxygen.yallagoom.api.event.SearchPlayerAsyncTask;
import com.oxygen.yallagoom.app.MainApplication;
import com.oxygen.yallagoom.entity.Player;
import com.oxygen.yallagoom.interfaces.AgePopUpCallback;
import com.oxygen.yallagoom.interfaces.PlayerCallback;
import com.oxygen.yallagoom.interfaces.PlayerListCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.AgeSelectPopup;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FindPlayerFragment extends Fragment {
    private RelativeLayout levels_lay;
    private RelativeLayout gender_layout;
    private ImageView arrow_bottom;
    private ImageView arrow_bottom2;
    private List<String> levelList;
    private List<String> genderList;
    private RelativeLayout filter_palyer_lay;
    private TextView genderLebel;
    private TextView levelsLebel;
    private RecyclerView levelsList;
    private RecyclerView list_gender;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView players_list;
    private RecycleViewFindPlayer recycleViewFindPlayer;
    private ArrayList<Player.PlayerList> selectPlayerLists;
    private TextView invite_Lebel;
    private RecycleViewGenderLevelAge levelsListItem;
    private RecycleViewGenderLevelAge genderListItem;
    private RelativeLayout category_lay;
    private RelativeLayout country_lay;
    private int sport_id = -1;
    private TextView category_name;
    private int country_id = -1;
    private TextView country_name;
    private int selectPositionGender = 0;
    private int selectPositionLevels = 0;
    private RelativeLayout invite_layout;
    private RelativeLayout no_data_layout;
    private View no_access_found;
    private RelativeLayout content;
    private LinearLayoutManager linearLayoutManager;
    private Parcelable recyclerViewState;
    private ImageView arrow_bottom3;
    private RelativeLayout age_layout;
    private RecyclerView list_age;
    private List<String> ageList;
    private RecycleViewGenderLevelAge ageListItem;
    private int selectPositionAge = 0;
    private TextView ageLebel;
    private AgeSelectPopup photoPopup;
    private int minCustom = -1;
    private int maxCustom = -1;
    private View viewTopRecy;
    private RelativeLayout search_view;
    private HorizontalScrollView filter_parent;
    private TextView search_home_icon;
    private TextView close;
    private RelativeLayout search_lay;
    private EditText search_edit;

    public FindPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_player, container, false);
        no_data_layout = (RelativeLayout) view.findViewById(R.id.no_data_layout);
        levelList = new ArrayList<>();
        levelList.add(getString(R.string.none));
        levelList.add(getString(R.string.beginner));
        levelList.add(getString(R.string.intermediate));
        levelList.add(getString(R.string.professional));

        genderList = new ArrayList<>();
        genderList.add(getString(R.string.both));
        genderList.add(getString(R.string.female));
        genderList.add(getString(R.string.Male));

        ageList = new ArrayList<>();
        ageList.add(getString(R.string.all));
        ageList.add("16-20");
        ageList.add("21-30");
        ageList.add("31-40");
        ageList.add("41-50");
        ageList.add(getString(R.string.custom));

        levelsListItem = new RecycleViewGenderLevelAge(levelList);
        genderListItem = new RecycleViewGenderLevelAge(genderList);
        ageListItem = new RecycleViewGenderLevelAge(ageList);

        search_home_icon = (TextView) getActivity().findViewById(R.id.search_home_icon);

        viewTopRecy = (View) view.findViewById(R.id.view);
        no_access_found = (View) view.findViewById(R.id.no_access_found);
        content = (RelativeLayout) view.findViewById(R.id.content);
        levelsList = (RecyclerView) view.findViewById(R.id.list);
        list_gender = (RecyclerView) view.findViewById(R.id.list_gender);
        list_age = (RecyclerView) view.findViewById(R.id.list_age);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        players_list = (RecyclerView) view.findViewById(R.id.players_list);
        filter_palyer_lay = (RelativeLayout) view.findViewById(R.id.filter_palyer_lay);
        filter_palyer_lay.setSelected(true);
        levels_lay = (RelativeLayout) view.findViewById(R.id.levels_layout);
        gender_layout = (RelativeLayout) view.findViewById(R.id.gender_layout);
        category_lay = (RelativeLayout) view.findViewById(R.id.category_lay);
        country_lay = (RelativeLayout) view.findViewById(R.id.country_lay);
        country_lay = (RelativeLayout) view.findViewById(R.id.country_lay);
        age_layout = (RelativeLayout) view.findViewById(R.id.age_layout);
        arrow_bottom = (ImageView) view.findViewById(R.id.arrow_bottom);
        arrow_bottom2 = (ImageView) view.findViewById(R.id.arrow_bottom2);
        arrow_bottom3 = (ImageView) view.findViewById(R.id.arrow_bottom3);
        genderLebel = (TextView) view.findViewById(R.id.gender);
        levelsLebel = (TextView) view.findViewById(R.id.levels);
        ageLebel = (TextView) view.findViewById(R.id.age);
        close = (TextView) view.findViewById(R.id.close);
        search_lay = (RelativeLayout) view.findViewById(R.id.search_lay);
        search_view = (RelativeLayout) view.findViewById(R.id.search_view);
        filter_parent = (HorizontalScrollView) view.findViewById(R.id.filter_parent);
        search_edit = (EditText) view.findViewById(R.id.search_edit);
        invite_layout = (RelativeLayout) view.findViewById(R.id.invite);
        invite_Lebel = (TextView) view.findViewById(R.id.invite_text);
        category_name = (TextView) view.findViewById(R.id.category_name);
        country_name = (TextView) view.findViewById(R.id.country_name);
        levelsList.setAdapter(levelsListItem);
        list_age.setAdapter(ageListItem);
        list_gender.setAdapter(genderListItem);
        levels_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (levelsList.getVisibility() == View.VISIBLE) {
                    levelsList.setVisibility(View.GONE);
                    arrow_bottom.setImageResource(R.drawable.ic_arrow_bottom);
                } else {
                    list_gender.setVisibility(View.GONE);
                    list_age.setVisibility(View.GONE);
                    levelsList.setVisibility(View.VISIBLE);
                    arrow_bottom.setImageResource(R.drawable.ic_arrow_up);
                    arrow_bottom2.setImageResource(R.drawable.ic_arrow_bottom);
                    arrow_bottom3.setImageResource(R.drawable.ic_arrow_bottom);
                }
            }
        });

        gender_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list_gender.getVisibility() == View.VISIBLE) {
                    list_gender.setVisibility(View.GONE);
                    arrow_bottom2.setImageResource(R.drawable.ic_arrow_bottom);
                } else {
                    levelsList.setVisibility(View.GONE);
                    list_age.setVisibility(View.GONE);
                    list_gender.setVisibility(View.VISIBLE);
                    arrow_bottom2.setImageResource(R.drawable.ic_arrow_up);
                    arrow_bottom3.setImageResource(R.drawable.ic_arrow_bottom);
                    arrow_bottom.setImageResource(R.drawable.ic_arrow_bottom);

                }
            }
        });
        age_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list_age.getVisibility() == View.VISIBLE) {
                    list_age.setVisibility(View.GONE);
                    arrow_bottom3.setImageResource(R.drawable.ic_arrow_bottom);
                } else {
                    levelsList.setVisibility(View.GONE);
                    list_gender.setVisibility(View.GONE);
                    list_age.setVisibility(View.VISIBLE);
                    arrow_bottom3.setImageResource(R.drawable.ic_arrow_up);
                    arrow_bottom2.setImageResource(R.drawable.ic_arrow_bottom);
                    arrow_bottom.setImageResource(R.drawable.ic_arrow_bottom);

                }
            }
        });
        filter_palyer_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!filter_palyer_lay.isSelected()) {
                    filter_palyer_lay.setSelected(true);
                    list_gender.setVisibility(View.GONE);
                    levelsList.setVisibility(View.GONE);
                    refreshItems("default", "", "", -1, -1, -1, "", -1);

                }

            }
        });
        invite_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectPlayerLists != null && selectPlayerLists.size() > 0) {
                    Intent intent = new Intent(FindPlayerFragment.this.getActivity(), InviteToEventActivity.class);
                    intent.putExtra("selectPlayerLists", selectPlayerLists);
                    startActivityForResult(intent, 106);
                } else {
                    ToolUtils.showSnak(FindPlayerFragment.this.getActivity(), getString(R.string.slect_one));
                }

            }
        });
        list_gender.addOnItemTouchListener(new RecyclerItemClickListener(FindPlayerFragment.this.getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectPositionGender = position;
                TextView textView = (TextView) view.findViewById(R.id.spinner_text);
                genderLebel.setText(textView.getText().toString());
                list_gender.setVisibility(View.GONE);
                arrow_bottom2.setImageResource(R.drawable.ic_arrow_bottom);
                filter_palyer_lay.setSelected(false);
                if (selectPlayerLists != null) {
                    selectPlayerLists.clear();
                    invite_Lebel.setText(getString(R.string.invite_) + " ( " + selectPlayerLists.size() + " )");
                }
                getRefreshData("filters");
            }
        }));
        levelsList.addOnItemTouchListener(new RecyclerItemClickListener(FindPlayerFragment.this.getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectPositionLevels = position;
                TextView textView = (TextView) view.findViewById(R.id.spinner_text);
                levelsLebel.setText(textView.getText().toString());
                levelsList.setVisibility(View.GONE);
                arrow_bottom.setImageResource(R.drawable.ic_arrow_bottom);
                filter_palyer_lay.setSelected(false);
                if (selectPlayerLists != null) {
                    selectPlayerLists.clear();
                    invite_Lebel.setText(getString(R.string.invite_) + " ( " + selectPlayerLists.size() + " )");
                }
                getRefreshData("filters");
            }
        }));
        list_age.addOnItemTouchListener(new RecyclerItemClickListener(FindPlayerFragment.this.getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectPositionAge = position;
                TextView textView = (TextView) view.findViewById(R.id.spinner_text);
                ageLebel.setText(textView.getText().toString());
                list_age.setVisibility(View.GONE);
                arrow_bottom3.setImageResource(R.drawable.ic_arrow_bottom);
                filter_palyer_lay.setSelected(false);
                if (selectPlayerLists != null) {
                    selectPlayerLists.clear();
                    invite_Lebel.setText(getString(R.string.invite_) + " ( " + selectPlayerLists.size() + " )");
                }
                if (position == 5) {
                    photoPopup = new AgeSelectPopup(FindPlayerFragment.this.getActivity(), new AgePopUpCallback() {
                        @Override
                        public void processFinish(int min, int max) {
                            photoPopup.dismiss();
                            minCustom = min;
                            maxCustom = max;
                            getRefreshData("filters");

                        }
                    });
                    photoPopup.showAtLocation(((Activity) getActivity()).findViewById(R.id.main_frame),
                            Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    getRefreshData("filters");

                }
            }
        }));
        category_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindPlayerFragment.this.getActivity(), SearchSportsActivity.class);
                intent.putExtra("sport_id", sport_id);
                startActivityForResult(intent, 101);
            }
        });
        country_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindPlayerFragment.this.getActivity(), SearchCountryActivity.class);
                intent.putExtra("id", country_id);
                startActivityForResult(intent, 102);
            }
        });
        if (MainApplication.verification_check) {
            refreshItems("default", "", "", -1, -1, -1, "", -1);
        } else {
            no_access_found.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        }

        search_home_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_view.setVisibility(View.VISIBLE);
                invite_layout.setVisibility(View.GONE);
                filter_parent.setVisibility(View.GONE);
                hideAndView();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_view.setVisibility(View.GONE);
                invite_layout.setVisibility(View.VISIBLE);
                filter_parent.setVisibility(View.VISIBLE);
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                search_edit.setText("");
                hideAndView();
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
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().toCharArray().length > 2) {
                    Log.e("SearchView", "" + s);
                    getDataSearchView(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;

    }

    private void refreshItems(String type, String player_name, String gender,
                              int country_id, int minage, int maxage, String rate, int sport_id) {
        refreshLayout.autoRefresh();
        SearchPlayerAsyncTask myPlayerAsyncTask = new SearchPlayerAsyncTask(FindPlayerFragment.this.getActivity(), type, player_name,
                gender, country_id, minage, maxage, rate, sport_id, new PlayerCallback() {
            @Override
            public void processFinish(Player player) {
                if (player.getData().size() == 0) {
                    no_data_layout.setVisibility(View.VISIBLE);
                } else {
                    no_data_layout.setVisibility(View.GONE);

                }
                recycleViewFindPlayer = new RecycleViewFindPlayer(player, new PlayerListCallback() {
                    @Override
                    public void processFinish(ArrayList<Player.PlayerList> playerLists) {
                        selectPlayerLists = playerLists;
                        invite_Lebel.setText(getText(R.string.invite_) + " ( " + playerLists.size() + " )");
                    }
                });
                players_list.setAdapter(recycleViewFindPlayer);
                onItemsLoadComplete();
            }
        });
        myPlayerAsyncTask.execute();


    }

    private void onItemsLoadComplete() {
        refreshLayout.finishRefresh();
    }

    private void getRefreshData(String type) {
        String level_result = "";
        String gender_result = "";
        int minAge = -1;
        int maxAge = -1;
        if (selectPositionLevels == 0) {
            level_result = "";
        } else if (selectPositionLevels == 1) {
            level_result = "B";
        } else if (selectPositionLevels == 2) {
            level_result = "I";
        } else if (selectPositionLevels == 3) {
            level_result = "P";
        }
        if (selectPositionGender == 0) {
            gender_result = "";
        } else if (selectPositionGender == 1) {
            gender_result = "f";
        } else if (selectPositionGender == 2) {
            gender_result = "m";
        }
        if (selectPositionAge == 0) {
            minAge = -1;
            maxAge = -1;
        } else if (selectPositionAge == 1) {
            minAge = 16;
            maxAge = 20;
        } else if (selectPositionAge == 2) {
            minAge = 21;
            maxAge = 30;
        } else if (selectPositionAge == 3) {
            minAge = 31;
            maxAge = 40;
        } else if (selectPositionAge == 4) {
            minAge = 41;
            maxAge = 50;
        } else if (selectPositionAge == 5) {
            minAge = minCustom;
            maxAge = maxCustom;
        }

        if (sport_id == -1 && country_id == -1 && level_result.equalsIgnoreCase("") && gender_result.equalsIgnoreCase("") && minAge == -1) {
        } else {
            refreshItems(type, "", gender_result, country_id, minAge, maxAge, level_result, sport_id);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                if (resultCode == 102) {
                    Bundle bundle = data.getExtras();
                    sport_id = bundle.getInt("sport");
                    if (sport_id == -1) {
                        category_name.setText(getString(R.string.category));
                    } else {
                        String sport_name = bundle.getString("sport_name");
                        category_name.setText(sport_name);
                    }
                    filter_palyer_lay.setSelected(false);
                    if (selectPlayerLists != null) {
                        selectPlayerLists.clear();
                        invite_Lebel.setText(getText(R.string.invite_) + " ( " + selectPlayerLists.size() + " )");
                    }
                    getRefreshData("filters");
                }
                break;
            case 102:
                if (resultCode == 102) {
                    country_id = data.getExtras().getInt("country_id");
                    if (country_id == -1) {
                        country_name.setText(getString(R.string.country));

                    } else {
                        country_name.setText(data.getExtras().getString("country_name"));
                    }
                    filter_palyer_lay.setSelected(false);
                    if (selectPlayerLists != null) {
                        selectPlayerLists.clear();
                        invite_Lebel.setText(getText(R.string.invite_) + " ( " + selectPlayerLists.size() + " )");
                    }
                    getRefreshData("filters");
                }
                break;
        }
    }

    private void hideAndView() {
        if (invite_layout.getVisibility() == View.VISIBLE) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewTopRecy.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, invite_layout.getId());
            //  viewTopRecy.setLayoutParams(params);
        } else if (search_view.getVisibility() == View.VISIBLE) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewTopRecy.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, search_view.getId());
            //    viewTopRecy.setLayoutParams(params);
        }
    }

    private void getDataSearchView(String value) {
        AndroidNetworking.post(Constant.urlData + Constant.players)
                .setTag("test")
                .addBodyParameter("type", "filters")
                .addBodyParameter("player_name", value)
                .addHeaders("Authorization", "Bearer " + ToolUtils.getSharedPreferences(getActivity(), Constant.userData).getString(Constant.userToken, null))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    public String status;

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt(Constant.status_callback);
                            if (status == 0) {
                                JSONObject errorMsg = response.getJSONObject(Constant.error_callback);
                            } else {
                                JSONObject data = response.getJSONObject(Constant.data);
                                Log.e("Players",""+data);
                                Player playerList = new Gson().fromJson(data.toString(), Player.class);
                                if (playerList.getData().size() == 0) {
                                    no_data_layout.setVisibility(View.VISIBLE);
                                } else {
                                    no_data_layout.setVisibility(View.GONE);

                                }
                                recycleViewFindPlayer = new RecycleViewFindPlayer(playerList, new PlayerListCallback() {
                                    @Override
                                    public void processFinish(ArrayList<Player.PlayerList> playerLists) {
                                        selectPlayerLists = playerLists;
                                        invite_Lebel.setText(getText(R.string.invite_) + " ( " + playerLists.size() + " )");
                                    }
                                });
                                players_list.setAdapter(recycleViewFindPlayer);

                            }
                            // Fetching the data from web service in background


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

}
