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
    private String URL_JSON = "http://192.168.1.6/tunisiatrip/select.php";
    private JsonArrayRequest ArrayRequest ;
    private RequestQueue requestQueue ;
    private List<Ville> lstAnime = new ArrayList<>();
    private RecyclerView myrv ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myrv = findViewById(R.id.rv);
        jsoncall();




    }

    public void jsoncall() {


        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0 ; i<response.length();i++) {

/*
                    Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();
*/

                    try {

                        jsonObject = response.getJSONObject(i);
                        Ville ville = new Ville();
                        ville.setId(jsonObject.getInt("id"));
                        ville.setNomville(jsonObject.getString("nomville"));
                        ville.setImage(jsonObject.getString("image"));

                        lstAnime.add(ville);
                    }
                    catch (JSONException e) {
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


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(ArrayRequest);
    }



    public void setRvadapter (List<Ville> lst) {

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lst) ;
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(myAdapter);




    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }*/
}