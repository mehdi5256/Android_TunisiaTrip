package android.mehdi.soatunisitatrip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Type_Attraction_Activity extends AppCompatActivity {
    BottomNavigationView bottom;
    private String URL_JSON = "http://192.168.1.8/tunisiatrip/select_type_attraction.php";
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Type_Attraction> lstAnime = new ArrayList<>();
    private RecyclerView myrv;
    TextView temp1;
    ImageView icontemp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type__attraction_);

        // Recieve Data
        String nomville = getIntent().getExtras().getString("ville_name");
        String imageville = getIntent().getExtras().getString("ville_image");

        CollapsingToolbarLayout collapsingToolbarLayout  = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);
/*
        TextView tvname = (findViewById(R.id.collapsingtoolbar_id));
*/
        ImageView img = findViewById(R.id.image_ville1);

         bottom = findViewById(R.id.bottom1);
         temp1 = (TextView)findViewById(R.id.temperature);
        icontemp = findViewById(R.id.icontemp);




        // setting values to each other
/*
        tvname.setText(nomville);
*/
        collapsingToolbarLayout.setTitle(nomville);
        Glide.with(this).load(imageville).into(img);






        myrv = findViewById(R.id.rv1);

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.map:

                        Intent intent1 = new Intent(Type_Attraction_Activity.this, OpenStreetMap.class);
                        startActivity(intent1);
                        break;

                    case R.id.experience:
                       Intent intent2 = new Intent(Type_Attraction_Activity.this, LoginActivity.class);
                        startActivity(intent2);
                        break;

                    /*case R.id.guide:
                        Intent intent = new Intent(Type_Attraction_Activity.this,Type_Attraction_Activity.class);
                        startActivity(intent);
                        Toast.makeText(Type_Attraction_Activity.this, "guide", Toast.LENGTH_SHORT).show();
                        break;*/


                }
                return false;
            }
        });


                jsoncall();
                findweather();

        }



    public void jsoncall() {


        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0; i < response.length(); i++) {


/*
                    Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();
*/


                    try {

                        jsonObject = response.getJSONObject(i);
                        Type_Attraction att = new Type_Attraction();
                        att.setId(jsonObject.getInt("id"));
                        att.setNom(jsonObject.getString("nom"));
                        att.setImage(jsonObject.getString("image"));

                        lstAnime.add(att);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //Toast.makeText(MainActivity.this,"Size of Liste "+String.valueOf(lstAnime.size()),Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this,lstAnime.get(1).toString(),Toast.LENGTH_SHORT).show();

                setRvadapter(lstAnime);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(Type_Attraction_Activity.this);
        requestQueue.add(ArrayRequest);
    }

    public void setRvadapter(List<Type_Attraction> lst) {

        TypeAttractionAdapter myAdapter = new TypeAttractionAdapter(this, lst);
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(myAdapter);

        }

        public  void  findweather()
        {
            String url2= "http://api.openweathermap.org/data/2.5/weather?lat="+String.valueOf(RecyclerViewAdapter.lat)+"&lon="+String.valueOf(RecyclerViewAdapter.longitude)+
                    "&APPID=355d88eda3816b321cdcc61c9095610d&fbclid=IwAR3RrpRoDssWFHgyCgp-2Wu5pyWn-QTt5VoKJIJJkkUqEo3pqomDg62GcDE";

            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        JSONObject main_object = response.getJSONObject("main");
                        JSONArray array = response.getJSONArray("weather");


                        JSONObject obj = array.getJSONObject(0);
                        String icon =obj.getString("icon");
                        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

                        String temp = String.valueOf(main_object.getDouble("temp"));
                        Picasso.with(Type_Attraction_Activity.this)
                                .load(iconUrl).into(icontemp);

                        double temp_int =Double.parseDouble(temp);
                        double centi = (temp_int -273.15 );
                        centi= Math.round(centi);
                        int i = (int) centi;
                        temp1.setText(String.valueOf(i+"°C"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jor);
        }

    }

