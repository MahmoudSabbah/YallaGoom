package com.yallagoom.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tumblr.permissme.PermissMe;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewAddFriendsMobile;
import com.yallagoom.api.SearchFriendsAsyncTask;
import com.yallagoom.api.SearchFriendsDefaultAsyncTask;
import com.yallagoom.entity.Player;
import com.yallagoom.entity.Sport;
import com.yallagoom.interfaces.SearchFriendsCallback;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddFriendsUsingMobileActivity extends AppCompatActivity {

    private static final int PICK_CONTACT = 120;
    private LinearLayout parent;
    private TextView left_text;
    private TextView right_text;
    private TextView header_title;
    private View header_view;
    private List<HashMap<String, String>> contactData;
    private IndexFastScrollRecyclerView listView;
    private EditText search_bt;
    private RecycleViewAddFriendsMobile recycleViewAddFriendsMobile;
    private List<String> mobiles;
    private List<String> id;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_add_using_mobile);
        parent = (LinearLayout) findViewById(R.id.parent);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ToolUtils.hideStatus(AddFriendsUsingMobileActivity.this);
        ToolUtils.setLightStatusBar(parent, AddFriendsUsingMobileActivity.this);
        mobiles = new ArrayList<>();
        id = new ArrayList<>();
        left_text = (TextView) findViewById(R.id.left_text);
        header_view = (View) findViewById(R.id.header_view);
        right_text = (TextView) findViewById(R.id.right_text);
        header_title = (TextView) findViewById(R.id.name_header);
        header_title.setText(R.string.find_friend_from_mobile);
        right_text.setText(getString(R.string.ok));
        left_text.setText(R.string.friends);
        header_view.setVisibility(View.GONE);
        search_bt = (EditText) findViewById(R.id.search_bt);
        listView = (IndexFastScrollRecyclerView) findViewById(R.id.list);
        listView.setIndexBarTransparentValue(0);
        // listView.setIndexBarColor(ContextCompat.getColor(AddFriendsUsingMobileActivity.this, R.color.transparent));
        listView.setIndexBarTextColor("#6b6a6a");
        contactData = new ArrayList<>();
        PermissMe.with(AddFriendsUsingMobileActivity.this)
                .setRequiredPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .setRequiredPermissions(Manifest.permission.SEND_SMS)
                .listener(new PermissMe.PermissionListener() {
                    @Override
                    public void onSuccess() {
                        //  getContact();
                        Log.e("getContact", "getContact");
                        refreshItems();
                    }

                    @Override
                    public void onRequiredPermissionDenied(String[] deniedPermissions, boolean[] isAutoDenied) {
                        finish();
                    }

                    @Override
                    public void onOptionalPermissionDenied(String[] deniedPermissions, boolean[] isAutoDenied) {

                    }
                })
                .verifyPermissions();

        search_bt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                List<HashMap<String, String>> listData = new ArrayList<>();
                if (contactData.size() > 0) {
                    for (int i = 0; i < contactData.size(); i++) {
                        if (ToolUtils.hexChecker(cs.toString().toUpperCase(), contactData.get(i).get("name").toUpperCase())) {
                            // list.add(list.get(i));
                            listData.add(contactData.get(i));

                        }

                    }
                    recycleViewAddFriendsMobile.updateList(listData);
                    recycleViewAddFriendsMobile.notifyDataSetChanged();
                    // listView.setAdapter(recycleViewAddFriendsMobile);
                }


            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

      /*  RecycleViewAddFriendsMobile listAdapterfriends=new RecycleViewAddFriendsMobile(strings);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(listAdapterfriends);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });
        recyclerView.addItemDecoration(headersDecor);
        listView.setAdapter(listAdapterfriends);*/

    }

    private void getContact() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.e("cNumber", "" + phoneNumber + "  " + name);
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("name", name);
            stringStringHashMap.put("phoneNumber", phoneNumber);
            contactData.add(stringStringHashMap);
        }
        phones.close();
        recycleViewAddFriendsMobile = new RecycleViewAddFriendsMobile(contactData, mobiles,id);
        listView.setAdapter(recycleViewAddFriendsMobile);
    }

    public void Back(View view) {
        finish();
    }

    private void refreshItems() {
        SearchFriendsDefaultAsyncTask searchFriendsDefaultAsyncTask = new SearchFriendsDefaultAsyncTask(AddFriendsUsingMobileActivity.this, new SearchFriendsCallback() {
            @Override
            public void finishProcess(Player player) {
                for (int i = 0; i < player.getData().size(); i++) {
                    if (player.getData().get(i).getMobile() != null) {
                        mobiles.add(player.getData().get(i).getMobile());
                        id.add(player.getData().get(i).getId()+"");
                    }
                }
                getContact();
            }
        });
        searchFriendsDefaultAsyncTask.execute();


    }


}
