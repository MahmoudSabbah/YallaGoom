package com.oxygen.yallagoom.fragment.myEventTapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.AddFriendChatActivity;
import com.oxygen.yallagoom.adapter.event.chat.RecycleViewConversations;
import com.oxygen.yallagoom.app.MainApplication;
import com.oxygen.yallagoom.entity.Chat.ConversationsGroup;
import com.oxygen.yallagoom.entity.Chat.UserConversations;
import com.oxygen.yallagoom.entity.Chat.UserCredentials;
import com.oxygen.yallagoom.entity.User;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.FirebaseUtils;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ChatFragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private boolean auto = true;
    private RecyclerView alert_list;

    private int page = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recycleListConversation;
    private FloatingActionButton fabAddConversation;
    private User user;
    private Map<String, UserConversations> lastMes;
    private Map<String, ConversationsGroup> conversationsGroupMap;
    public static Map<String, ArrayList<UserCredentials>> userCredentialsMap;
    private RecycleViewConversations recycleViewConversations;
    private View no_access_found;
    private RelativeLayout content;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        user = new Gson().fromJson(ToolUtils.getSharedPreferences(ChatFragment.this.getContext(), Constant.userData).getString(Constant.allUserData, null), User.class);
        lastMes = new HashMap<>();
        userCredentialsMap = new HashMap<>();
        conversationsGroupMap = new HashMap<>();
        no_access_found = (View) view.findViewById(R.id.no_access_found);
        content = (RelativeLayout) view.findViewById(R.id.content);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        recycleListConversation = (RecyclerView) view.findViewById(R.id.recycleListConversation);
        fabAddConversation = (FloatingActionButton) view.findViewById(R.id.fabAddConversation);
        fabAddConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatFragment.this.getActivity(), AddFriendChatActivity.class);
                startActivity(intent);
            }
        });
        recycleViewConversations = new RecycleViewConversations(lastMes, userCredentialsMap, conversationsGroupMap, user);
        recycleListConversation.setAdapter(recycleViewConversations);
     /*   Map<String,Object> stringObjectMap=new HashMap<>();
        stringObjectMap.put("ConversationsGroup",new ConversationsGroup());

        FirebaseUtils.getDatabaseReference().child("Test").setValue(stringObjectMap);*/
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setEnabled(true);
      /*  swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/
        if (MainApplication.verification_check) {
            getAllChatFirebase2();
        }else {
            no_access_found.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        }
        return view;

    }


    private void getAllChatFirebase2() {
        FirebaseUtils.getDatabaseReference().child(Constant.users + "/" + user.getFirebase_auth_user_id() + "/" + Constant.conversations).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() == 0) {
                            swipeRefreshLayout.setRefreshing(false);
                            swipeRefreshLayout.setEnabled(false);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        FirebaseUtils.getDatabaseReference().child(Constant.users + "/" + user.getFirebase_auth_user_id() + "/" + Constant.conversations).
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        getUserKeys(dataSnapshot.getKey());
                        //   getLastMessage(dataSnapshot.getKey());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }


    private void getUserKeys(final String conversationsKey) {
        final ArrayList<UserCredentials> userList = new ArrayList<>();
        FirebaseUtils.getDatabaseReference().child(Constant.conversations + "/" + conversationsKey).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot parentSnap) {
                        final ConversationsGroup conversationsGroup = parentSnap.child("group").getValue(ConversationsGroup.class);
                        for (DataSnapshot ds : parentSnap.child("users").getChildren()) {
                            if (ds.getKey().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
                                FirebaseUtils.getDatabaseReference().child(Constant.conversations + "/" + conversationsKey + "/" + Constant.users).
                                        addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                                getUserInfo(dataSnapshot.getKey(), conversationsKey, userList, parentSnap.child("users").getChildrenCount(), conversationsGroup);
                                            }

                                            @Override
                                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                            }

                                            @Override
                                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                                for (int i = 0; i < userList.size(); i++) {
                                                    if (userList.get(i).getKey().equalsIgnoreCase(dataSnapshot.getKey())) {
                                                        if (userList.get(i).getKey().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
                                                            userCredentialsMap.remove(conversationsKey);
                                                            lastMes.remove(conversationsKey);
                                                            conversationsGroupMap.remove(conversationsKey);
                                                            recycleViewConversations.notifyDataSetChanged();
                                                            break;
                                                        } else {
                                                            userList.remove(i);
                                                            userCredentialsMap.put(conversationsKey, userList);
                                                            recycleViewConversations.notifyDataSetChanged();
                                                            break;
                                                        }

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                break;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }


    private void getUserInfo(final String userKey, final String conversationsKey, final ArrayList<UserCredentials> userList, final long childrenCount, final ConversationsGroup conversationsGroup) {
        FirebaseUtils.getDatabaseReference().child(Constant.users + "/" + userKey + "/" + Constant.credentials).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserCredentials userCredentialsSnapshot = dataSnapshot.getValue(UserCredentials.class);
                        userCredentialsSnapshot.setKey(userKey);
                        userCredentialsSnapshot.setProfilePicLink(userCredentialsSnapshot.getProfilePicLink());
                        userCredentialsSnapshot.setName(userCredentialsSnapshot.getName());
                        userCredentialsSnapshot.setEmail(userCredentialsSnapshot.getEmail());
                        userList.add(userCredentialsSnapshot);
                        Log.e(conversationsKey,""+userKey);

                        userCredentialsMap.put(conversationsKey, userList);

                        if (userList.size() == childrenCount) {
                        //    Log.e("userList",""+userList.size());
                            conversationsGroupMap.put(conversationsKey, conversationsGroup);
                            getLastMessage(conversationsKey);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void getLastMessage(final String conversationsKey) {
        Query lastQuery = FirebaseUtils.getDatabaseReference().child(Constant.users + "/" + user.getFirebase_auth_user_id() + "/" + Constant.conversations + "/" + conversationsKey).orderByKey().limitToLast(1);
        lastQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserConversations data = dataSnapshot.getValue(UserConversations.class);
                lastMes.put(conversationsKey, data);
                //if (userCredentialsMap.size() > 0) {
              /*  for (int i = 0;i<userCredentialsMap.size();i++ ){
                    Log.e("getChildrenCount2", "" + userCredentialsMap.get(userCredentialsMap.keySet().toArray()[i]).size());

                }*/
               recycleViewConversations.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setEnabled(false);
                //  }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                UserConversations data = dataSnapshot.getValue(UserConversations.class);
                lastMes.put(conversationsKey, data);
                if (userCredentialsMap.size() > 0) {
                    recycleViewConversations.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                UserConversations data = dataSnapshot.getValue(UserConversations.class);
                lastMes.put(conversationsKey, data);
                if (userCredentialsMap.size() > 0) {
                    recycleViewConversations.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sortList() {
       /* Collections.sort(lastMes, new Comparator<UserConversations>() {
            @Override
            public int compare(UserConversations o1, UserConversations o2) {
                return (int) o1.getTimestamp()-(int)(o2.getTimestamp());
            }
        });*/
    }
}
 /*private void getUserKeys(final String conversationsKey) {
        final ArrayList<UserCredentials> userList = new ArrayList<>();
        FirebaseUtils.getDatabaseReference().child(Constant.conversations + "/" + conversationsKey + "/" + Constant.users).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot parentSnap) {
                        for (DataSnapshot ds : parentSnap.getChildren()) {
                            if (ds.getKey().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
                                FirebaseUtils.getDatabaseReference().child(Constant.conversations + "/" + conversationsKey + "/" + Constant.users).
                                        addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                //    if (!dataSnapshot.getKey().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
                                                getUserInfo(dataSnapshot.getKey(), conversationsKey, userList, parentSnap.getChildrenCount());
                                                //   }

                                            }

                                            @Override
                                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                            }

                                            @Override
                                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                                for (int i = 0; i < userList.size(); i++) {
                                                    if (userList.get(i).getKey().equalsIgnoreCase(dataSnapshot.getKey())) {
                                                        if (userList.get(i).getKey().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
                                                            userCredentialsMap.remove(conversationsKey);
                                                            lastMes.remove(conversationsKey);
                                                            recycleViewConversations.notifyDataSetChanged();
                                                            break;
                                                        } else {
                                                            userList.remove(i);
                                                            userCredentialsMap.put(conversationsKey, userList);
                                                            recycleViewConversations.notifyDataSetChanged();
                                                            break;
                                                        }

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                break;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
      *//*  FirebaseUtils.getDatabaseReference().child(Constant.conversations + "/" + conversationsKey + "/" + Constant.users).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //  Log.e("getChildrenCount", "" + dataSnapshot.getChildrenCount());
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (!ds.getKey().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
                                Log.e("" + conversationsKey, "" + ds.getKey());
                                for (int i = 0; i < userList.size(); i++) {
                                    if (userList.get(i).getKey().equalsIgnoreCase(ds.getKey())) {
                                        return;
                                    }else {

                                    }
                                }
                                getUserInfo(ds.getKey(), conversationsKey, userList, dataSnapshot.getChildrenCount() - 1);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*//*

    }*/
/*
 private void getUserKeys2(final String conversationsKey) {
     final ArrayList<UserCredentials> userList = new ArrayList<>();
     FirebaseUtils.getDatabaseReference().child(Constant.conversations + "/" + conversationsKey).
             addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(final DataSnapshot parentSnap) {
                     final ConversationsGroup conversationsGroup = parentSnap.child("group").getValue(ConversationsGroup.class);
                     for (DataSnapshot ds : parentSnap.child("users").getChildren()) {
                         if (ds.getKey().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
                             Log.e("rrrrrrrr",""+ds.getKey());
                             FirebaseUtils.getDatabaseReference().child(Constant.conversations + "/" + conversationsKey + "/" + Constant.users).
                                     addChildEventListener(new ChildEventListener() {
                                         @Override
                                         public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                             getUserInfo(dataSnapshot.getKey(), conversationsKey, userList, parentSnap.child("users").getChildrenCount(), conversationsGroup);
                                         }

                                         @Override
                                         public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                         }

                                         @Override
                                         public void onChildRemoved(DataSnapshot dataSnapshot) {
                                             for (int i = 0; i < userList.size(); i++) {
                                                 if (userList.get(i).getKey().equalsIgnoreCase(dataSnapshot.getKey())) {
                                                     if (userList.get(i).getKey().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
                                                         userCredentialsMap.remove(conversationsKey);
                                                         lastMes.remove(conversationsKey);
                                                         conversationsGroupMap.remove(conversationsKey);
                                                         recycleViewConversations.notifyDataSetChanged();
                                                         break;
                                                     } else {
                                                         userList.remove(i);
                                                         userCredentialsMap.put(conversationsKey, userList);
                                                         recycleViewConversations.notifyDataSetChanged();
                                                         break;
                                                     }

                                                 }
                                             }
                                         }

                                         @Override
                                         public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                         }

                                         @Override
                                         public void onCancelled(DatabaseError databaseError) {

                                         }
                                     });
                             break;
                         }
                     }

                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });


 }*/
