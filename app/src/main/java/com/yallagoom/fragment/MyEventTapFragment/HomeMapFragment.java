package com.yallagoom.fragment.MyEventTapFragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.api.NearByEventAsyncTask;
import com.yallagoom.entity.Event;
import com.yallagoom.interfaces.NearEventCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.MapUtils;
import com.yallagoom.utils.ToolUtils;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import static io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider.REQUEST_CHECK_SETTINGS;

/**
 * Created by Mahmoud Sabbah on 2/1/2018.
 */

public class HomeMapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFragment;
    private ImageLoader imageLoader;

    public HomeMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_map, container, false);
     /*   mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);*/
        mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(HomeMapFragment.this);
        cameraZoom();
        imageLoader = ImageLoader.getInstance();

    /*    ToolUtils.buildAlertMessageNoGps(HomeMapFragment.this.getActivity(), this, this, new CheckGPSCallback() {
            @Override
            public void processFinish(boolean check, Status status) {
                if (!check) {
                    try {
                        status.startResolutionForResult(
                                HomeMapFragment.this.getActivity(), REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                } else {
                    mapFragment.getMapAsync(HomeMapFragment.this);
                    cameraZoom();
                }
            }
        });*/


        return view;

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("mGoogleMap", "mGoogleMap");

        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(HomeMapFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeMapFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        //  mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("resultCode", "resultCode");
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mapFragment.getMapAsync(HomeMapFragment.this);
                        cameraZoom();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

    private void cameraZoom() {
        SmartLocation.with(HomeMapFragment.this.getActivity()).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(final Location location) {

                        NearByEventAsyncTask nearByEventAsyncTask = new NearByEventAsyncTask(HomeMapFragment.this.getActivity(), 1, null, new NearEventCallback() {
                            @Override
                            public void processFinish(Event nearEvent) {
                                for (int i = 0; i < nearEvent.getData().size(); i++) {


                                    LatLng latLng = new LatLng(Double.parseDouble(nearEvent.getData().get(i).getEventLat()), Double.parseDouble(nearEvent.getData().get(i).getEventLong()));
                                    View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
                                    TextView numTxt = (TextView) marker.findViewById(R.id.name);
                                    final ImageView image_event = (ImageView) marker.findViewById(R.id.image_event);
                                    numTxt.setText(nearEvent.getData().get(i).getEventTitle());
                                    //     imageLoader.displayImage(Constant.imageUrl + nearEvent.getData().get(i).getEventImage(), image_event);
                                    if (nearEvent.getData().get(i).getEventImage() != null) {
                                        ToolUtils.setImageSmall_50(Constant.imageUrl + nearEvent.getData().get(i).getEventImage(),image_event,imageLoader);
                                   /*     imageLoader.loadImage(Constant.imageUrl + nearEvent.getData().get(i).getEventImage(), targetSize, new ImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String s, View view) {
                                                Log.e("onLoading", "onLoadingStarted");

                                            }

                                            @Override
                                            public void onLoadingFailed(String s, View view, FailReason failReason) {
                                                Log.e("onLoading", "onLoadingFailed");

                                            }

                                            @Override
                                            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                                Log.e("onLoading", "onLoadingComplete" + s);
                                                // image_event.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 80, 80, false));
                                                image_event.setImageBitmap(bitmap);
                                            }

                                            @Override
                                            public void onLoadingCancelled(String s, View view) {
                                                Log.e("onLoading", "onLoadingCancelled");

                                            }
                                        });*/
                                    }
                                    //  MapUtils.PicassoImage(getActivity(), Constant.imageUrl + nearEvent.getData().get(i).getEventImage(), image_event);
                                    BitmapDescriptor imagelayout = BitmapDescriptorFactory.fromBitmap(MapUtils.createDrawableFromView(getActivity(), marker));
                                    if (imagelayout != null) {
                                        mGoogleMap.addMarker(new MarkerOptions()
                                                .position(latLng)
                                                .icon(imagelayout));
                                    }




                              /*      final MarkerOptions marker = new MarkerOptions().position(
                                            latLng).anchor(0.5f, 0.5f) .title(nearEvent.getData().get(i).getEventTitle())
                                            .snippet(nearEvent.getData().get(i).getStartEventDate()+" "+nearEvent.getData().get(i).getStartEventTime()).flat(true);//fromBitmap(MapUtils.getbitmap(CONTEXT, MapUtils.carIconMarker))
                                    mGoogleMap.addMarker(marker).setTag(nearEvent.getData().get(i).getId());*/
                                }
                                LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                final CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myLocation, 15);
                                //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( targetPosition, 15 ), 1000, null);
                                mGoogleMap.animateCamera(yourLocation);
                            }
                        });
                        nearByEventAsyncTask.execute(location.getLatitude() + "", location.getLongitude() + "", Constant.defultDistance);

                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SmartLocation.with(HomeMapFragment.this.getActivity()).location().stop();
    }
   /* @Override
    public void onDestroyView() {
        super.onDestroyView();
        MapFragment mapFragment = (MapFragment) getActivity()
                .getFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
            getActivity().getFragmentManager().beginTransaction()
                    .remove(mapFragment).commit();
    }*/
}

