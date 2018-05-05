package com.oxygen.yallagoom.fragment.myEventTapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.AddFriendsBySearchActivity;
import com.oxygen.yallagoom.activity.AddFriendsUsingMobileActivity;
import com.oxygen.yallagoom.adapter.event.friends.RecycleViewMyFriendsList;
import com.oxygen.yallagoom.adapter.event.friends.RecycleViewNewFriendsRequest;
import com.oxygen.yallagoom.api.event.GetMyFriendAsyncTask;
import com.oxygen.yallagoom.api.event.GetRequestFriendAsyncTask;
import com.oxygen.yallagoom.app.MainApplication;
import com.oxygen.yallagoom.entity.MyFriendList;
import com.oxygen.yallagoom.entity.MyFriends;
import com.oxygen.yallagoom.entity.User;
import com.oxygen.yallagoom.interfaces.GetMyFriendCallback;
import com.oxygen.yallagoom.interfaces.GetMyFriendListCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.floatingactionbutton.FloatingActionButton;
import com.oxygen.yallagoom.widget.floatingactionbutton.FloatingActionsMenu;
import com.oxygen.yallagoom.widget.segmented.SegmentedGroup;

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
    private LinearLayout content_lay;
    private View no_access_found;

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
        content_lay = (LinearLayout) view.findViewById(R.id.content_lay);
        no_access_found = (View) view.findViewById(R.id.no_access_found);
        no_data_layout = (RelativeLayout) view.findViewById(R.id.no_data_layout);
        segmented_friends = (SegmentedGroup) view.findViewById(R.id.segmented_friends);
        friends_list = (RadioButton) view.findViewById(R.id.friends_list);
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
        if (MainApplication.verification_check) {

            if (getActivity().getIntent().hasExtra("ActionNotification")) {
                String type= getActivity().getIntent().getExtras().getString("ActionNotification");
                //getActivity().getIntent().removeExtra("ActionNotification");
            /*    boolean x=  getActivity().getIntent().getExtras().getBoolean("active");
                Log.e("active",""+x);
                getActivity().getIntent().putExtra("active", false);*/
                Log.e("typetype",""+type);
                switch (type) {
                    case "friend_request":
                        new_requests.setChecked(true);
                        break;
                    case "you_got_new_friend":
                        friends_list.setChecked(true);
                        break;
                    default:
                        friends_list.setChecked(true);
                        break;

                }
                refreshLayout.autoRefresh();

            } else {
                Log.e("typetype","haveNot");
                friends_list.setChecked(true);
                refreshLayout.autoRefresh();
            }
        } else {
            no_access_found.setVisibility(View.VISIBLE);
            content_lay.setVisibility(View.GONE);
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (!checkrefresh) {
                    int radioButtonID = segmented_friends.getCheckedRadioButtonId();
                    View radioButton = segmented_friends.findViewById(radioButtonID);
                    int idx = segmented_friends.indexOfChild(radioButton);
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
                List<User> users = new ArrayList<>();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().getIntent().hasExtra("ActionNotification")) {
            getActivity().getIntent().removeExtra("ActionNotification");
        }
    }
}
