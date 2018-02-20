package com.yallagoom.fragment.eventTapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yallagoom.R;
import com.yallagoom.action.RecyclerItemClickListener;
import com.yallagoom.activity.InviteToEventActivity;
import com.yallagoom.activity.SearchCountryActivity;
import com.yallagoom.activity.SearchSportsActivity;
import com.yallagoom.adapter.RecycleViewFindPlayer;
import com.yallagoom.adapter.RecycleViewGenderLevel;
import com.yallagoom.api.MyPlayerAsyncTask;
import com.yallagoom.entity.Player;
import com.yallagoom.interfaces.PlayerCallback;
import com.yallagoom.interfaces.PlayerListCallback;
import com.yallagoom.utils.ToolUtils;

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
    private RecycleViewGenderLevel levelsListItem;
    private RecycleViewGenderLevel genderListItem;
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

        levelsListItem = new RecycleViewGenderLevel(levelList);
        genderListItem = new RecycleViewGenderLevel(genderList);

        levelsList = (RecyclerView) view.findViewById(R.id.list);
        list_gender = (RecyclerView) view.findViewById(R.id.list_gender);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        players_list = (RecyclerView) view.findViewById(R.id.players_list);

        filter_palyer_lay = (RelativeLayout) view.findViewById(R.id.filter_palyer_lay);
        filter_palyer_lay.setSelected(true);
        levels_lay = (RelativeLayout) view.findViewById(R.id.levels_layout);
        gender_layout = (RelativeLayout) view.findViewById(R.id.gender_layout);
        category_lay = (RelativeLayout) view.findViewById(R.id.category_lay);
        country_lay = (RelativeLayout) view.findViewById(R.id.country_lay);
        arrow_bottom = (ImageView) view.findViewById(R.id.arrow_bottom);
        arrow_bottom2 = (ImageView) view.findViewById(R.id.arrow_bottom2);
        genderLebel = (TextView) view.findViewById(R.id.gender);
        levelsLebel = (TextView) view.findViewById(R.id.levels);
        invite_layout = (RelativeLayout) view.findViewById(R.id.invite);
        invite_Lebel = (TextView) view.findViewById(R.id.invite_text);
        category_name = (TextView) view.findViewById(R.id.category_name);
        country_name = (TextView) view.findViewById(R.id.country_name);
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
                    invite_Lebel.setText(getText(R.string.invite_) + " ( " + selectPlayerLists.size() + " )");
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
                    invite_Lebel.setText(getText(R.string.invite_) + " ( " + selectPlayerLists.size() + " )");
                }
                getRefreshData("filters");
            }
        }));
        category_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindPlayerFragment.this.getActivity(), SearchSportsActivity.class);
                startActivityForResult(intent, 101);
            }
        });
        country_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindPlayerFragment.this.getActivity(), SearchCountryActivity.class);
                startActivityForResult(intent, 102);
            }
        });
        refreshItems("default", "", "", -1, -1, -1, "", -1);

        return view;

    }

    private void refreshItems(String type, String player_name, String gender,
                              int country_id, int minage, int maxage, String rate, int sport_id) {
        refreshLayout.autoRefresh();
        MyPlayerAsyncTask myPlayerAsyncTask = new MyPlayerAsyncTask(FindPlayerFragment.this.getActivity(), type, player_name,
                gender, country_id, minage, maxage, rate, sport_id, new PlayerCallback() {
            @Override
            public void processFinish(Player player) {
                if (player.getData().size()==0){
                    no_data_layout.setVisibility(View.VISIBLE);
                }else {
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
                    country_name.setText(data.getExtras().getString("country_name"));
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

}
