package com.yallagoom.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CircularImageView;
import com.yallagoom.widget.TouchImageView;

public class FullScreenImageActivity extends AppCompatActivity {

    private TouchImageView mImageView;
    private CircularImageView ivUser;
    private TextView tvUser;
    private ProgressDialog progressDialog;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
         imageLoader=ImageLoader.getInstance();
        bindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setValues();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.gc();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void bindViews(){
        progressDialog = new ProgressDialog(this);
        mImageView = (TouchImageView) findViewById(R.id.imageView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivUser = (CircularImageView)toolbar.findViewById(R.id.avatar);
        tvUser = (TextView)toolbar.findViewById(R.id.title);
    }

    private void setValues(){
        String nameUser,urlPhotoUser,urlPhotoClick;
        nameUser = getIntent().getStringExtra("nameUser");
        urlPhotoUser = getIntent().getStringExtra("urlPhotoUser");
        urlPhotoClick = getIntent().getStringExtra("urlPhotoClick");
        tvUser.setText(nameUser); // Name
        Log.e("urlPhotoUser",""+urlPhotoUser);
        ToolUtils.setImage(urlPhotoClick,mImageView,imageLoader);
        ToolUtils.setImage(urlPhotoUser,ivUser,imageLoader);
    }

}
