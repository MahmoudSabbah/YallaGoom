package com.yallagoom.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devlomi.record_view.OnRecordClickListener;
import com.devlomi.record_view.OnRecordListener;
import com.devlomi.record_view.RecordButton;
import com.devlomi.record_view.RecordView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.adapter.event.chat.RecycleViewChatAdapter;
import com.yallagoom.entity.Chat.UserConversations;
import com.yallagoom.entity.Chat.UserCredentials;
import com.yallagoom.entity.User;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.FirebaseUtils;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CircularImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import id.zelory.compressor.Compressor;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ADD_MEMBER_CODE = 202;
    private static final int IMAGE_GALLERY_REQUEST = 205;
    private View contentRoot;
    private EmojiconEditText edMessage;
    private ImageView btSendMessage;
    private ImageView btEmoji;
    private EmojIconActions emojIcon;
    private RecyclerView rvListMessage;
    private ArrayList<UserCredentials> listOfUser;
    private ArrayList<UserConversations> userConversationsArrayList;
    private String conKey;
    private User user;
    private RecycleViewChatAdapter recycleViewChatAdapter;
    private LinearLayoutManager linearLayoutManager;
    private TextView users_name;
    private CircularImageView friend_image;
    private ImageLoader imageLoader;
    private String ownerID;
    private TextView add_member;
    private TextView select_image;
    private StorageReference storageRef;
    private String TAG = getPackageName();
    private RecordView recordView;
    private RecordButton recordButton;
    private RelativeLayout msg_lay;
    private MediaRecorder mRecorder = null;
    private static String mFileName = null;
    private KProgressHUD progress;
    private Parcelable recyclerViewState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindViews();
    }

    private void bindViews() {
        contentRoot = findViewById(R.id.contentRoot);
        //     ToolUtils.hideStatus(ChatActivity.this);
        ToolUtils.setLightStatusBar(contentRoot, ChatActivity.this);
        createProgress();

        user = new Gson().fromJson(ToolUtils.getSharedPreferences(ChatActivity.this, Constant.userData).getString(Constant.allUserData, null), User.class);
        listOfUser = (ArrayList<UserCredentials>) getIntent().getSerializableExtra("listOfUser");
        conKey = getIntent().getExtras().getString("key");
        imageLoader = ImageLoader.getInstance();
        storageRef = FirebaseUtils.firebaseStorage.getReferenceFromUrl(Constant.URL_STORAGE_REFERENCE).child(Constant.FOLDER_STORAGE_IMG);

        userConversationsArrayList = new ArrayList<>();
        edMessage = (EmojiconEditText) findViewById(R.id.editTextMessage);
        users_name = (TextView) findViewById(R.id.users_name);
        add_member = (TextView) findViewById(R.id.add_member);
        select_image = (TextView) findViewById(R.id.select_image);
        select_image.setOnClickListener(this);
        users_name.setText(getIntent().getExtras().getString("title"));

        friend_image = (CircularImageView) findViewById(R.id.friend_image);
        if (getIntent().hasExtra("OwnerID")) {
            ownerID = getIntent().getExtras().getString("OwnerID");
            friend_image.setImageResource(R.drawable.folder_group_icon);
            if (ownerID.equalsIgnoreCase(user.getFirebase_auth_user_id()))
                add_member.setVisibility(View.VISIBLE);
        } else {
            ToolUtils.setImage(listOfUser.get(0).getProfilePicLink(), friend_image, imageLoader);
        }
        msg_lay = (RelativeLayout) findViewById(R.id.msg_lay);
        btSendMessage = (ImageView) findViewById(R.id.buttonMessage);
        btSendMessage.setOnClickListener(this);
        btEmoji = (ImageView) findViewById(R.id.buttonEmoji);
        emojIcon = new EmojIconActions(this, contentRoot, edMessage, btEmoji);
        emojIcon.ShowEmojIcon();
        rvListMessage = (RecyclerView) findViewById(R.id.messageRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListMessage.setLayoutManager(linearLayoutManager);
        recyclerViewState = rvListMessage.getLayoutManager().onSaveInstanceState();

        rvListMessage.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        recycleViewChatAdapter = new RecycleViewChatAdapter(userConversationsArrayList, user, listOfUser);
        rvListMessage.setAdapter(recycleViewChatAdapter);
        getChatData();
        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, AddFriendChatActivity.class);
                intent.putExtra("AddFriend", "AddFriend");
                intent.putExtra("ConversationKey", conKey);
                startActivityForResult(intent, ADD_MEMBER_CODE);
            }
        });
        recordView = (RecordView) findViewById(R.id.record_view);
        recordButton = (RecordButton) findViewById(R.id.record_button);
        //IMPORTANT
        recordButton.setRecordView(recordView);
        recordButton.setListenForRecord(true);
        recordButton.setOnRecordClickListener(new OnRecordClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("RecordButton", "RECORD BUTTON CLICKED");
                sendMessageFirebase();
            }
        });

        recordView.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onStart() {
                Log.e("RecordView", "onStart");
                msg_lay.setVisibility(View.INVISIBLE);
                startRecording();

            }

            @Override
            public void onCancel() {
                Log.e("RecordView", "onCancel");
                msg_lay.setVisibility(View.VISIBLE);
                stopRecording(0);

            }

            @Override
            public void onFinish(long recordTime) {
                msg_lay.setVisibility(View.VISIBLE);
                stopRecording(recordTime);
/*
                String time = getHumanTimeText(recordTime);
                Toast.makeText(MainActivity.this, "onFinishRecord - Recorded Time is: " + time, Toast.LENGTH_SHORT).show();
                */
                Log.e("RecordView", "onFinish");

            }

            @Override
            public void onLessThanSecond() {
                msg_lay.setVisibility(View.VISIBLE);
                stopRecording(0);
                Log.e("RecordView", "onLessThanSecond");
            }
        });
        edMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edMessage.getText().toString().equalsIgnoreCase("")) {
                    recordButton.setListenForRecord(true);
                    recordButton.setImageResource(R.drawable.ic_mic_white);

                } else {
                    recordButton.setListenForRecord(false);
                    recordButton.setImageResource(R.drawable.ic_send_button);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonMessage:
                sendMessageFirebase();
                break;
            case R.id.select_image:
                photoGalleryIntent();
                break;
        }
    }

    public void Back(View view) {
        finish();
    }

    private void getChatData() {
        FirebaseUtils.getDatabaseReference().child(Constant.users + "/" + user.getFirebase_auth_user_id() + "/" + Constant.conversations + "/" + conKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserConversations userConversations = dataSnapshot.getValue(UserConversations.class);
                userConversations.setKey(dataSnapshot.getKey());
                userConversationsArrayList.add(userConversations);
                recycleViewChatAdapter.notifyDataSetChanged();
                linearLayoutManager.scrollToPosition(userConversationsArrayList.size() - 1);
                if (!userConversations.isRead()) {
                    Map<String, Object> stringStringMap = new HashMap<>();
                    stringStringMap.put("read", true);
                    FirebaseUtils.getDatabaseReference().child(Constant.users + "/" + user.getFirebase_auth_user_id() + "/" + Constant.conversations + "/" + conKey + "/" + dataSnapshot.getKey()).updateChildren(stringStringMap);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                UserConversations userConversations = dataSnapshot.getValue(UserConversations.class);
                for (int i = 0; i < userConversationsArrayList.size(); i++) {
                    if (userConversationsArrayList.get(i).getKey() != null && userConversationsArrayList.get(i).getKey().equalsIgnoreCase(dataSnapshot.getKey())) {
                        userConversationsArrayList.set(i, userConversations);

                    }
                }
                recycleViewChatAdapter.notifyDataSetChanged();
                linearLayoutManager.scrollToPosition(userConversationsArrayList.size() - 1);


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (int i = 0; i < userConversationsArrayList.size(); i++) {
                    if (userConversationsArrayList.get(i).getKey().equalsIgnoreCase(dataSnapshot.getKey())) {
                        userConversationsArrayList.remove(userConversationsArrayList.get(i));
                        recycleViewChatAdapter.notifyDataSetChanged();
                        linearLayoutManager.scrollToPosition(userConversationsArrayList.size() - 1);
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
    }

    private void sendMessageFirebase() {

        long time = System.currentTimeMillis() / 1000;
        String pushKey = FirebaseUtils.getDatabaseReference().child(Constant.users).child(user.getFirebase_auth_user_id() + "/" + Constant.conversations + "/" + conKey).push().getKey();

        Map<String, Object> stringObjectMap = new HashMap<>();
        UserConversations userConversations = new UserConversations();
        userConversations.setContent(edMessage.getText().toString());
        userConversations.setFromID(user.getFirebase_auth_user_id());
        userConversations.setRead(true);
        userConversations.setTimestamp(time);
        userConversations.setToID("none");
        userConversations.setType("text");
        stringObjectMap.put(user.getFirebase_auth_user_id() + "/" + Constant.conversations + "/" + conKey + "/" + pushKey, userConversations);
        userConversations.setRead(false);
        for (int i = 0; i < listOfUser.size(); i++) {
            stringObjectMap.put(listOfUser.get(i).getKey() + "/" + Constant.conversations + "/" + conKey + "/" + pushKey, userConversations);

        }
        FirebaseUtils.getDatabaseReference().child(Constant.users).updateChildren(stringObjectMap);
        edMessage.setText(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_MEMBER_CODE:
                if (resultCode == RESULT_OK) {
                    ArrayList<UserCredentials> listUser = (ArrayList<UserCredentials>) data.getSerializableExtra("add_member_result");
                    if (listUser != null) {
                        Log.e("listUser", "" + listUser.size());
                        listOfUser.addAll(listUser);
                    }
                    //
                }
                break;
            case IMAGE_GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        try {
                            File actualImage = ToolUtils.from(this, data.getData());
                            //   Bitmap compressedImageBitmap = new Compressor(ChatActivity.this).compressToBitmap(actualImage);
                            long time = System.currentTimeMillis() / 1000;

                            File compressedImageFile = new Compressor(ChatActivity.this).compressToFile(actualImage);
                            progress.show();
                            sendFileFirebase(Uri.fromFile(compressedImageFile), "photo", "IMAGE", -1, time);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        //URI IS NULL
                    }
                }
                break;
        }
    }

    private void photoGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");///    <string name="select_picture_title">Get Photo From</string>

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.get_phone)), IMAGE_GALLERY_REQUEST);
    }

    private void sendFileFirebase(final Uri file, final String type, String fileName, final long recordTime, final long timestamp) {

        if (FirebaseUtils.firebaseStorage != null) {
            final String name = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
            StorageReference imageGalleryRef = storageRef.child(name + "_" + fileName);
            UploadTask uploadTask = imageGalleryRef.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure sendFileFirebase " + e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    //  long time = System.currentTimeMillis() / 1000;
                    String pushKey = FirebaseUtils.getDatabaseReference().child(Constant.users).child(user.getFirebase_auth_user_id() + "/" + Constant.conversations + "/" + conKey).push().getKey();

                    Map<String, Object> stringObjectMap = new HashMap<>();
                    UserConversations userConversations = new UserConversations();
                    userConversations.setContent(downloadUrl.toString());
                    userConversations.setFromID(user.getFirebase_auth_user_id());
                    //  userConversations.setRead(true);
                    userConversations.setTimestamp(timestamp);
                    userConversations.setToID("none");
                    userConversations.setType(type);
                    if (type.equalsIgnoreCase("audio")) {
                        userConversations.setDuration(recordTime);
                    }
                    // stringObjectMap.put(user.getFirebase_auth_user_id() + "/" + Constant.conversations + "/" + conKey + "/" + pushKey, userConversations);
                    userConversations.setRead(false);
                    for (int i = 0; i < listOfUser.size(); i++) {
                        if (listOfUser.get(i).getKey().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
                            Log.e("listOfUser", "" + listOfUser.get(i).getKey());

                            userConversations.setRead(true);
                            stringObjectMap.put(listOfUser.get(i).getKey() + "/" + Constant.conversations + "/" + conKey + "/" + pushKey, userConversations);
                        } else {
                            userConversations.setRead(false);
                            stringObjectMap.put(listOfUser.get(i).getKey() + "/" + Constant.conversations + "/" + conKey + "/" + pushKey, userConversations);
                        }

                    }
                    FirebaseUtils.getDatabaseReference().child(Constant.users).updateChildren(stringObjectMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                //    if (type.equalsIgnoreCase("audio") && progress != null) {
                                progress.dismiss();
                                //  }
                            }
                        }
                    });

                }
            });
        } else {
            //IS NULL
        }

    }

    private void startRecording() {
        long time = System.currentTimeMillis() / 1000;
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "YallaGoom");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            mFileName = Environment.getExternalStorageDirectory().toString() + "/YallaGoom";
            mFileName += "/AUD_" + time + ".3gp";

            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(mFileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (Exception e) {
                Log.e("error_mes", "" + e.getMessage());
            }

        } else {
            // Do something else on failure
        }


    }

    private void stopRecording(long recordTime) {
        try {
            if (mRecorder != null) {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;


                File directory = new File(mFileName);
                if (recordTime > 0) {
                    if (progress != null) {
                        progress.show();
                    }
                    long time = System.currentTimeMillis() / 1000;
                    sendFileFirebase(Uri.fromFile(directory), "audio", "AUD", recordTime, time);
                }
            }
        } catch (Exception e) {
            Log.e("error_mes", "" + e.getMessage());
        }

    }

    private void createProgress() {
        progress = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.please_wait))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
        ;
    }

}
