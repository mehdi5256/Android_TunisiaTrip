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

public class testActivity extends AppCompatActivity {

    private String URL_JSON = "http://192.168.1.6/tunisiatrip/selectexperience.php?id1=2";
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<experience> lstAnime;

    private RecyclerView myrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        lstAnime = new ArrayList<>();
        myrv = (RecyclerView) findViewById(R.id.rv2);
        jsoncall();
    }

    private void jsoncall() {
        {


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
                            experience experience = new experience();

/*
                        experience.getId_user().setName(jsonObject.getString("nom"));
*/
                            experience.setNom(jsonObject.getString("nom"));
                            experience.setUpload_date(jsonObject.getString("upload_date"));
                            experience.setDescription(jsonObject.getString("description"));


                            lstAnime.add(experience);
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


            requestQueue = Volley.newRequestQueue(testActivity.this);
            requestQueue.add(ArrayRequest);


        }

    }

    private void setRvadapter(List<experience> lstAnime) {

        ExperienceAdapter experienceAdapter = new ExperienceAdapter(this, lstAnime);
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(experienceAdapter);

    }
}