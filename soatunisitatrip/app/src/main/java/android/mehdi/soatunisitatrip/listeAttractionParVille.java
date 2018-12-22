
package android.mehdi.soatunisitatrip;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
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

public class listeAttractionParVille extends AppCompatActivity implements OnMapReadyCallback,LocationEngineListener,PermissionsListener,MapboxMap.OnMapClickListener {

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

    TextView tvnom_attraction,adresse_att,description_att;
    ImageView image_attrac, precedent,teleph,mail,site;
    int a =Integer.parseInt( AttractionByVilleAdapter.tel);
    double longitude = AttractionByVilleAdapter.longi;
    double latitude= AttractionByVilleAdapter.latit;
    BottomNavigationView btm;

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onStart() {
        super.onStart();
         //Point point= Point.fromLngLat(longitude,latitude);
        if(locationEngine !=null)
        {
            locationEngine.requestLocationUpdates();

        }
        if(locationLayerPlugin !=null)
        {
            locationLayerPlugin.onStart();
        }
        if(destinationMarker !=null)
        {
            map.removeMarker(destinationMarker);
        }


        String nom_attraction = getIntent().getExtras().getString("attraction_name");
        String image = getIntent().getExtras().getString("attraction_image");
        String descrition = getIntent().getExtras().getString("attraction_description");
        String siteweb = getIntent().getExtras().getString("attraction_siteweb");
        String email = getIntent().getExtras().getString("attraction_email");
        int telephone = getIntent().getExtras().getInt("attraction_telephone");
        String Adresse = getIntent().getExtras().getString("attraction_adresse");
       double latitude= getIntent().getExtras().getFloat("attraction_latitude");
        double longitude= getIntent().getExtras().getFloat("attraction_longitude");





        /*---------------------------------------------------------------------------------*/


        tvnom_attraction = (TextView) findViewById(R.id.tvnom);
        image_attrac = (ImageView) findViewById(R.id.imageView4);
        description_att = (TextView) findViewById(R.id.textView7);
       /* site= (ImageView) findViewById(R.id.site);
        mail=(ImageView) findViewById(R.id.mail);
        teleph=(ImageView)findViewById(R.id.tele);*/
        adresse_att = (TextView) findViewById(R.id.textView6);

        precedent = (ImageView) findViewById(R.id.imageView2);

        btm= (BottomNavigationView) findViewById(R.id.bottom1);


        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.web:
                        Intent broswer = new Intent(Intent.ACTION_VIEW, Uri.parse(siteweb));
                        startActivity(broswer);

                        break;
                    case R.id.call:
                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:" + a));
                        startActivity(call);
                        break;


                    case R.id.meail:
                        String[] TO = {email};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");


                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            finish();
                            System.out.println("Finished sending email...");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(listeAttractionParVille.this,
                                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }



                }

                return false;
            }
        });
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
        setContentView(R.layout.activity_liste_attraction_par_ville);


        System.out.println("laaaaaa"+ latitude+  "texxxt"+ longitude);



        /*------------------------------------------------------------------------------------*/
        Mapbox.getInstance(this,"pk.eyJ1IjoibWVoZGk1MjU2IiwiYSI6ImNqb3hlbGx3cjBvNTAza3BjaTg5MTNkMDgifQ.T-o40xa5m92Lu6ALlTTHmw");
        setContentView(R.layout.activity_liste_attraction_par_ville);
        osm = (MapView) findViewById(R.id.map3);
        String nom_attraction = getIntent().getExtras().getString("attraction_name");
        String image = getIntent().getExtras().getString("attraction_image");
        String descrition = getIntent().getExtras().getString("attraction_description");
        String siteweb = getIntent().getExtras().getString("attraction_siteweb");
        String email = getIntent().getExtras().getString("attraction_email");
        int telephone = getIntent().getExtras().getInt("attraction_telephone");
        String Adresse = getIntent().getExtras().getString("attraction_adresse");
      /*  Float latitude= getIntent().getExtras().getFloat("attraction_latitude");
        Float longitude= getIntent().getExtras().getFloat("attraction_longitude");*/





        /*---------------------------------------------------------------------------------*/


        tvnom_attraction = (TextView) findViewById(R.id.tvnom);
        image_attrac = (ImageView) findViewById(R.id.imageView4);
        description_att = (TextView) findViewById(R.id.textView7);
       /* site= (ImageView) findViewById(R.id.site);
        mail=(ImageView) findViewById(R.id.mail);
        teleph=(ImageView)findViewById(R.id.tele);*/
        adresse_att = (TextView) findViewById(R.id.textView6);

        precedent = (ImageView) findViewById(R.id.imageView2);

        btm= (BottomNavigationView) findViewById(R.id.bottom1);


        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.web:
                        Intent broswer = new Intent(Intent.ACTION_VIEW, Uri.parse(siteweb));
                        startActivity(broswer);

                        break;
                    case R.id.call:
                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:" + a));
                        startActivity(call);
                        break;


                    case R.id.meail:
                        String[] TO = {email};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
/*
                        Toast.makeText(listeAttractionParVille.this, "text"+longitude+latitude, Toast.LENGTH_SHORT).show();
*/

                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            finish();
                            System.out.println("Finished sending email...");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(listeAttractionParVille.this,
                                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }



                }

                return false;
            }
        });
        osm.onCreate(savedInstanceState);
        osm.getMapAsync(this);

        /*startButton = (Button)findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .origin(OriginPosition)
                        .destination(DestinationPosition)
                        .shouldSimulateRoute(true)
                        .build();
                NavigationLauncher.startNavigation(listeAttractionParVille.this, options);


            }
        });*/
        /*------------------------------------------------------------------------------------*/

        tvnom_attraction.setText(nom_attraction);
        description_att.setText(descrition);
        adresse_att.setText(Adresse);
        Glide.with(this).load(image).into(image_attrac);


        /*----------------------------*/


        precedent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*---------------------------------------------------*/


    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        enableLocation();
        map.addOnMapClickListener(this);

        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(latitude,
                        longitude))
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

    @Override
    public void onMapClick(@NonNull LatLng point) {

       /* if(destinationMarker !=null)
        {
            map.removeMarker(destinationMarker);
        }*/
/*
        destinationMarker=map.addMarker(new MarkerOptions().position(point));
        DestinationPosition= Point.fromLngLat(point.getLongitude(),point.getLatitude());
        OriginPosition= Point.fromLngLat(originlocation.getLongitude(),originlocation.getLatitude());
        getRoute(OriginPosition,DestinationPosition);*/
        /*startButton.setEnabled(true);
        startButton.setBackgroundResource(R.color.colorPrimary);*/


    }

    private void setCameraPosition(Location location){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),13.0));


    }
}

