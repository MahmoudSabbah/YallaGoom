package com.yallagoom.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewFriendsListGroup;
import com.yallagoom.api.AddGroupAsyncTask;
import com.yallagoom.api.SearchFriendsAsyncTask;
import com.yallagoom.entity.Player;
import com.yallagoom.interfaces.SearchFriendsCallback;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

public class AddGroupActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView left_text;
    private TextView right_text;
    private TextView header_title;
    private RecyclerView _list;
    private RecycleViewFriendsListGroup recycleViewFriendsListGroup;
    private SwipeRefreshLayout swipe_refresh;
    private EditText search_bt;
    private ArrayList<Player.PlayerList> playerList;
    private EditText title_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        parent = (LinearLayout) findViewById(R.id.parent);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ToolUtils.hideStatus(AddGroupActivity.this);
        ToolUtils.setLightStatusBar(parent, AddGroupActivity.this);
        left_text = (TextView) findViewById(R.id.left_text);
        right_text = (TextView) findViewById(R.id.right_text);
        header_title = (TextView) findViewById(R.id.name_header);
        header_title.setText(getString(R.string.new_group));
        right_text.setText(getString(R.string.save));
        left_text.setText(getString(R.string.groups));
        _list = (RecyclerView) findViewById(R.id._list);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        search_bt = (EditText) findViewById(R.id.search_bt);
        title_group = (EditText) findViewById(R.id.title_group);
        swipe_refresh.setEnabled(false);
        swipe_refresh.setRefreshing(true);
        search_bt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
                ArrayList<Player.PlayerList> listData = new ArrayList<>();
                for (int i = 0; i < playerList.size(); i++) {
                    String name = playerList.get(i).getFirst_name() + " " + playerList.get(i).getLast_name();

                    if (ToolUtils.hexChecker(charSequence.toString().toUpperCase(), name.toUpperCase())) {
                        // list.add(list.get(i));
                        listData.add(playerList.get(i));

                    } else {
                    }

                }
                recycleViewFriendsListGroup.updateList(listData);
                _list.setAdapter(recycleViewFriendsListGroup);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!title_group.getText().toString().equalsIgnoreCase("")) {
                    List<Integer> idList = new ArrayList<>();
                    for (int i = 0; i < playerList.size(); i++) {
                        if (playerList.get(i).getCheckSelectGroup() == 1) {
                            idList.add(playerList.get(i).getId());
                        }
                    }
                    AddGroupAsyncTask addGroupAsyncTask = new AddGroupAsyncTask(AddGroupActivity.this, idList, null, title_group.getText().toString());
                    addGroupAsyncTask.execute();
                }else {
                    ToolUtils.viewToast(AddGroupActivity.this,getString(R.string.group_title));
                }
            }
        });

        getData();
    }

    public void Back(View view) {
        finish();
    }

    private void getData() {
        SearchFriendsAsyncTask searchFriendsAsyncTask = new SearchFriendsAsyncTask(AddGroupActivity.this, "default", "", "", -1, -1, -1, "", -1, new SearchFriendsCallback() {
            @Override
            public void finishProcess(Player player) {
                playerList = player.getData();
                recycleViewFriendsListGroup = new RecycleViewFriendsListGroup(player.getData());
                _list.setAdapter(recycleViewFriendsListGroup);
                swipe_refresh.setRefreshing(false);
            }
        });
        searchFriendsAsyncTask.execute();

    }
}
