package com.oxygen.yallagoom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.interfaces.AgePopUpCallback;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.widget.AgeSelectPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.action.RecyclerItemClickListener;
import com.oxygen.yallagoom.adapter.RecycleViewAddFriendsSearch;
import com.oxygen.yallagoom.adapter.RecycleViewGenderLevelAge;
import com.oxygen.yallagoom.api.SearchFriendsAsyncTask;
import com.oxygen.yallagoom.entity.event.Player;
import com.oxygen.yallagoom.interfaces.SearchFriendsCallback;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsBySearchActivity extends AppCompatActivity {

    private LinearLayout parent;
    private RecyclerView players_list;
    private SmartRefreshLayout refreshLayout;
    private ArrayList<String> levelList;
    private ArrayList<String> genderList;
    private RecycleViewGenderLevelAge levelsListItem;
    private RecycleViewGenderLevelAge genderListItem;
    private RecyclerView levelsList;
    private RecyclerView list_gender;
    private RelativeLayout filter_palyer_lay;
    private RelativeLayout levels_lay;
    private RelativeLayout gender_layout;
    private RelativeLayout category_lay;
    private RelativeLayout country_lay;
    private ImageView arrow_bottom;
    private ImageView arrow_bottom2;
    private TextView genderLebel;
    private TextView levelsLebel;
    private TextView category_name;
    private TextView country_name;
    private int selectPositionGender;
    private int selectPositionLevels;
    private int sport_id=-1;
    private int country_id=-1;
    private ArrayList<Player.PlayerList> selectPlayerLists;
    private RecycleViewAddFriendsSearch recycleViewAddFriendsSearch;
    private EditText search_bt;
    private TextView right_text;
    private RelativeLayout no_data_layout;
    private RecyclerView list_age;
    private List<String> ageList;
    private RelativeLayout age_layout;
    private ImageView arrow_bottom3;
    private TextView ageLebel;
    private RecycleViewGenderLevelAge ageListItem;
    private int selectPositionAge = 0;
    private AgeSelectPopup photoPopup;
    private int minCustom = -1;
    private int maxCustom = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_by_search);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(AddFriendsBySearchActivity.this);
        ToolUtils.setLightStatusBar(parent, AddFriendsBySearchActivity.this);
        no_data_layout = (RelativeLayout) findViewById(R.id.no_data_layout);

        right_text = (TextView) findViewById(R.id.right_text);
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
        search_bt = (EditText) findViewById(R.id.search_bt);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        players_list = (RecyclerView) findViewById(R.id.players_list);
        levelsList = (RecyclerView) findViewById(R.id.list);
        list_gender = (RecyclerView) findViewById(R.id.list_gender);
        list_age = (RecyclerView) findViewById(R.id.list_age);

        filter_palyer_lay = (RelativeLayout) findViewById(R.id.filter_palyer_lay);
        filter_palyer_lay.setSelected(true);

        levels_lay = (RelativeLayout) findViewById(R.id.levels_layout);
        gender_layout = (RelativeLayout) findViewById(R.id.gender_layout);
        category_lay = (RelativeLayout) findViewById(R.id.category_lay);
        country_lay = (RelativeLayout) findViewById(R.id.country_lay);
        age_layout = (RelativeLayout) findViewById(R.id.age_layout);
        arrow_bottom = (ImageView) findViewById(R.id.arrow_bottom);
        arrow_bottom2 = (ImageView) findViewById(R.id.arrow_bottom2);
        arrow_bottom3 = (ImageView) findViewById(R.id.arrow_bottom3);
        genderLebel = (TextView) findViewById(R.id.gender);
        levelsLebel = (TextView) findViewById(R.id.levels);
        ageLebel = (TextView) findViewById(R.id.age);
        category_name = (TextView) findViewById(R.id.category_name);
        country_name = (TextView) findViewById(R.id.country_name);
        levelsList.setAdapter(levelsListItem);
        levels_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (levelsList.getVisibility() == View.VISIBLE) {
                    levelsList.setVisibility(View.GONE);
                    arrow_bottom.setImageResource(R.drawable.ic_arrow_bottom);
                } else {
                    list_gender.setVisibility(View.GONE);
                    levelsList.setVisibility(View.VISIBLE);
                    arrow_bottom.setImageResource(R.drawable.ic_arrow_up);
                    arrow_bottom2.setImageResource(R.drawable.ic_arrow_bottom);
                }
            }
        });
        list_gender.setAdapter(genderListItem);
        list_age.setAdapter(ageListItem);
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    list_gender.setVisibility(View.VISIBLE);
                    arrow_bottom2.setImageResource(R.drawable.ic_arrow_up);
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
        search_bt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
                if (charSequence.toString().toCharArray().length > 2) {
                    getDataSearchView(charSequence.toString());
                }
              /*  ArrayList<Player.PlayerList> listData = new ArrayList<>();
                for (int i = 0; i < selectPlayerLists.size(); i++) {
                    String name = selectPlayerLists.get(i).getFirst_name() + " " + selectPlayerLists.get(i).getLast_name();
                    if (ToolUtils.hexChecker(charSequence.toString().toUpperCase(), name.toUpperCase())) {
                        listData.add(selectPlayerLists.get(i));
                    }

                }
                recycleViewAddFriendsSearch.updateList(listData);
                recycleViewAddFriendsSearch.notifyDataSetChanged();*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        list_gender.addOnItemTouchListener(new RecyclerItemClickListener(AddFriendsBySearchActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectPositionGender = position;
                TextView textView = (TextView) view.findViewById(R.id.spinner_text);
                genderLebel.setText(textView.getText().toString());
                list_gender.setVisibility(View.GONE);
                arrow_bottom2.setImageResource(R.drawable.ic_arrow_bottom);
                filter_palyer_lay.setSelected(false);
                getRefreshData("filters");
            }
        }));
        levelsList.addOnItemTouchListener(new RecyclerItemClickListener(AddFriendsBySearchActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectPositionLevels = position;
                TextView textView = (TextView) view.findViewById(R.id.spinner_text);
                levelsLebel.setText(textView.getText().toString());
                levelsList.setVisibility(View.GONE);
                arrow_bottom.setImageResource(R.drawable.ic_arrow_bottom);
                filter_palyer_lay.setSelected(false);
                getRefreshData("filters");
            }
        }));
        category_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFriendsBySearchActivity.this, SearchSportsActivity.class);
                intent.putExtra("sport_id", sport_id);
                startActivityForResult(intent, 101);
            }
        });
        country_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFriendsBySearchActivity.this, SearchCountryActivity.class);
                intent.putExtra("id", country_id);
                startActivityForResult(intent, 102);
            }
        });
        list_age.addOnItemTouchListener(new RecyclerItemClickListener(AddFriendsBySearchActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectPositionAge = position;
                TextView textView = (TextView) view.findViewById(R.id.spinner_text);
                ageLebel.setText(textView.getText().toString());
                list_age.setVisibility(View.GONE);
                arrow_bottom3.setImageResource(R.drawable.ic_arrow_bottom);
                filter_palyer_lay.setSelected(false);
                if (position == 5) {
                    photoPopup = new AgeSelectPopup(AddFriendsBySearchActivity.this, new AgePopUpCallback() {
                        @Override
                        public void processFinish(int min, int max) {
                            photoPopup.dismiss();
                            minCustom = min;
                            maxCustom = max;
                            getRefreshData("filters");

                        }
                    });
                    photoPopup.showAtLocation((AddFriendsBySearchActivity.this).findViewById(R.id.parent),
                            Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    getRefreshData("filters");

                }
            }
        }));
        refreshItems("default", "", "", -1, -1, -1, "", -1);

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

                    getRefreshData("filters");
                }
                break;
        }
    }

    public void Back(View view) {
        finish();
    }

    private void refreshItems(String type, String player_name, String gender,
                              int country_id, int minage, int maxage, String rate, int sport_id) {
        refreshLayout.autoRefresh();
        SearchFriendsAsyncTask searchFriendsAsyncTask = new SearchFriendsAsyncTask(AddFriendsBySearchActivity.this, type, player_name,
                gender, country_id, minage, maxage, rate, sport_id, new SearchFriendsCallback() {
            @Override
            public void finishProcess(Player player) {
                if (player.getData().size()==0){
                    no_data_layout.setVisibility(View.VISIBLE);
                }else {
                    no_data_layout.setVisibility(View.GONE);

                }
                selectPlayerLists = player.getData();
                recycleViewAddFriendsSearch = new RecycleViewAddFriendsSearch(player);
                players_list.setAdapter(recycleViewAddFriendsSearch);
                onItemsLoadComplete();
            }
        });
        searchFriendsAsyncTask.execute();


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
    private void getDataSearchView(String value) {
        ToolUtils.getDataSearchView(value, AddFriendsBySearchActivity.this, new StringResultCallback() {
            @Override
            public void processFinish(String result, KProgressHUD progress) {
                Player playerList = new Gson().fromJson(result, Player.class);
                if (playerList.getData().size() == 0) {
                    no_data_layout.setVisibility(View.VISIBLE);
                } else {
                    no_data_layout.setVisibility(View.GONE);

                }
                recycleViewAddFriendsSearch = new RecycleViewAddFriendsSearch(playerList);
                players_list.setAdapter(recycleViewAddFriendsSearch);

            }
        });

    }
}
