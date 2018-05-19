package com.oxygen.yallagoom.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.event.chat.RecycleViewChatChooseFriend;
import com.oxygen.yallagoom.api.event.GetMyFriendAsyncTask;
import com.oxygen.yallagoom.entity.Chat.ConversationsGroup;
import com.oxygen.yallagoom.entity.event.MyFriendList;
import com.oxygen.yallagoom.entity.User;
import com.oxygen.yallagoom.fragment.myEventTapFragment.ChatFragment;
import com.oxygen.yallagoom.interfaces.GetMyFriendListCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.FirebaseUtils;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddFriendChatActivity extends AppCompatActivity {

    private RecyclerView list_friends;
    private TextView ok;
    private TextView cancel;
    private EditText search_edit;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout parent;
    public static RelativeLayout group_lay;
    private EditText group_name_edit;
    private RecycleViewChatChooseFriend recycleViewChatFriend;
    private User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_chat);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(AddFriendChatActivity.this);
        ToolUtils.setLightStatusBar(parent, AddFriendChatActivity.this);
        myUser = new Gson().fromJson(ToolUtils.getSharedPreferences(AddFriendChatActivity.this, Constant.userData).getString(Constant.allUserData, null), User.class);

        list_friends = (RecyclerView) findViewById(R.id.list_friends);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(true);
        ok = (TextView) findViewById(R.id.ok);
        cancel = (TextView) findViewById(R.id.cancel);
        group_lay = (RelativeLayout) findViewById(R.id.group_lay);
        group_name_edit = (EditText) findViewById(R.id.group_name_edit);
        search_edit = (EditText) findViewById(R.id.search_edit);
        search_edit.setHint(getString(R.string.search));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().hasExtra("AddFriend")) {
                    addFriendToConversation(getIntent().getExtras().getString("ConversationKey"));
                } else {
                    if (recycleViewChatFriend != null && recycleViewChatFriend.userCredentialsArrayList != null && recycleViewChatFriend.userCredentialsArrayList.size() > 0) {
                        if (recycleViewChatFriend.userCredentialsArrayList.size() > 1 && group_name_edit.getText().toString().equalsIgnoreCase("")) {
                            ToolUtils.viewToast(AddFriendChatActivity.this, getString(R.string.enter_group_name));
                        } else if (recycleViewChatFriend.userCredentialsArrayList.size() > 1) {
                            registerChat();
                        } else {
                            if (ChatFragment.userCredentialsMap.size() != 0) {
                                for (int i = 0; i < ChatFragment.userCredentialsMap.size(); i++) {
                                    if (ChatFragment.userCredentialsMap.get(ChatFragment.userCredentialsMap.keySet().toArray()[i]).size() == 2) {
                                        for (int ii = 0; ii < ChatFragment.userCredentialsMap.get(ChatFragment.userCredentialsMap.keySet().toArray()[i]).size(); ii++) {
                                            Log.e("qweqwe",""+  ChatFragment.userCredentialsMap.get(ChatFragment.userCredentialsMap.keySet().toArray()[i]).get(ii).getKey());
                                            Log.e("qweqwe1",""+  recycleViewChatFriend.userCredentialsArrayList.get(0).getKey());
                                            if (recycleViewChatFriend.userCredentialsArrayList.get(0).getKey().equalsIgnoreCase(
                                                    ChatFragment.userCredentialsMap.get(ChatFragment.userCredentialsMap.keySet().toArray()[i]).get(ii).getKey()
                                            )) {
                                                Intent intent = new Intent(AddFriendChatActivity.this, ChatActivity.class);
                                                intent.putExtra("key", ChatFragment.userCredentialsMap.keySet().toArray()[i] + "");
                                                intent.putExtra("title", recycleViewChatFriend.userCredentialsArrayList.get(0).getName());
                                                intent.putExtra("listOfUser", ChatFragment.userCredentialsMap.get(ChatFragment.userCredentialsMap.keySet().toArray()[i]));
                                                startActivity(intent);
                                                finish();

                                                return;

                                            }
                                        }

                                    }
                                }
                                registerChat();
                                return;
                            } else {
                                registerChat();
                            }

                        }
                    } else {
                        ToolUtils.viewToast(AddFriendChatActivity.this, getString(R.string.add_friends));

                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();
    }

    private void getData() {
        GetMyFriendAsyncTask getMyFriendAsyncTask = new GetMyFriendAsyncTask(AddFriendChatActivity.this, new GetMyFriendListCallback() {
            @Override
            public void processFinish(MyFriendList myFriend) {
                ArrayList<User> users = new ArrayList<>();
                int userId = ToolUtils.getSharedPreferences(AddFriendChatActivity.this, Constant.userData).getInt(Constant.userId, -1);
                for (int i = 0; i < myFriend.getData().size(); i++) {
                    if (userId != -1 && userId == myFriend.getData().get(i).getUser_id()) {
                        if (myFriend.getData().get(i).getUser_target().getFirebase_auth_user_id() != null)
                            users.add(myFriend.getData().get(i).getUser_target());
                    } else {
                        if (myFriend.getData().get(i).getUser().getFirebase_auth_user_id() != null)
                            users.add(myFriend.getData().get(i).getUser());

                    }
                }
                recycleViewChatFriend = new RecycleViewChatChooseFriend(users);
                list_friends.setAdapter(recycleViewChatFriend);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        getMyFriendAsyncTask.execute();

    }

    private void registerChat() {
        Map<String, Object> parentMap = new HashMap<>();
        Map<String, Object> stringObjectMap = new HashMap<>();
        for (int i = 0; i < recycleViewChatFriend.userCredentialsArrayList.size(); i++) {
            Map<String, Object> rule = new HashMap<>();
            rule.put(Constant.rule, Constant.user_);
            stringObjectMap.put(recycleViewChatFriend.userCredentialsArrayList.get(i).getKey(), rule);
        }
        Map<String, Object> rule = new HashMap<>();
        if (recycleViewChatFriend.userCredentialsArrayList.size() > 1) {
            rule.put(Constant.rule, Constant.admin);
        } else {
            rule.put(Constant.rule, Constant.user_);

        }
        stringObjectMap.put(myUser.getFirebase_auth_user_id(), rule);

        ConversationsGroup conversationsGroup = new ConversationsGroup();
        conversationsGroup.setImage("none");
        conversationsGroup.setOwnerID(myUser.getFirebase_auth_user_id());
        conversationsGroup.setTimestamp(System.currentTimeMillis() / 1000);
        conversationsGroup.setTitle(group_name_edit.getText().toString());
        parentMap.put(Constant.users, stringObjectMap);
        if (recycleViewChatFriend.userCredentialsArrayList.size() > 1) {
            parentMap.put(Constant.group, conversationsGroup);

        }

        final String pushKey = FirebaseUtils.getDatabaseReference().child(Constant.conversations).push().getKey();
        FirebaseUtils.getDatabaseReference().child(Constant.conversations + "/" + pushKey).setValue(parentMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Intent intent = new Intent(AddFriendChatActivity.this, ChatActivity.class);
                    intent.putExtra("key", pushKey);
                    if (recycleViewChatFriend.userCredentialsArrayList.size() > 1) {
                        intent.putExtra("OwnerID", myUser.getFirebase_auth_user_id());
                        intent.putExtra("title", group_name_edit.getText().toString());
                    } else {
                        intent.putExtra("title", recycleViewChatFriend.userCredentialsArrayList.get(0).getName());
                    }
                    intent.putExtra("listOfUser", recycleViewChatFriend.userCredentialsArrayList);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void addFriendToConversation(String key) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        for (int i = 0; i < recycleViewChatFriend.userCredentialsArrayList.size(); i++) {
            Map<String, Object> rule = new HashMap<>();
            rule.put(Constant.rule, Constant.user_);
            stringObjectMap.put(recycleViewChatFriend.userCredentialsArrayList.get(i).getKey(), rule);
        }
        FirebaseUtils.getDatabaseReference().child(Constant.conversations + "/" + key + "/" + Constant.users).updateChildren(stringObjectMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Intent intent = new Intent();
                    intent.putExtra("add_member_result", recycleViewChatFriend.userCredentialsArrayList);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
