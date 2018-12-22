package android.mehdi.soatunisitatrip;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.mehdi.soatunisitatrip.Model.User;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class  ExperienceActivity extends AppCompatActivity {

     FloatingActionButton floatbtn;
    int idville = RecyclerViewAdapter.a;

    BottomNavigationView bottom;



    private String URL_JSON = "http://192.168.1.5/tunisiatrip/selectexperience.php?id1=" + idville;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<experience> lstAnime;

    private RecyclerView myrv;

    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);
        bottom = findViewById(R.id.bottom5);

        floatbtn = (FloatingActionButton) findViewById(R.id.floatbtn);

        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExperienceActivity.this,AddExperienceActivity.class);
                startActivity(intent);
            }
        });

       /* TextView hello = (TextView) findViewById(R.id.textView2);
        Button btn = (Button) findViewById(R.id.button2);
        Button exp = (Button) findViewById(R.id.exp);



 */




        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.guide:
                        Intent intent2 = new Intent(ExperienceActivity.this, MainActivity.class);
                        startActivity(intent2);
                        finishAffinity();

                        break;

                    case R.id.map:

                        Intent intent1 = new Intent(ExperienceActivity.this, OpenStreetMap.class);
                        startActivity(intent1);
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


        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");

setSupportActionBar(toolbar);
        lstAnime = new ArrayList<>();
        myrv = (RecyclerView) findViewById(R.id.rvexp);
        jsoncall();



       /* exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

*//*
                showpositiveDialog();


*//*
                Intent broswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
                startActivity(broswer);

            }
        });*/

        SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sh.edit();

        String login = sh.getString("mail", "");
        String mdp = sh.getString("password", "");
        String nom = sh.getString("nom", "");

/*
        hello.setText("Hello "+ nom + " mot de passe  :"+ mdp+ " login " + login);
*/
        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);
                final SharedPreferences.Editor editor = sh.edit();
                Intent intent = new Intent(ExperienceActivity.this, LoginActivity.class);
                editor.remove("login");
                editor.remove("password");
                editor.commit();

                startActivity(intent);
                finish();*/

/*
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deconnexion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sh.edit();
        Intent intent = new Intent(ExperienceActivity.this, LoginActivity.class);
        editor.remove("login");
        editor.remove("password");
        editor.commit();

        startActivity(intent);
        finish();

        return true;
    }




    private void jsoncall() {

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
                        experience experience = new experience();

/*
                        experience.getId_user().setName(jsonObject.getString("nom"));

*/                      experience.setId(Integer.parseInt(jsonObject.getString("id")));
                        experience.setNom(jsonObject.getString("nom"));
                        experience.setEmail(jsonObject.getString("email"));
                        experience.setUpload_date(jsonObject.getString("upload_date"));
                        experience.setNom_image(jsonObject.getString("image"));
                        experience.setDescription(jsonObject.getString("description"));


                        lstAnime.add(experience);
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


        requestQueue = Volley.newRequestQueue(ExperienceActivity.this);
        requestQueue.add(ArrayRequest);


    }

    private void setRvadapter(List<experience> lstAnime) {

        ExperienceAdapter experienceAdapter = new ExperienceAdapter(this,lstAnime);
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(experienceAdapter);


    }

}
