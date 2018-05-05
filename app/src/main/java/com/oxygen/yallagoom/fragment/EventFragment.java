package com.oxygen.yallagoom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.fragment.myEventTapFragment.ChatFragment;
import com.oxygen.yallagoom.fragment.myEventTapFragment.FindEventFragment;
import com.oxygen.yallagoom.fragment.myEventTapFragment.FindPlayerFragment;
import com.oxygen.yallagoom.fragment.myEventTapFragment.FriendEventFragment;
import com.oxygen.yallagoom.fragment.myEventTapFragment.FriendFragment;
import com.oxygen.yallagoom.fragment.myEventTapFragment.GroupFragment;
import com.oxygen.yallagoom.fragment.myEventTapFragment.HomeFragment;
import com.oxygen.yallagoom.fragment.myEventTapFragment.MyEventFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud Sabbah on 2/1/2018.
 */

public class EventFragment extends Fragment {


    // private ViewPager viewPager;
    private TabLayout tabLayout;
    private FrameLayout body;
    Fragment fragment = null;
    private TextView header_title;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabevent_item, container, false);
        header_title = (TextView) getActivity().findViewById(R.id.header_title);
        header_title.setText(getString(R.string.my_event));

        body = (FrameLayout) view.findViewById(R.id.body);
        // viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        // viewPager.setOffscreenPageLimit(0);
        // setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        //    tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.text_tap_font, null);
            // tv.setTypeface(Typeface);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new HomeFragment();
                        break;
                    case 1:
                        fragment = new FindPlayerFragment();
                        break;
                    case 2:
                        fragment = new FindEventFragment();
                        break;
                    case 3:
                        fragment = new FriendFragment();
                        break;
                    case 4:
                        fragment = new FriendEventFragment();
                        break;
                    case 5:
                        fragment = new MyEventFragment();
                        break;
                    case 6:
                        fragment = new GroupFragment();
                        break;
                    case 7:
                        fragment = new ChatFragment();
                        break;
                }
                changeFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (getActivity().getIntent().hasExtra("ActionNotification")) {
            switch (getActivity().getIntent().getExtras().getString("ActionNotification")) {
                case "friend_request":
                    TabLayout.Tab tab = tabLayout.getTabAt(3);
                    tab.select();
                    fragment = new FriendFragment();
                    break;
                case "you_got_new_friend":
                    TabLayout.Tab tab2 = tabLayout.getTabAt(3);
                    tab2.select();
                    fragment = new FriendFragment();
                    break;
                default:
                    fragment = new HomeFragment();
            }
        } else {
            fragment = new HomeFragment();
        }
        changeFragment(fragment);
        return view;

    }

    private void setupViewPager(ViewPager viewPager) {

        Log.e("setupViewPager", "setupViewPager");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new FindPlayerFragment(), "Find a player");
        adapter.addFragment(new FindEventFragment(), "Find event");
        adapter.addFragment(new FriendFragment(), "Friends");
        adapter.addFragment(new FriendEventFragment(), "Friend’s events");
        adapter.addFragment(new MyEventFragment(), "My events");
        adapter.addFragment(new ChatFragment(), "Chat");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.body, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Evente","event");
        if (getActivity().getIntent().hasExtra("ActionNotification")) {
            getActivity().getIntent().removeExtra("ActionNotification");
        }
    }
}

