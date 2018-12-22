package android.mehdi.soatunisitatrip;

import android.graphics.Paint;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerTitleStrip;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OpenStreetMap extends AppCompatActivity implements OnMapReadyCallback,LocationEngineListener,PermissionsListener,MapboxMap.OnMapClickListener{



    MapView osm;
    MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originlocation;
    private Point OriginPosition;
    private Point DestinationPosition;
    private Marker destinationMarker;
    private Button startButton;
    private NavigationMapRoute navigationMapRoute;
    private static final String TAG = "OpenStreetMap";



   /* MapView osm ;
    MapController mc;*/


    @SuppressWarnings("MissingPermission")
    @Override
    protected void onStart() {
        super.onStart();
        if(locationEngine !=null)
        {
            locationEngine.requestLocationUpdates();

        }
        if(locationLayerPlugin !=null)
        {
            locationLayerPlugin.onStart();
        }
        osm.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        osm.onResume();
    }

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onStop() {
        super.onStop();
        if(locationEngine !=null)
        {
            locationEngine.requestLocationUpdates();
        }
        if(locationLayerPlugin !=null)
        {
            locationLayerPlugin.onStop();
        }
        osm.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationEngine !=null)
        {
            locationEngine.deactivate();
        }
        osm.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        osm.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        osm.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this,"pk.eyJ1IjoibWVoZGk1MjU2IiwiYSI6ImNqb3hlbGx3cjBvNTAza3BjaTg5MTNkMDgifQ.T-o40xa5m92Lu6ALlTTHmw");
        setContentView(R.layout.activity_open_street_map);
        osm = (MapView) findViewById(R.id.map1);
        osm.onCreate(savedInstanceState);
        osm.getMapAsync(this);

        startButton = (Button)findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .origin(OriginPosition)
                        .destination(DestinationPosition)
                        .shouldSimulateRoute(true)
                        .build();
                NavigationLauncher.startNavigation(OpenStreetMap.this, options);


            }
        });



        /*osm = (MapView) findViewById(R.id.map);
        osm.setBuiltInZoomControls(true);
        osm.setMultiTouchControls(true);

        mc =(MapController) osm.getController();
        GeoPoint center = new GeoPoint(-20.1698,-40.2487);
        mc.animateTo(center);*/
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        enableLocation();
        map.addOnMapClickListener(this);
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(36.9253,
                        10.2803))
                .title("Eiffel Tower");
        map.addMarker(options);
        DestinationPosition= Point.fromLngLat(options.getPosition().getLongitude(),options.getPosition().getLatitude());
        OriginPosition= Point.fromLngLat(originlocation.getLongitude(),originlocation.getLatitude());
        getRoute(OriginPosition,DestinationPosition);

    }
    private void enableLocation(){

        if(PermissionsManager.areLocationPermissionsGranted(this)){
            inisialiseLoactionEngine();
            inizialiseLocationLayer();

        }
        else {
            permissionsManager= new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);

        }
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {

        if(destinationMarker !=null)
        {
            map.removeMarker(destinationMarker);
        }

        destinationMarker=map.addMarker(new MarkerOptions().position(point));
        DestinationPosition= Point.fromLngLat(point.getLongitude(),point.getLatitude());
        OriginPosition= Point.fromLngLat(originlocation.getLongitude(),originlocation.getLatitude());
        getRoute(OriginPosition,DestinationPosition);
        startButton.setEnabled(true);
        startButton.setBackgroundResource(R.color.colorPrimary);


    }

    private void getRoute(Point origin,Point destination)

    {

        NavigationRoute.builder()
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body() == null){
                            Log.e(TAG , "No Route found");
                            return;

                        }
                        else if (response.body().routes().size()==0){
                            Log.e(TAG , "No Route found");
                            return;
                        }
                        DirectionsRoute currentRoute = response.body().routes().get(0);
                        if (navigationMapRoute != null){
                            navigationMapRoute.removeRoute();
                        }else {
                            navigationMapRoute = new NavigationMapRoute(null,osm,map);
                        }

                        navigationMapRoute.addRoute(currentRoute);

                    }


                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Log.e(TAG , "Error:"+t.getMessage());
                    }
                });
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected() {
        locationEngine.requestLocationUpdates();

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null)
        {
            originlocation=location;
            setCameraPosition(location);
        }

    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

        //toast dialog

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onPermissionResult(boolean granted) {

        if(granted){
            enableLocation();
        }


    }
    @SuppressWarnings("MissingPermission")
    private void inisialiseLoactionEngine(){
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();
        Location lastLocation = locationEngine.getLastLocation();
        if(lastLocation!=null)
        {
            originlocation = lastLocation;

            setCameraPosition(lastLocation);
        }
        else {
            locationEngine.addLocationEngineListener(this);
        }


    }
    @SuppressWarnings("MissingPermission")
    private void inizialiseLocationLayer(){

        locationLayerPlugin = new LocationLayerPlugin(osm,map,locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);


    }

    private void setCameraPosition(Location location){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),13.0));


    }





   /* public void addMarker(GeoPoint center) {
        Marker marker = new Marker(osm);
        marker.setPosition(center);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_launcher_background));
        osm.getOverlays().clear();
        osm.getOverlays().add(marker);
        osm.invalidate();

    }*/

}
