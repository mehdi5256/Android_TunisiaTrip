package android.mehdi.soatunisitatrip;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
/*
import com.demotxt.myapp.myapplication.R;
import com.demotxt.myapp.myapplication.adapter.RvAdapter;
import com.demotxt.myapp.myapplication.model.Anime;
*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String URL_JSON = "http://41.226.11.252:1180/tunisiatrip/select.php";
    private JsonArrayRequest ArrayRequest ;
    private RequestQueue requestQueue ;
    private List<Ville> lstAnime = new ArrayList<>();
    private RecyclerView myrv ;
    Toolbar toolbar;
      Dialog epicDialog;
      TextView tvnomville,dscville,guide;
      Button oui,non;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        epicDialog = new Dialog(this);
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setLogo(R.drawable.icon_logo1);
        myrv = findViewById(R.id.rv);
        jsoncall();




    }

    public void jsoncall() {


        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0 ; i<response.length();i++) {

                    try {

                        jsonObject = response.getJSONObject(i);
                        Ville ville = new Ville();
                        ville.setId(jsonObject.getInt("id"));
                        ville.setNomville(jsonObject.getString("nomville"));
                        ville.setImage(jsonObject.getString("image"));
                        ville.setLatitude(jsonObject.getString("latitude"));
                        ville.setLongitude(jsonObject.getString("Longitude"));


                        lstAnime.add(ville);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                setRvadapter(lstAnime);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(ArrayRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.godown,R.anim.goup);
    }

    public void setRvadapter (List<Ville> lst) {

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lst) ;
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(myAdapter);




    }


   public void  showVilleDialog() {

       oui.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,Type_Attraction_Activity.class);

               startActivity(intent);


           }
       });
       non.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               epicDialog.dismiss();
           }
       });
       dscville.setText("Voulez vous commencer votre voyage à"+RecyclerViewAdapter.nom_ville);
       tvnomville.setText(RecyclerViewAdapter.nom_ville);
       epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       epicDialog.show();
   }
   }