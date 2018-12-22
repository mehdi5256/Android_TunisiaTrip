package android.mehdi.soatunisitatrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttractionParVille extends AppCompatActivity {

int idville =  RecyclerViewAdapter.a;
int id_attraction = TypeAttractionAdapter.id_attraction;

    private String URL_JSON = "http://192.168.1.5/tunisiatrip/selectAttractionById.php?id1="+idville+"&id2="+id_attraction ;



/*
private String URL_JSON = "http://192.168.1.6/tunisiatrip/selectAttractionById.php?id1=1&id2=1";
*/
    private JsonArrayRequest ArrayRequest ;
    private RequestQueue requestQueue ;
    private List<Attraction> lstAttraction = new ArrayList<>();
    private RecyclerView myrvatt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_par_ville);

        myrvatt= findViewById(R.id.rviewAttraction);

        jsoncall();

    }
    public void jsoncall() {


        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0; i < response.length(); i++) {


                    try {

                        jsonObject = response.getJSONObject(i);
                        Attraction ville = new Attraction();
                        ville.setNomAttraction(jsonObject.getString("nomattraction"));
                        ville.setImage(jsonObject.getString("image"));
                        ville.setDescription(jsonObject.getString("description"));
                        ville.setSiteweb(jsonObject.getString("siteweb"));
                        ville.setAdresse(jsonObject.getString("adresse"));
                        ville.setMail(jsonObject.getString("mail"));
                        ville.setTelephone(jsonObject.getString("telephone"));
                        ville.setLatitude((float) jsonObject.getDouble("latitude"));
                        ville.setLongitude((float) jsonObject.getDouble("longitude"));









                        lstAttraction.add(ville);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                setRvadapter(lstAttraction);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(AttractionParVille.this);
        requestQueue.add(ArrayRequest);

    }

    private void setRvadapter(List<Attraction> lstAttraction) {


        AttractionByVilleAdapter attractionByVilleAdapter = new AttractionByVilleAdapter(this,lstAttraction);
        myrvatt.setLayoutManager(new LinearLayoutManager(this));
        myrvatt.setAdapter(attractionByVilleAdapter);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);





    }*/
}



/*

ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
@Override
public void onResponse(JSONArray response) {

        JSONObject jsonObject = null;


        for (int i = 0 ; i<response.length();i++) {

*/
/*
                    Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();
*//*


        try {

        jsonObject = response.getJSONObject(i);
        Attraction attraction = new Attraction();
        attraction.setNomAttraction(jsonObject.getString("nomattraction"));
        attraction.setAdresse(jsonObject.getString("adresse"));
        attraction.setImage(jsonObject.getString("image"));

        lstAttraction.add(attraction);
        }
        catch (JSONException e) {
        e.printStackTrace();
        }
        }


        //Toast.makeText(MainActivity.this,"Size of Liste "+String.valueOf(lstAnime.size()),Toast.LENGTH_SHORT).show();

        //Toast.makeText(MainActivity.this,lstAnime.get(1).toString(),Toast.LENGTH_SHORT).show();

        setRvadapter(lstAttraction);
        }
        }, new Response.ErrorListener() {
@Override
public void onErrorResponse(VolleyError error) {

        }
        });


        requestQueue = Volley.newRequestQueue(AttractionParVille.this);
        requestQueue.add(ArrayRequest);
        }



public void setRvadapter (List<Attraction> lst) {

        AttractionByVilleAdapter myAdapter = new AttractionByVilleAdapter(this,lst) ;
        myrvatt.setLayoutManager(new LinearLayoutManager(this));
        myrvatt.setAdapter(myAdapter);*/
