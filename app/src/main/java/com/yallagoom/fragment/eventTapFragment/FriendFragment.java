package com.yallagoom.fragment.eventTapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yallagoom.R;
import com.yallagoom.activity.AddFriendsBySearchActivity;
import com.yallagoom.activity.AddFriendsUsingMobileActivity;
import com.yallagoom.adapter.RecycleViewMyFriendsList;
import com.yallagoom.adapter.RecycleViewNewFriendsRequest;
import com.yallagoom.api.GetMyFriendAsyncTask;
import com.yallagoom.api.GetRequestFriendAsyncTask;
import com.yallagoom.entity.MyFriendList;
import com.yallagoom.entity.MyFriends;
import com.yallagoom.interfaces.GetMyFriendCallback;
import com.yallagoom.interfaces.GetMyFriendListCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.floatingactionbutton.FloatingActionButton;
import com.yallagoom.widget.floatingactionbutton.FloatingActionsMenu;
import com.yallagoom.widget.segmented.SegmentedGroup;

import java.util.ArrayList;
import java.util.List;


public class FriendFragment extends Fragment {


    RecyclerView mRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private FloatingActionsMenu multiple_actions_down;
    private FloatingActionButton search_friends;
    private FloatingActionButton contact_friend;
    private RecycleViewMyFriendsList recycleViewFindPlayer;
    private SegmentedGroup segmented_friends;
    private RadioButton friends_list;
    private RadioButton new_requests;
    private RecycleViewNewFriendsRequest recycleViewNewFriendsRequest;
    private boolean checkrefresh = false;
    private RelativeLayout no_data_layout;

    public FriendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fast_scroller_recycler);
        //   search_bt = (EditText) view.findViewById(R.id.search_bt);
        no_data_layout = (RelativeLayout) view.findViewById(R.id.no_data_layout);
        segmented_friends = (SegmentedGroup) view.findViewById(R.id.segmented_friends);
        friends_list = (RadioButton) view.findViewById(R.id.friends_list);
        friends_list.setChecked(true);
        new_requests = (RadioButton) view.findViewById(R.id.new_requests);
        multiple_actions_down = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions_down);
        search_friends = (FloatingActionButton) view.findViewById(R.id.search_friends);
        contact_friend = (FloatingActionButton) view.findViewById(R.id.contact_friend);
        // mRecyclerView.setIndexBarColor("#199899");
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        segmented_friends.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                refreshLayout.autoRefresh();
                int radioButtonID = segmented_friends.getCheckedRadioButtonId();
                View radioButton = segmented_friends.findViewById(radioButtonID);
                int idx = segmented_friends.indexOfChild(radioButton);
                Log.e("idxidxidx", "" + idx);
                if (idx == 0) {
                    refreshItems();
                } else {
                    getFriendsRequest();
                }
                checkrefresh = true;

            }
        });
     /*   search_bt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
                ArrayList<MyFriends.FriendsList> listData = new ArrayList<>();
                for (int i = 0; i < selectPlayerLists.size(); i++) {
                    String name= selectPlayerLists.get(i).getUser().getFirst_name()+" "+selectPlayerLists.get(i).getUser().getLast_name();

                    if (ToolUtils.hexChecker(charSequence.toString().toUpperCase(), name.toUpperCase())) {
                        // list.add(list.get(i));
                        listData.add(selectPlayerLists.get(i));

                    }

                }
                recycleViewFindPlayer.updateList(listData);
                recycleViewFindPlayer.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
        search_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendFragment.this.getActivity(), AddFriendsBySearchActivity.class);
                startActivity(intent);
            }
        });
        contact_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendFragment.this.getActivity(), AddFriendsUsingMobileActivity.class);
                startActivity(intent);
            }
        });
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (!checkrefresh) {
                    int radioButtonID = segmented_friends.getCheckedRadioButtonId();
                    View radioButton = segmented_friends.findViewById(radioButtonID);
                    int idx = segmented_friends.indexOfChild(radioButton);
                    Log.e("idxidxidx", "" + idx);
                    if (idx == 0) {
                        refreshItems();
                    } else {
                        getFriendsRequest();
                    }
                    checkrefresh = false;
                } else {
                    checkrefresh = false;
                    refreshLayout.finishRefresh();
                }

            }
        });
        return view;

    }

    private void refreshItems() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        multiple_actions_down.setVisibility(View.VISIBLE);
        GetMyFriendAsyncTask getMyFriendAsyncTask = new GetMyFriendAsyncTask(FriendFragment.this.getActivity(), new GetMyFriendListCallback() {
            @Override
            public void processFinish(MyFriendList myFriend) {
                if (myFriend.getData().size() == 0) {
                    no_data_layout.setVisibility(View.VISIBLE);
                } else {
                    no_data_layout.setVisibility(View.GONE);

                }
                List<MyFriendList.Data.User> users = new ArrayList<>();
                if (FriendFragment.this.getActivity() != null) {
                    int userId = ToolUtils.getSharedPreferences(FriendFragment.this.getActivity(), Constant.userData).getInt(Constant.userId, -1);
                    for (int i = 0; i < myFriend.getData().size(); i++) {
                        Log.e("idUser", userId + " - " + myFriend.getData().get(i).getUser_id());
                        if (userId != -1 && userId == myFriend.getData().get(i).getUser_id()) {
                            users.add(myFriend.getData().get(i).getUser_target());
                        } else {
                            users.add(myFriend.getData().get(i).getUser());

                        }
                    }

                    recycleViewFindPlayer = new RecycleViewMyFriendsList(users, FriendFragment.this.getActivity());
                    mRecyclerView.setAdapter(recycleViewFindPlayer);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    onItemsLoadComplete();
                }
            }
        });
        getMyFriendAsyncTask.execute();

    }

    private void onItemsLoadComplete() {
        refreshLayout.finishRefresh();
    }

    private void getFriendsRequest() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        multiple_actions_down.setVisibility(View.GONE);
        GetRequestFriendAsyncTask getRequestFriendAsyncTask = new GetRequestFriendAsyncTask(FriendFragment.this.getActivity(), new GetMyFriendCallback() {
            @Override
            public void processFinish(MyFriends myFriend) {
                if (myFriend.getData().size() == 0) {
                    no_data_layout.setVisibility(View.VISIBLE);
                } else {
                    no_data_layout.setVisibility(View.GONE);

                }
                recycleViewNewFriendsRequest = new RecycleViewNewFriendsRequest(myFriend.getData());
                mRecyclerView.setAdapter(recycleViewNewFriendsRequest);
                mRecyclerView.setVisibility(View.VISIBLE);
                onItemsLoadComplete();
            }
        });
        getRequestFriendAsyncTask.execute();
    }

}
