package com.oxygen.yallagoom.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.HomeActivity;
import com.oxygen.yallagoom.activity.MySportsActivity;
import com.oxygen.yallagoom.api.UpdateUserApiAsyncTask;
import com.oxygen.yallagoom.app.MainApplication;
import com.oxygen.yallagoom.entity.FirebaseUser;
import com.oxygen.yallagoom.interfaces.StringResultCallback;

import java.util.HashMap;

/**
 * Created by Mahmoud Sabbah on 4/12/2018.
 */

public class FirebaseUtils {
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public static void CreateFirebaseUser(final String email, String password, final String name, final String pic, final Activity activity, final KProgressHUD progress, final int flag) {
        FirebaseUtils.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    progress.dismiss();
                    ToolUtils.viewToast(activity, task.getException().getMessage());
                } else {
                    FirebaseUtils.initNewUserInfo(activity, email, pic, name, task.getResult().getUser().getUid(), progress,flag);
                }
            }
        });

    }

    public static void initNewUserInfo(final Activity activity, String email, String pic, String name, final String UID, final KProgressHUD progress, final int flag) {
        FirebaseUser newUser = new FirebaseUser();
        newUser.setEmail(email);
        newUser.setProfilePicLink(pic);
        newUser.setName(name);

        FirebaseDatabase.getInstance().getReference().child(Constant.users + "/" + UID + "/" + Constant.credentials).setValue(newUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                progress.dismiss();
                if (databaseError == null) {
                    HashMap<String, String> hashMapsData = new HashMap<>();
                    hashMapsData.put("firebase_auth_user_id", UID);
                    UpdateUserApiAsyncTask updateUserApiAsyncTask = new UpdateUserApiAsyncTask(activity, hashMapsData, new StringResultCallback() {
                        @Override
                        public void processFinish(String result, KProgressHUD progress) {
                            MainApplication.verification_check = true;
                            SharedPreferences.Editor sharedloginCheck = ToolUtils.setSharedPrefernce(activity, Constant.loginCheck);
                            sharedloginCheck.putBoolean(Constant.verification_check, true).apply();
                            SharedPreferences.Editor shared = ToolUtils.setSharedPrefernce(activity, Constant.userData);
                            shared.putString(Constant.allUserData, result);
                            shared.apply();
                            //  ToolUtils.viewToast(activity, activity.getString(R.string.update_status));
                            if (flag==0){
                                Intent intent = new Intent(activity, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intent);
                                activity.finish();
                            }else {
                                Intent intent = new Intent(activity, MySportsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intent);
                                activity.finish();
                            }

                        }
                    });
                    updateUserApiAsyncTask.execute();
                } else {
                    ToolUtils.viewToast(activity, databaseError.getMessage());

                }
            }
        });
    }

    public static DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();

    }

    public static void loginUserToFirebase(final Context mContext, String email, String password, final KProgressHUD progress) {
        FirebaseUtils.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress.dismiss();
                if (task.isSuccessful()) {
                    MainApplication.verification_check = true;
                    SharedPreferences.Editor shared = ToolUtils.setSharedPrefernce(mContext, Constant.loginCheck);
                    shared.putBoolean(Constant.verification_check, true).apply();

                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                } else {
                    ToolUtils.viewToast(mContext, mContext.getString(R.string.try_again));
                }

            }
        });
    }
}
