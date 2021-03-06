package com.oxygen.yallagoom.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.HomeTapAdapter;
import com.oxygen.yallagoom.fragment.EventFragment;
import com.oxygen.yallagoom.fragment.GiftsFragment;
import com.oxygen.yallagoom.fragment.ScoresFragment;
import com.oxygen.yallagoom.fragment.SettingsFragment;
import com.oxygen.yallagoom.fragment.TicketsFragment;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import cn.jzvd.JZVideoPlayer;

public class HomeActivity extends AppCompatActivity {
    private AlphaTabsIndicator alphaTabsIndicator;
    private FrameLayout body_home;
    private Fragment fragment;
    private int idFragment = 1;
    private int preFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ToolUtils.hideStatus(HomeActivity.this);

        // ViewPager mViewPger = (ViewPager) findViewById(R.id.mViewPager);
        alphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        HomeTapAdapter mainAdapter = new HomeTapAdapter(getSupportFragmentManager(), alphaTabsIndicator);
        body_home = (FrameLayout) findViewById(R.id.body_home);

     /*   mViewPger.setAdapter(mainAdapter);
        mViewPger.setOffscreenPageLimit(0);
        mViewPger.addOnPageChangeListener(mainAdapter);

        alphaTabsIndicator.setViewPager(mViewPger);
*/
     /*   alphaTabsIndicator.getTabView(0);//.showNumber(6)
        alphaTabsIndicator.getTabView(1);
        alphaTabsIndicator.getTabView(2);
        alphaTabsIndicator.getTabView(3);
        alphaTabsIndicator.getTabView(4);*/
        alphaTabsIndicator.setOnTabChangedListner(new OnTabChangedListner() {
            @Override
            public void onTabSelected(int tabNum) {
                switch (tabNum) {
                    case 0:
                        idFragment = 1;
                        fragment = new EventFragment();
                        break;
                    case 1:
                        idFragment = 2;
                        fragment = new ScoresFragment();
                        break;
                    case 2:
                        idFragment = 3;
                        fragment = new TicketsFragment();
                        break;
                    case 3:
                        idFragment = 4;
                        fragment = new GiftsFragment();
                        break;
                    case 4:
                        idFragment = 5;
                        fragment = new SettingsFragment();
                        break;
                }
                changeFragment(fragment);

            }
        });
        if (getIntent().hasExtra("ActionNotification")) {
            fragment = new EventFragment();
        } else {
            fragment = new EventFragment();
        }
        changeFragment(fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", "onActivityResult");
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    /*  @Override
      public void onBackPressed() {

          int count = getFragmentManager().getBackStackEntryCount();

          if (count == 0) {
              super.onBackPressed();
              //additional code
          } else {
              getFragmentManager().popBackStack();
          }

      }*/
    private void changeFragment(Fragment fragment) {
        if (preFragment != idFragment) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.body_home, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
            preFragment = idFragment;
        }
    }

    @Override
    public void onBackPressed() {
        if (idFragment != 1) {
            alphaTabsIndicator.setTabCurrenItem(0);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            if (JZVideoPlayer.backPress()) {
                return;
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    public void Login(View view) {
        LoginActivity.startLoginActivity(this);
        finish();
    }
}
