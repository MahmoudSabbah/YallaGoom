package com.yallagoom.fragment.eventTapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.yallagoom.R;
import com.yallagoom.activity.HomeCreateNewEventActivity;


public class HomeFragment extends Fragment {

    private BoomMenuButton bmb;
    private int idFragment = 1;
    private int preFragment = 0;
    private FloatingActionButton change_view_floating;
    private FloatingActionButton add_event_floating;
    private FloatingActionButton maim_floating_button;
    private int check=0;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        change_view_floating = (FloatingActionButton) view.findViewById(R.id.change_view_floating);
        add_event_floating = (FloatingActionButton) view.findViewById(R.id.add_event_floating);
        maim_floating_button = (FloatingActionButton) view.findViewById(R.id.maim_floating_button);
        Log.e("Tabb", "tab1");
        maim_floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (change_view_floating.getVisibility() == View.VISIBLE) {
                    change_view_floating.setVisibility(View.GONE);
                    add_event_floating.setVisibility(View.GONE);
                } else {
                    change_view_floating.setVisibility(View.VISIBLE);
                    add_event_floating.setVisibility(View.VISIBLE);
                }
            }
        });
        add_event_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), HomeCreateNewEventActivity.class);
                startActivityForResult(intent, 210);
            }
        });
        change_view_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check==1){
                    if (idFragment!=1){
                        changeFragment(new HomeListFragment());
                        idFragment = 1;
                        check=0;
                        change_view_floating.setImageResource(R.drawable.location_icon2);
                    }
                }else {
                    if (idFragment!=2){
                        changeFragment(new HomeMapFragment());
                        check=1;
                        idFragment = 2;
                        change_view_floating.setImageResource(R.drawable.list_item_icon);

                    }
                }
            }
        });
    /*    bmb = (BoomMenuButton) view.findViewById(R.id.bmb);
        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                Log.e("index",""+index);
                if (index==0){

                    if (idFragment!=1){
                        changeFragment(new HomeListFragment());
                        idFragment = 1;
                    }
                }else {
                    if (idFragment!=2){
                        changeFragment(new HomeMapFragment());

                        idFragment = 2;
                    }
                }
            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });*/

     /*   for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .normalImageRes(R.drawable.menu_icon);
            bmb.addBuilder(builder);
        }*/
        changeFragment(new HomeListFragment());
        return view;

    }

    private void changeFragment(Fragment fragment) {
        if (preFragment != idFragment) {
          //  getActivity().getFragmentManager().popBackStack();
         /*   FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(myFrag);
            trans.commit();
            manager.popBackStack();*/
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.body2, fragment).addToBackStack("tag2").commit();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 210:
                if (resultCode == 102) {
                   // getData();
                }
                break;
        }
    }
}
