package com.yallagoom.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.yallagoom.R;
import com.yallagoom.adapter.HomeTapAdapter;
import com.yallagoom.fragment.EventFragment;
import com.yallagoom.fragment.ScoresFragment;
import com.yallagoom.fragment.TicketsFragment;
import com.yallagoom.fragment.eventTapFragment.FindPlayerFragment;
import com.yallagoom.fragment.eventTapFragment.HomeFragment;
import com.yallagoom.fragment.eventTapFragment.HomeListFragment;
import com.yallagoom.fragment.eventTapFragment.HomeMapFragment;
import com.yallagoom.utils.ToolUtils;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

public class HomeActivity extends AppCompatActivity {
    private AlphaTabsIndicator alphaTabsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ToolUtils.hideStatus(HomeActivity.this);
        ViewPager mViewPger = (ViewPager) findViewById(R.id.mViewPager);
        alphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        HomeTapAdapter mainAdapter = new HomeTapAdapter(getSupportFragmentManager(), alphaTabsIndicator);
        mViewPger.setAdapter(mainAdapter);
        mViewPger.setOffscreenPageLimit(5);
        mViewPger.addOnPageChangeListener(mainAdapter);

        alphaTabsIndicator.setViewPager(mViewPger);

     /*   alphaTabsIndicator.getTabView(0);//.showNumber(6)
        alphaTabsIndicator.getTabView(1);
        alphaTabsIndicator.getTabView(2);
        alphaTabsIndicator.getTabView(3);
        alphaTabsIndicator.getTabView(4);*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
