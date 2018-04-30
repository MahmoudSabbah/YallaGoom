package com.yallagoom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.activity.LoginActivity;
import com.yallagoom.fragment.ticketsTapFragment.DiscoverFragment;
import com.yallagoom.fragment.ticketsTapFragment.MyBookingFragment;
import com.yallagoom.fragment.ticketsTapFragment.OffersFragment;
import com.yallagoom.fragment.ticketsTapFragment.WishlistFragment;

/**
 * Created by Mahmoud Sabbah on 2/1/2018.
 */

public class TicketsFragment extends Fragment {


    private TextView login_bt;
    private TextView header_title;
    private FrameLayout body_tickets;
    private TabLayout tabLayout;
    Fragment fragment = null;
    public TicketsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);
        login_bt=(TextView)view.findViewById(R.id.login_bt);
        header_title=(TextView)getActivity().findViewById(R.id.header_title);
        header_title.setText(getString(R.string.tickets));

        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TicketsFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        body_tickets = (FrameLayout) view.findViewById(R.id.body_tickets);
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
                        fragment = new DiscoverFragment();
                        break;
                    case 1:
                        fragment = new OffersFragment();
                        break;
                    case 2:
                        fragment = new MyBookingFragment();
                        break;
                    case 3:
                        fragment = new WishlistFragment();
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
        fragment = new DiscoverFragment();
        changeFragment(fragment);
        return view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.body_tickets, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

}

