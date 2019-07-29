package com.bri.ojt.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bri.ojt.Model.Request.ReqLocation;
import com.bri.ojt.Model.Response.LocationResponse;
import com.bri.ojt.Network.API;
import com.bri.ojt.Network.RetrofitClient;
import com.bri.ojt.R;
import com.bri.ojt.Util.BRISignature;
import com.bri.ojt.Util.BaseActivity;
import com.bri.ojt.Util.Consts;
import com.bri.ojt.Widget.ToggleButton;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationActivity extends BaseActivity implements OnMapReadyCallback {

    private API api;
    private Consts consts;
    private GoogleMap gMaps;
    private FusedLocationProviderClient fusedLocClient;
    private ProgressBar progressBar;
    private LocationRequest locationRequest;
    private LinearLayout locDetail;
    private TextView locTitle;
    private LatLng sourceLoc;
    private LatLng destionationLoc;
    private BottomSheetBehavior btmSheet;
    private ToggleButton toggleATM, toggleCRM, toggleEDC, toggleUKER;
    private ReqLocation reqLocation;
    int from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocClient = LocationServices.getFusedLocationProviderClient(this);

        from = getIntent().getIntExtra("from", 100);

        Thread thread = new Thread(this::setView);
        thread.start();

        Handler handler = new Handler();
        handler.postDelayed(() -> new TaskHelper(LocationActivity.this).execute(), 1000);

        api = RetrofitClient.getNewInstance(this).getAPI();
        consts = Consts.getInstance();


    }

    private void setView() {
        LinearLayout btmSheetLayout = findViewById(R.id.bottom_sheet);
        progressBar = findViewById(R.id.progress_bar);
        locDetail = findViewById(R.id.location_detail);
        locTitle = findViewById(R.id.location_title);
        toggleATM = findViewById(R.id.toggle_atm);
        toggleCRM = findViewById(R.id.toggle_crm);
        toggleEDC = findViewById(R.id.toggle_edc);
        toggleUKER = findViewById(R.id.toggle_uker);
        btmSheet = BottomSheetBehavior.from(btmSheetLayout);
        Button btnRoute = findViewById(R.id.btn_routes);
        btnRoute.setOnClickListener(onClickListener);
        toggleATM.setOnClickListener(onClickListener);
        toggleCRM.setOnClickListener(onClickListener);
        toggleEDC.setOnClickListener(onClickListener);
        toggleUKER.setOnClickListener(onClickListener);

        runOnUiThread(() -> {
            if (from == 100) {
                toggleATM.setSelected(true);
            } else {
                toggleUKER.setSelected(true);
            }
        });
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_location;
    }

    @Override
    protected String getActivityTitle() {
        return "";
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMaps = googleMap;
        LatLng IndonesiaCord = new LatLng(-2.098339, 117.910500);
        gMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(IndonesiaCord, 3.5f));

        gMaps.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMaps.setTrafficEnabled(true);
        if (checkAccessLocation(REQ_ACCESS_FINE_LOCATION)) {
            setMyLocation();
        }

    }

    private void setMyLocation() {
        gMaps.setOnMarkerClickListener(onMarkerClickListener);
        gMaps.setOnMapClickListener(onMapClickListener);
        goSettingLoc();
    }

    private void goSettingLoc() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> responseTask = settingsClient.checkLocationSettings(builder.build());
        responseTask.addOnSuccessListener(locationSettingsResponse -> getLastLocation());
        responseTask.addOnFailureListener(e -> {
            showMessage("Akses gps diperlukan untuk mentukan lokasi sekitar Anda.");
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(LocationActivity.this,
                                REQ_SETTINGS_LOCATION);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }, 1200);

        } );
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkAccessLocation(REQ_ACCESS_FINE_LOCATION)) {
            // get current location instead of last location
//            fusedLocClient.getLastLocation().addOnSuccessListener(location -> {
//                if (location != null) {
//                    updateLocation(new LatLng(location.getLatitude(), location.getLongitude()));
//                    showMessage("getlast location: " + location.getLatitude() + ","+ location.getLongitude());
//                } else {
//                    startLocationUpdates();
//                }
//            });
            startLocationUpdates();
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void stopLocationUpdates() {
        fusedLocClient.removeLocationUpdates(locationCallback);
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult == null)
                return;
            for (Location location : locationResult.getLocations()) {
                updateLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                sourceLoc = new LatLng(location.getLatitude(), location.getLongitude());
                stopLocationUpdates();
            }
        }
    };

    private GoogleMap.OnMarkerClickListener onMarkerClickListener = marker -> {
        locTitle.setText(marker.getTitle());
        destionationLoc = marker.getPosition();
        locDetail.setVisibility(View.VISIBLE);
        if (btmSheet.getState() == BottomSheetBehavior.STATE_COLLAPSED)
            btmSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

        return false;
    };

    private GoogleMap.OnMapClickListener onMapClickListener = latLng -> {
        locDetail.setVisibility(View.GONE);
        if (btmSheet.getState() == BottomSheetBehavior.STATE_EXPANDED)
            btmSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
    };

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_routes:
                String url = String.format(Locale.US, "https://www.google.com/maps/dir/?api=1&origin=%.7f,%.7f&destination=%.7f,%.7f&travelmode=driving", sourceLoc.latitude, sourceLoc.longitude, destionationLoc.latitude, destionationLoc.longitude);
                Uri briHeadquarterLoc = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, briHeadquarterLoc);
                startActivity(intent);
                break;
            case R.id.toggle_atm:
                reqLocation = new ReqLocation("atm", "2", String.valueOf(sourceLoc.latitude), String.valueOf(sourceLoc.longitude));
                getLocationByType(reqLocation);
                setToggle(v.getId());
                break;
            case R.id.toggle_crm:
                reqLocation = new ReqLocation("crm", "2", String.valueOf(sourceLoc.latitude), String.valueOf(sourceLoc.longitude));
                getLocationByType(reqLocation);
                setToggle(v.getId());
                break;
            case R.id.toggle_edc:
                reqLocation = new ReqLocation("edc", "2", String.valueOf(sourceLoc.latitude), String.valueOf(sourceLoc.longitude));
                getLocationByType(reqLocation);
                setToggle(v.getId());
                break;
            case R.id.toggle_uker:
                default:
                    reqLocation = new ReqLocation("branch", "10", String.valueOf(sourceLoc.latitude), String.valueOf(sourceLoc.longitude));
                    getLocationByType(reqLocation);
                    setToggle(v.getId());
                    break;
        }

    };

    private void setToggle(int id) {
        toggleATM.setSelected(id == toggleATM.getId());
        toggleCRM.setSelected(id == toggleCRM.getId());
        toggleEDC.setSelected(id == toggleEDC.getId());
        toggleUKER.setSelected(id == toggleUKER.getId());
    }

    @SuppressLint("MissingPermission")
    private void updateLocation(LatLng latLng) {
        gMaps.setMyLocationEnabled(true);
        gMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 2000, null);
        reqLocation = new ReqLocation(from == 100 ? "atm" : "branch", from == 100 ? "2" : "10", String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));

        getLocationByType(reqLocation);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQ_ACCESS_FINE_LOCATION:
                    setMyLocation();
                    break;
            }
        } else {
            showMessage("Akses Lokasi dibutuhkan untuk menampilkan lokasi disekitar Anda.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Akses Lokasi dibutuhkan untuk menampilkan lokasi disekitar Anda.", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(this::finish, 1000);
            return;
        }

        switch (requestCode) {
            case REQ_SETTINGS_LOCATION:
                getLastLocation();
                break;
        }

    }

    private static class TaskHelper extends AsyncTask<String, Void, Void> {

        private WeakReference<LocationActivity> activityReference;
        private SupportMapFragment fragment;

        // only retain a weak reference to the activity
        TaskHelper(LocationActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            fragment = SupportMapFragment.newInstance();
            activityReference.get().getSupportFragmentManager().beginTransaction().add(R.id.frame_map, fragment).commit();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fragment.getMapAsync(activityReference.get());
            activityReference.get().switchProgressBar();
        }
    }

    private void getLocationByType(ReqLocation location) {
        switchProgressBar();
        String path = Consts.PATH_LOCATION + "/" + location.getTipe() + "/" + location.getRadius() + "/" +
                location.getLatitude() + "/" + location.getLongitude();

        Map<String, String> header = BRISignature.getHeaderv2(path, "GET", consts.getToken(), "");

        Call<LocationResponse> call = api.getLocation(header, location.getTipe(), location.getRadius(), location.getLatitude(), location.getLongitude());

        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                switchProgressBar();

                LocationResponse locationResponse = response.body();
                if (locationResponse != null) {
                    if (response.code() == Consts.CODE_SUCCESS && locationResponse.getResponseCode().equalsIgnoreCase("200")) {
                        // clear all mark
                        gMaps.clear();
                        if (locDetail.getVisibility() == View.VISIBLE)
                            locDetail.setVisibility(View.GONE);

                        for (LocationResponse.Location item : locationResponse.getData()) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            switch (location.getTipe()) {
                                case "atm":
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_atm));
                                    markerOptions.title(item.getLokasi());
                                    break;
                                case "crm":
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_crm));
                                    markerOptions.title(item.getLokasi());
                                    break;
                                case "edc":
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_edc));
                                    markerOptions.title(item.getNamaMerchant());
                                    break;
                                case "branch":
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_uker));
                                    markerOptions.title(item.getUnitKerja());
                                    break;
                            }

                            markerOptions.position(new LatLng(Double.valueOf(item.getLatitude().trim().replace(",", "")), Double.valueOf(item.getLongitude().trim().replace(",", ""))));
                            gMaps.addMarker(markerOptions);

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                switchProgressBar();
                showErrorMessage(t.getMessage());
            }
        });
    }

    private void switchProgressBar() {
        runOnUiThread(() -> progressBar.setVisibility(progressBar.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));
    }
}
