package com.oxygen.yallagoom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.oxygen.yallagoom.fragment.EventFragment;
import com.oxygen.yallagoom.fragment.GiftsFragment;
import com.oxygen.yallagoom.fragment.ScoresFragment;
import com.oxygen.yallagoom.fragment.SettingsFragment;
import com.oxygen.yallagoom.fragment.TextFragment;
import com.oxygen.yallagoom.fragment.TicketsFragment;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud Sabbah on 2/1/2018.
 */

public class HomeTapAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private AlphaTabsIndicator alphaTabsIndicator;
    private List<Fragment> fragments = new ArrayList<>();
    public HomeTapAdapter(FragmentManager fm, AlphaTabsIndicator alphaTabsIndicator) {
        super(fm);
        fragments.add(new EventFragment());
        fragments.add(new ScoresFragment());
        fragments.add(new TicketsFragment());
        fragments.add(new GiftsFragment());
        fragments.add(new SettingsFragment());

        this.alphaTabsIndicator = alphaTabsIndicator;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
       /* if (0 == position) {
            alphaTabsIndicator.getTabView(0).showNumber(alphaTabsIndicator.getTabView(0).getBadgeNumber() - 1);
        } else if (2 == position) {
            alphaTabsIndicator.getCurrentItemView().removeShow();
        } else if (3 == position) {
            alphaTabsIndicator.removeAllBadge();
        }*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
