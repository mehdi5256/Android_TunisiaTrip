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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Type_Attraction_Activity extends AppCompatActivity {
    BottomNavigationView bottom;
    private String URL_JSON = "http://192.168.1.22/tunisiatrip/select_type_attraction.php";
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Type_Attraction> lstAnime = new ArrayList<>();
    private RecyclerView myrv;



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
                        Toast.makeText(Type_Attraction_Activity.this, "map", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.experience:
                       Intent intent2 = new Intent(Type_Attraction_Activity.this, LoginActivity.class);
                        startActivity(intent2);
                        Toast.makeText(Type_Attraction_Activity.this, "exp", Toast.LENGTH_SHORT).show();
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

    }

