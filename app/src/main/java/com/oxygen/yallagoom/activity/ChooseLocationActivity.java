package com.oxygen.yallagoom.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.action.RecyclerItemClickListener;
import com.oxygen.yallagoom.adapter.RecycleViewPlaces;
import com.oxygen.yallagoom.entity.PlaceJSONParser;
import com.oxygen.yallagoom.interfaces.CheckGPSCallback;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import static io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider.REQUEST_CHECK_SETTINGS;

public class ChooseLocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFragment;
    private EditText search_edit;
    private TextView ok;
    private TextView cancel;
    private LinearLayout parent;
    private RecyclerView search_locationList;
    private Location mLocation;
    boolean flag = true;
    private List<HashMap<String, String>> data_place;
    private String latPlace;
    private String lngPlace;
    private Marker mainMarker;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_location);
        ToolUtils.hideStatus(ChooseLocationActivity.this);
        parent = (LinearLayout) findViewById(R.id.parent);
        bundle = getIntent().getExtras();

        ToolUtils.setLightStatusBar(parent, ChooseLocationActivity.this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        search_locationList = (RecyclerView) findViewById(R.id.search_location);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        search_locationList.setLayoutManager(mLayoutManager);
        search_locationList.setItemAnimator(new DefaultItemAnimator());
        ok = (TextView) findViewById(R.id.ok);
        cancel = (TextView) findViewById(R.id.cancel);
        search_edit = (EditText) findViewById(R.id.search_edit);
        ToolUtils.buildAlertMessageNoGps(ChooseLocationActivity.this, this, this, new CheckGPSCallback() {
            @Override
            public void processFinish(boolean check, Status status) {
                if (!check) {
                    try {
                        status.startResolutionForResult(
                                ChooseLocationActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                } else {
                    mapFragment.getMapAsync(ChooseLocationActivity.this);
                    cameraZoom();
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("lat", mainMarker.getPosition().latitude);
                intent.putExtra("lng", mainMarker.getPosition().longitude);
                intent.putExtra("address", search_edit.getText().toString());
                setResult(102, intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mLocation != null) {
                    search_locationList.setVisibility(View.VISIBLE);
                    getPlaces(charSequence + "");
                   /* GetLocationNameAsyncTask getLocationNameAsyncTask = new GetLocationNameAsyncTask(ChooseLocationActivity.this, new GetPlaceCallback() {
                        @Override
                        public void processFinish(List<HashMap<String, String>> data_place) {
                            RecycleViewPlaces RecycleViewPlaces=new RecycleViewPlaces(data_place);
                            search_locationList.setAdapter(RecycleViewPlaces);

                        }
                    });
                    getLocationNameAsyncTask.execute(charSequence + "", mLocation.getLatitude() + "", mLocation.getLongitude() + "");*/
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        search_locationList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView address = (TextView) view.findViewById(R.id.address);

                getPlaceDetails(data_place.get(position).get("place_id"), address.getText().toString());

            }
        }));
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
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(ChooseLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ChooseLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                //   search_edit.setText(mGoogleMap.getCameraPosition().target.latitude + " , " + mGoogleMap.getCameraPosition().target.longitude);
            }
        });
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // TODO Auto-generated method stub
                Log.e("mainMarker", "mainMarker");
                if (mainMarker == null) {
                    final MarkerOptions markerOptions = new MarkerOptions().position(
                            point).zIndex(1.0f);//fromBitmap(MapUtils.getbitmap(CONTEXT, MapUtils.carIconMarker))
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(ToolUtils.getbitmap(ChooseLocationActivity.this, R.drawable.main_marker)));
                    mainMarker = mGoogleMap.addMarker(markerOptions);
                } else {
                    mainMarker.setPosition(point);
                }

            }
        });

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
                        mapFragment.getMapAsync(ChooseLocationActivity.this);
                        cameraZoom();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

    private void cameraZoom() {

        SmartLocation.with(ChooseLocationActivity.this).location()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(final Location location) {
                        mLocation = location;
                        LatLng myLocation = null;
                        if (bundle.getBoolean("check")) {
                            myLocation = new LatLng(location.getLatitude(), location.getLongitude());

                        } else {
                            myLocation = new LatLng(bundle.getDouble("lat"), bundle.getDouble("lng"));
                        }
                        if (flag) {
                            if (mainMarker == null) {
                                final MarkerOptions markerOptions = new MarkerOptions().position(
                                        myLocation).zIndex(1.0f);//fromBitmap(MapUtils.getbitmap(CONTEXT, MapUtils.carIconMarker))
                                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(ToolUtils.getbitmap(ChooseLocationActivity.this, R.drawable.main_marker)));
                                mainMarker = mGoogleMap.addMarker(markerOptions);
                            } else {
                                mainMarker.setPosition(myLocation);
                            }
                            final CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myLocation, 15);
                            //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( targetPosition, 15 ), 1000, null);
                            mGoogleMap.animateCamera(yourLocation);
                            flag = false;
                        }
                    }
                });


      /*  if (!bundle.getBoolean("check")) {
            LatLng myLocation = new LatLng(Double.parseDouble(bundle.getString("lat")), Double.parseDouble(bundle.getString("lng")));
            if (mainMarker == null) {
                final MarkerOptions markerOptions = new MarkerOptions().position(
                        myLocation).zIndex(1.0f);//fromBitmap(MapUtils.getbitmap(CONTEXT, MapUtils.carIconMarker))
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(ToolUtils.getbitmap(ChooseLocationActivity.this, R.drawable.main_marker)));
                mainMarker = mGoogleMap.addMarker(markerOptions);
            } else {
                mainMarker.setPosition(myLocation);
            }
            final CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myLocation, 15);
            //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( targetPosition, 15 ), 1000, null);
            mGoogleMap.animateCamera(yourLocation);
        }*/

    }

    private void getPlaces(String value) {
        AndroidNetworking.get(ToolUtils.searchresult(value, mLocation.getLatitude(), mLocation.getLongitude(), this))
                .setTag("getPlace")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response2", "" + response);
                        PlaceJSONParser placeJsonParser = new PlaceJSONParser();
                        try {
                            String status = response.getString("status");
                            if (status.equalsIgnoreCase("OK")) {
                                data_place = placeJsonParser.parse(response);

                                RecycleViewPlaces RecycleViewPlaces = new RecycleViewPlaces(data_place);
                                search_locationList.setAdapter(RecycleViewPlaces);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

    }

    private void getPlaceDetails(String placeID, final String address) {
        final String dataResult = ToolUtils.getLanLong(placeID, ChooseLocationActivity.this);
        AndroidNetworking.get(dataResult)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    public String status;

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Fetching the data from web service in background
                            status = response.getString("status");
                            if (status.equalsIgnoreCase("OK")) {
                                for (int i = 0; i < 1; i++) {
                                    JSONObject resultData = response.getJSONObject("result");
                                    JSONObject geometry = resultData.getJSONObject("geometry");
                                    JSONObject location = geometry.getJSONObject("location");
                                    latPlace = location.getString("lat");
                                    lngPlace = location.getString("lng");
                                }
                                LatLng myLocation = new LatLng(Double.parseDouble(latPlace),
                                        Double.parseDouble(lngPlace));
                                if (mainMarker == null) {
                                    final MarkerOptions markerOptions = new MarkerOptions().position(
                                            myLocation).zIndex(1.0f);//fromBitmap(MapUtils.getbitmap(CONTEXT, MapUtils.carIconMarker))
                                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(ToolUtils.getbitmap(ChooseLocationActivity.this, R.drawable.main_marker)));
                                    mainMarker = mGoogleMap.addMarker(markerOptions);
                                } else {
                                    mainMarker.setPosition(myLocation);
                                }
                                CameraUpdate center =
                                        CameraUpdateFactory.newLatLng(myLocation);
                                CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

                                mGoogleMap.moveCamera(center);
                                mGoogleMap.animateCamera(zoom);
                                search_edit.setText(address);
                                search_locationList.setVisibility(View.GONE);
                                ToolUtils.hideKeyboard(ChooseLocationActivity.this);
                            } else {
                                if (status.equalsIgnoreCase("NOT_FOUND")) {
                                    ToolUtils.viewToast(ChooseLocationActivity
                                            .this, "No Coordinates found  for this location");

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
}
