package fr.esiea.mobile.attrackpark;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ServiceConfigurationError;

import fr.esiea.mobile.attrackpark.domain.Park;
import fr.esiea.mobile.attrackpark.domain.Parks;

public class MapsActivity extends FragmentActivity implements LocationListener{

    // Parameter to define the map and location manager
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    // refresh user postion every 5 minutes
    private final long UPDATE_LOCATION_TIME = 300000;
    // refresh user position every 10 km
    private final float UPDATE_LOCATION_DISTANCE = 10000;

    // Lattitude, longitude and zoom to use when we go back from an activity to MapsActivity
    // LatLng is also use when user ask to locate a park
    private LatLng latLng = null;
    private Float zoom = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Retrieve park location when is needed
        // Is in the onCreate since the LatLng can change if user go to a child activity and come back
        Bundle b = getIntent().getExtras();
        if (b != null && !b.isEmpty()) {
            Log.d("Map","Has lat and lon");
            double lat = b.getDouble("latitude");
            double lon = b.getDouble("longitude");
            latLng = new LatLng(lat,lon);
            Log.d("Map","lat " + lat + " lon " + lon);
        } else {
            latLng = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set up the map to be used
        setUpMapIfNeeded();

        if (mMap != null) {
            // add all saved parks to the map and locate them with markers
            for (Park park : Parks.getInstance().getParks()) {
                mMap.addMarker(new MarkerOptions().position(park.getLatLng()).title(park.getName()));
            }
        }

        // Define behavior when a park's marker is clicked
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // When a marker is clicked, a new activity displaying park's detail is launched
                Park park = Parks.getInstance().getParkByName(marker.getTitle());
                if (park != null){
                    Log.d("Marker","Click on marker for park " + park.getId());
                    MarkerClicked(park);
                }
                return true;
            }
        });

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        String theBestProvider = locationManager.getBestProvider(criteria, true);
        try {
            locationManager.requestLocationUpdates(theBestProvider, UPDATE_LOCATION_TIME, UPDATE_LOCATION_DISTANCE, this);
        } catch (SecurityException e){
            e.printStackTrace();
            Log.e("Map", "Authorisation denied for requestLocationUpdates : " + e.getMessage());
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e){
            e.printStackTrace();
            Log.e("Map", "Authorisation denied for requestLocationUpdates : " + e.getMessage());
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            Log.d("Map", "mMap is null");
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                    setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */

    // set up the mMap object
    private void setUpMap() {
        Log.d("Map","Set map on user");
        mMap.setMyLocationEnabled(true);
    }

    // move the camera to the given location depending and latLng and zoom parameters
    private void moveCameraToGivenLocation(LatLng latLng, Float zoom) {
        Log.d("Map", "Set map on given coordinates");
        mMap.setMyLocationEnabled(true);

        if (zoom == null) {
            // zoom is null then we want to locate and focus on the selected park
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(latLng).zoom(11).build()));
        } else {
            // zoom is not null then we want to set the camera as the user set it before he launch an other activity
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(latLng).zoom(zoom).build()));
        }
    }

    private void MarkerClicked(Park park){
        // Camera position is saved
        this.latLng = mMap.getCameraPosition().target;
        this.zoom = mMap.getCameraPosition().zoom;
        // Create intent to launch the DetailParkActivity
        Intent nextActivity = new Intent(this, DetailParkActivity.class);
        // Give the selected park id
        Bundle b = new Bundle();
        b.putLong("id",park.getId());
        nextActivity.putExtras(b);
        // launch activity
        startActivity(nextActivity);
    }

    // This method is used each time the MapsActivity is resumed
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, "Position changée", Toast.LENGTH_SHORT);
        // Set behavior depending on LatLong and zoom parameters
        if (latLng == null){
            // latLng is null the we want to locate and focus on the user
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Float zoom = new Float(11);
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(latLng).zoom(zoom).build()));
        } else {
            // latLng is not null then we try either locate a park or just resume the activity as the user set it
           moveCameraToGivenLocation(latLng,zoom);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(this, "Status changé", Toast.LENGTH_SHORT);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Provider activé", Toast.LENGTH_SHORT);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Provider désactivé", Toast.LENGTH_SHORT);
    }
}
