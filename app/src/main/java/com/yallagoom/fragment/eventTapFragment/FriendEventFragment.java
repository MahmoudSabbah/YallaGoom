package com.yallagoom.fragment.eventTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewFriendsListEvent;
import com.yallagoom.api.GetEventFriendAsyncTask;
import com.yallagoom.api.SearchFriendsAsyncTask;
import com.yallagoom.entity.FriendsEvents;
import com.yallagoom.entity.Player;
import com.yallagoom.interfaces.FriendsEventCallback;
import com.yallagoom.interfaces.PlayerListCallback;
import com.yallagoom.interfaces.SearchFriendsCallback;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.Date;


public class FriendEventFragment extends Fragment {


    public FriendEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_event, container, false);
        getData();
        return view;

    }

    private void getData() {
        GetEventFriendAsyncTask getEventFriendAsyncTask = new GetEventFriendAsyncTask(FriendEventFragment.this.getActivity(), new FriendsEventCallback() {
            @Override
            public void processFinish(FriendsEvents friendsEvents) {
                for (int i = 0; i < friendsEvents.getData().size(); i++) {
                    Log.e("friendsEvents", "" + ToolUtils.getDay(ToolUtils.converStringToDate(
                            friendsEvents.getData().get(i).getStartEventDate(),"yyyy-MM-dd"// HH:mm:ss
                    )));

                }

            }
        });
        getEventFriendAsyncTask.execute();
    }


}
