package com.oxygen.yallagoom.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tumblr.permissme.PermissMe;
import com.yalantis.ucrop.UCrop;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.LoginActivity;
import com.oxygen.yallagoom.activity.SettingsHomeClickHobbiesActivity;
import com.oxygen.yallagoom.activity.SettingsHomeClickNewsSettingsActivity;
import com.oxygen.yallagoom.activity.SettingsHomeClickPrivacyTermsActivity;
import com.oxygen.yallagoom.activity.SettingsHomeClickProfileActivity;
import com.oxygen.yallagoom.activity.SettingsHomeClickStatusActivity;
import com.oxygen.yallagoom.api.LogOutApiAsyncTask;
import com.oxygen.yallagoom.api.UpdateUseImagerApiAsyncTask;
import com.oxygen.yallagoom.entity.User;
import com.oxygen.yallagoom.interfaces.ClickPopUpCallback;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CameraGalleryChoicePopup;
import com.oxygen.yallagoom.widget.CircularImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mahmoud Sabbah on 2/1/2018.
 */

public class SettingsFragment extends Fragment {


    private static final int RESULT_LOAD_IMAGE = 202;
    private static final int REQUEST_IMAGE_CAPTURE = 203;
    private TextView login_bt;
    private TextView header_title;
    private RelativeLayout profile_layout;
    private RelativeLayout status_layout;
    private RelativeLayout new_settings;
    private CircularImageView user_image;
    private TextView user_name;
    private TextView status;
    private TextView arrow_profile;
    private View status_layout_view;
    private RelativeLayout hobbies_layout;
    private View hobbies_layout_view;
    private RelativeLayout notification_layout;
    private TextView sign_in_out;
    private TextView camera;
    private User user;
    private ImageLoader imageLoader;
    private CameraGalleryChoicePopup photoPopup;
    private RelativeLayout sign_in_out_lay;
    private RelativeLayout terms_conditions;
    private RelativeLayout privacy_policy;
    private RelativeLayout _share;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        header_title = (TextView) getActivity().findViewById(R.id.header_title);
        header_title.setText(getString(R.string.settings));
        login_bt = (TextView) view.findViewById(R.id.login_bt);
        user_image = (CircularImageView) view.findViewById(R.id.user_image);
        user_name = (TextView) view.findViewById(R.id.user_name);
        status = (TextView) view.findViewById(R.id.status);
        sign_in_out = (TextView) view.findViewById(R.id.sign_in_out);
        arrow_profile = (TextView) view.findViewById(R.id.arrow);
        camera = (TextView) view.findViewById(R.id.camera);
        status_layout = (RelativeLayout) view.findViewById(R.id.status_layout);
        hobbies_layout = (RelativeLayout) view.findViewById(R.id.hobbies_layout);
        notification_layout = (RelativeLayout) view.findViewById(R.id.notification_layout);
        status_layout_view = (View) view.findViewById(R.id.status_layout_view);
        hobbies_layout_view = (View) view.findViewById(R.id.hobbies_layout_view);
        profile_layout = (RelativeLayout) view.findViewById(R.id.profile_layout);
        sign_in_out_lay = (RelativeLayout) view.findViewById(R.id.sign_in_out_lay);
        terms_conditions = (RelativeLayout) view.findViewById(R.id.terms_conditions);
        privacy_policy = (RelativeLayout) view.findViewById(R.id.privacy_policy);
        _share = (RelativeLayout) view.findViewById(R.id._share);

        if (ToolUtils.getSharedPreferences(SettingsFragment.this.getActivity(), Constant.userData).getString(Constant.allUserData, null) == null) {
            user_name.setText(getString(R.string.not_registered));
            arrow_profile.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
            camera.setVisibility(View.GONE);
            camera.setVisibility(View.GONE);
            status_layout.setVisibility(View.GONE);
            hobbies_layout.setVisibility(View.GONE);
            notification_layout.setVisibility(View.GONE);
            status_layout_view.setVisibility(View.GONE);
            hobbies_layout_view.setVisibility(View.GONE);
            sign_in_out.setText(getString(R.string.log_in_));
            sign_in_out_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SettingsFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        } else {
            user = new Gson().fromJson(ToolUtils.getSharedPreferences(SettingsFragment.this.getActivity(), Constant.userData).getString(Constant.allUserData, null), User.class);
            user_name.setText(user.getFirst_name() + " " + user.getLast_name());
            status.setText(user.getStatus());
            imageLoader = ImageLoader.getInstance();
            ToolUtils.setImage(Constant.imageUrl + user.getImg_url(), user_image, imageLoader);

            profile_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SettingsFragment.this.getContext(), SettingsHomeClickProfileActivity.class);
                    startActivity(intent);
                }
            });
            sign_in_out_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogOutApiAsyncTask logOutApiAsyncTask = new LogOutApiAsyncTask(SettingsFragment.this.getActivity());
                    logOutApiAsyncTask.execute();
                }
            });
        }


        status_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsFragment.this.getContext(), SettingsHomeClickStatusActivity.class);
                startActivity(intent);
            }
        });
        new_settings = (RelativeLayout) view.findViewById(R.id.new_settings);
        new_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsFragment.this.getContext(), SettingsHomeClickNewsSettingsActivity.class);
                startActivity(intent);
            }
        });
        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissMe.with(SettingsFragment.this)
                        .setRequiredPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setRequiredPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .listener(new PermissMe.PermissionListener() {
                            @Override
                            public void onSuccess() {
                                photoPopup = new CameraGalleryChoicePopup(SettingsFragment.this.getActivity(), getString(R.string.camera_gallery_choose), new ClickPopUpCallback() {
                                    @Override
                                    public void processFinish(final int check) {
                                        photoPopup.dismiss();
                                        if (check == 0) {
                                            dispatchTakePictureIntent();
                                        } else {
                                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                            photoPickerIntent.setType("image/*");
                                            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
                                        }
                                    }
                                });
                                photoPopup.showAtLocation(((Activity) SettingsFragment.this.getActivity()).findViewById(R.id.main_frame),
                                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                            }

                            @Override
                            public void onRequiredPermissionDenied(String[] deniedPermissions,
                                                                   boolean[] isAutoDenied) {

                            }

                            @Override
                            public void onOptionalPermissionDenied(String[] deniedPermissions,
                                                                   boolean[] isAutoDenied) {

                            }
                        })
                        .verifyPermissions();

            }
        });
        hobbies_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsFragment.this.getContext(), SettingsHomeClickHobbiesActivity.class);
                startActivity(intent);

            }
        });
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsFragment.this.getContext(), SettingsHomeClickPrivacyTermsActivity.class);
                intent.putExtra("type","privacy");
                startActivity(intent);
            }
        });
        terms_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsFragment.this.getContext(), SettingsHomeClickPrivacyTermsActivity.class);
                intent.putExtra("type","terms");
                startActivity(intent);
            }
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ToolUtils.getSharedPreferences(SettingsFragment.this.getActivity(), Constant.userData).getString(Constant.allUserData, null) != null) {
            user = new Gson().fromJson(ToolUtils.getSharedPreferences(SettingsFragment.this.getActivity(), Constant.userData).getString(Constant.allUserData, null), User.class);
            user_name.setText(user.getFirst_name() + " " + user.getLast_name());
            status.setText(user.getStatus());
            ToolUtils.setImage(Constant.imageUrl + user.getImg_url(), user_image, imageLoader);

        }
    }

    private void TakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            Fragment frag = this;
            frag.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    String destinationFileName = "crop";
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    UCrop.of(ToolUtils.getImageUri(SettingsFragment.this.getActivity(), imageBitmap)
                            , Uri.fromFile(new File(getActivity().getCacheDir(), destinationFileName)))
                            .start(SettingsFragment.this.getActivity());
                }
                break;
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    String destinationFileName = "crop";
                    UCrop.of(data.getData(), Uri.fromFile(new File(getActivity().getCacheDir(), destinationFileName)))
                            .withMaxResultSize(500, 500)
                            .start(SettingsFragment.this.getActivity());
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    final Uri resultUri = UCrop.getOutput(data);
                    try {
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(resultUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        UpdateUseImagerApiAsyncTask updateUseImagerApiAsyncTask = new UpdateUseImagerApiAsyncTask(SettingsFragment.this.getActivity(),
                                selectedImage, new StringResultCallback() {
                            @Override
                            public void processFinish(String result, KProgressHUD progress) {
                                user_image.setImageBitmap(selectedImage);

                            }
                        });
                        updateUseImagerApiAsyncTask.execute();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}

