package com.oxygen.yallagoom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.action.RecyclerItemClickListener;
import com.oxygen.yallagoom.adapter.RecycleViewAddFriendsSearch;
import com.oxygen.yallagoom.adapter.RecycleViewGenderLevelAge;
import com.oxygen.yallagoom.api.SearchFriendsAsyncTask;
import com.oxygen.yallagoom.entity.Player;
import com.oxygen.yallagoom.interfaces.SearchFriendsCallback;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

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
    private int sport_id;
    private int country_id;
    private ArrayList<Player.PlayerList> selectPlayerLists;
    private RecycleViewAddFriendsSearch recycleViewAddFriendsSearch;
    private EditText search_bt;
    private TextView right_text;
    private RelativeLayout no_data_layout;

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

        levelsListItem = new RecycleViewGenderLevelAge(levelList);
        genderListItem = new RecycleViewGenderLevelAge(genderList);
        search_bt = (EditText) findViewById(R.id.search_bt);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        players_list = (RecyclerView) findViewById(R.id.players_list);
        levelsList = (RecyclerView) findViewById(R.id.list);
        list_gender = (RecyclerView) findViewById(R.id.list_gender);

        filter_palyer_lay = (RelativeLayout) findViewById(R.id.filter_palyer_lay);
        filter_palyer_lay.setSelected(true);

        levels_lay = (RelativeLayout) findViewById(R.id.levels_layout);
        gender_layout = (RelativeLayout) findViewById(R.id.gender_layout);
        category_lay = (RelativeLayout) findViewById(R.id.category_lay);
        country_lay = (RelativeLayout) findViewById(R.id.country_lay);
        arrow_bottom = (ImageView) findViewById(R.id.arrow_bottom);
        arrow_bottom2 = (ImageView) findViewById(R.id.arrow_bottom2);
        genderLebel = (TextView) findViewById(R.id.gender);
        levelsLebel = (TextView) findViewById(R.id.levels);
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
        search_bt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
                ArrayList<Player.PlayerList> listData = new ArrayList<>();
                for (int i = 0; i < selectPlayerLists.size(); i++) {
                    String name = selectPlayerLists.get(i).getFirst_name() + " " + selectPlayerLists.get(i).getLast_name();
                    if (ToolUtils.hexChecker(charSequence.toString().toUpperCase(), name.toUpperCase())) {
                        listData.add(selectPlayerLists.get(i));
                    }

                }
                recycleViewAddFriendsSearch.updateList(listData);
                recycleViewAddFriendsSearch.notifyDataSetChanged();
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
                startActivityForResult(intent, 101);
            }
        });
        country_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFriendsBySearchActivity.this, SearchCountryActivity.class);
                intent.putExtra("id",-1);
                startActivityForResult(intent, 102);
            }
        });
      /*  refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshItems("default", "", "", -1, -1, -1, "", -1);
            }
        });*/
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
                    String sport_name = bundle.getString("sport_name");
                    category_name.setText(sport_name);
                    filter_palyer_lay.setSelected(false);
                    getRefreshData("filters");
                }
                break;
            case 102:
                if (resultCode == 102) {
                    country_id = data.getExtras().getInt("country_id");
                    country_name.setText(data.getExtras().getString("country_name"));
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
        Log.e("level_result", "" + selectPositionLevels);

        refreshItems(type, "", gender_result, country_id, -1, -1, level_result, sport_id);
    }

}
