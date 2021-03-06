package com.oxygen.yallagoom.fragment.myEventTapFragment;

import android.content.Intent;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.AddGroupActivity;
import com.oxygen.yallagoom.adapter.event.group.RecycleViewListGroup;
import com.oxygen.yallagoom.api.event.GetGroupAsyncTask;
import com.oxygen.yallagoom.app.MainApplication;
import com.oxygen.yallagoom.entity.event.Group;
import com.oxygen.yallagoom.interfaces.DeleteFragmentCallback;
import com.oxygen.yallagoom.interfaces.GetGroupCallback;
import com.oxygen.yallagoom.widget.floatingactionbutton.FloatingActionButton;
import com.oxygen.yallagoom.widget.segmented.SegmentedGroup;

import java.util.ArrayList;


public class GroupFragment extends Fragment implements DeleteFragmentCallback {


    private SegmentedGroup segmented_group;
    private RadioButton my_groups;
    private RadioButton others_groups;
    private RecyclerView group_recycler;
    private SmartRefreshLayout refreshLayout;
    private RecycleViewListGroup recycleViewListGroup;
    private int check = 0;
    private ArrayList<Group.MyGroup.Data> groupData;
    private FloatingActionButton add_group;
    private ArrayList<Group.MyGroup.Data> groupDataMy_own_group;
    private ArrayList<Group.MyGroup.Data> groupDataGetOthers_group;
    private LinearLayout content_lay;
    private View no_access_found;
    private View no_data_layout;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        content_lay = (LinearLayout) view.findViewById(R.id.content_lay);
        no_access_found = (View) view.findViewById(R.id.no_access_found);
        no_data_layout = (View) view.findViewById(R.id.no_data_layout);

        segmented_group = (SegmentedGroup) view.findViewById(R.id.segmented_group);
        my_groups = (RadioButton) view.findViewById(R.id.my_groups);
        my_groups.setChecked(true);
        others_groups = (RadioButton) view.findViewById(R.id.others_groups);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        group_recycler = (RecyclerView) view.findViewById(R.id.group_recycler);
        add_group = (FloatingActionButton) view.findViewById(R.id.add_group);
        if (MainApplication.verification_check) {
            refreshLayout.autoRefresh();
        } else {
            no_access_found.setVisibility(View.VISIBLE);
            content_lay.setVisibility(View.GONE);
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(check);
            }
        });
        segmented_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonID = segmented_group.getCheckedRadioButtonId();
                View radioButton = segmented_group.findViewById(radioButtonID);
                int idx = segmented_group.indexOfChild(radioButton);
                if (groupDataGetOthers_group != null || groupDataMy_own_group != null) {
                    checkSelectGroup(idx);
                }
            }
        });
        add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupFragment.this.getActivity(), AddGroupActivity.class);
                startActivityForResult(intent, 306);
            }
        });
        return view;

    }

    private void getData(final int i) {
        GetGroupAsyncTask getGroupAsyncTask = new GetGroupAsyncTask(GroupFragment.this.getActivity(), new GetGroupCallback() {
            @Override
            public void processFinish(Group group) {
                groupDataMy_own_group = group.getMy_own_group().getData();
                groupDataGetOthers_group = group.getOthers_group().getData();
                checkSelectGroup(i);
                refreshLayout.finishRefresh();
            }
        });
        getGroupAsyncTask.execute();
    }


    @Override
    public void processFinish(int position, ArrayList<Group.MyGroup.Data> group, int check) {
        groupDataMy_own_group = group;
        if (groupDataMy_own_group.size() == 0) {
            no_data_layout.setVisibility(View.VISIBLE);
            group_recycler.setVisibility(View.GONE);
        } else {
            no_data_layout.setVisibility(View.GONE);
            group_recycler.setVisibility(View.VISIBLE);
        }
        recycleViewListGroup = new RecycleViewListGroup(groupDataMy_own_group, check, GroupFragment.this);
        group_recycler.setAdapter(recycleViewListGroup);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 306:
                if (resultCode == 102) {
                    String newGroup = data.getExtras().getString("data");
                    Group.MyGroup.Data newGroupData = new Gson().fromJson(newGroup.toString(), Group.MyGroup.Data.class);
//                    Log.e("newGroupData",""+newGroupData.getMembers_list().size());
                    //   groupDataMy_own_group.add(newGroupData);
                /*    recycleViewListGroup = new RecycleViewListGroup(groupDataMy_own_group, check, GroupFragment.this);
                    group_recycler.setAdapter(recycleViewListGroup);*/
                }
                break;
        }
    }

    private void checkSelectGroup(int idx) {
        if (idx == 0) {
            if (groupDataMy_own_group.size() == 0) {
                no_data_layout.setVisibility(View.VISIBLE);
                group_recycler.setVisibility(View.GONE);
            } else {
                no_data_layout.setVisibility(View.GONE);
                group_recycler.setVisibility(View.VISIBLE);
            }
            recycleViewListGroup = new RecycleViewListGroup(groupDataMy_own_group, 0, GroupFragment.this);
            group_recycler.setAdapter(recycleViewListGroup);
            check = 0;
        } else {
            if (groupDataGetOthers_group.size() == 0) {
                no_data_layout.setVisibility(View.VISIBLE);
                group_recycler.setVisibility(View.GONE);
            } else {
                no_data_layout.setVisibility(View.GONE);
                group_recycler.setVisibility(View.VISIBLE);
            }
            recycleViewListGroup = new RecycleViewListGroup(groupDataGetOthers_group, 1, GroupFragment.this);
            group_recycler.setAdapter(recycleViewListGroup);
            check = 1;
        }
    }
}
