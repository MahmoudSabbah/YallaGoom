package com.yallagoom.fragment.eventTapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.yallagoom.R;


public class HomeFragment extends Fragment {

    private BoomMenuButton bmb;
    private int idFragment = 1;
    private int preFragment = 0;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.e("Tabb","tab1");

        bmb = (BoomMenuButton) view.findViewById(R.id.bmb);
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
        });

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .normalImageRes(R.drawable.menu_icon);
            bmb.addBuilder(builder);
        }
        changeFragment(new HomeListFragment());
        return view;

    }
    private void changeFragment(Fragment fragment) {
        if (preFragment != idFragment) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.body, fragment).addToBackStack("tag2").commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
