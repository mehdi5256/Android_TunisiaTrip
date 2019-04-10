package android.mehdi.soatunisitatrip;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.mehdi.soatunisitatrip.Common.SharedPrefManager;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class  ExperienceActivity extends AppCompatActivity {


    //Drawable NavBar
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;
    private TextView currusername;
    private CircleImageView NavProfileImage ;
    //

    FloatingActionButton floatbtn;

    int idville = RecyclerViewAdapter.a;

    BottomNavigationView bottom;



    private String URL_JSON = "http://41.226.11.252:1180/tunisiatrip/selectexperience.php?id1=" + idville;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<experience> lstAnime;

    private RecyclerView myrv;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        bottom = findViewById(R.id.bottom5);

        //Drawable navBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar = (Toolbar)findViewById(R.id.main_app_bar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Accueil");

        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(ExperienceActivity.this,drawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_view) ;
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });

        currusername = navView.findViewById(R.id.nav_user_full_name);
        currusername.setText(SharedPrefManager.getInstance(this).getUserName());
        NavProfileImage = (CircleImageView) navView.findViewById(R.id.nav_profile_image);
        Picasso.with(ExperienceActivity.this)
                .load(SharedPrefManager.getInstance(this).getPhotoProfil()).into(NavProfileImage);

        //Drawable Nav End
        floatbtn = (FloatingActionButton) findViewById(R.id.floatbtn);
        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExperienceActivity.this,AddExperienceActivity.class);
                startActivity(intent);
            }
        });


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

                        Intent intent1 = new Intent(ExperienceActivity.this, Favoris_activity.class);
                        startActivity(intent1);
                        break;


                }
                return false;
            }
        });


        lstAnime = new ArrayList<>();
        myrv = (RecyclerView) findViewById(R.id.rvexp);
        jsoncall();



        SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sh.edit();

        String login = sh.getString("mail", "");
        String mdp = sh.getString("password", "");
        String nom = sh.getString("nom", "");


    }
    //Drawable navBar button functions
    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()){


            case R.id.nav_home:
                Intent intent2 = new Intent(ExperienceActivity.this, MainActivity.class);
                startActivity(intent2);
                finishAffinity();
                break;

            case R.id.nav_addExp:
                Intent add = new Intent(ExperienceActivity.this,AddExperienceActivity.class);
                startActivity(add);
                break;

            case R.id.nav_favoris:
                Intent intent1 = new Intent(ExperienceActivity.this, Favoris_activity.class);
                startActivity(intent1);
                break;

            case R.id.nav_logout:
                SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);
                final SharedPreferences.Editor editor = sh.edit();
                Intent intent = new Intent(ExperienceActivity.this, LoginActivity.class);
                editor.remove("login");
                editor.remove("password");
                editor.commit();

                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }




    private void jsoncall() {

        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0 ; i<response.length();i++) {

                    try {

                        jsonObject = response.getJSONObject(i);
                        experience experience = new experience();

                     experience.setId(Integer.parseInt(jsonObject.getString("id")));
                        experience.setNom(jsonObject.getString("nom"));
                        experience.setEmail(jsonObject.getString("email"));
                        experience.setImage_profil(jsonObject.getString("photoprofil"));
                        experience.setUpload_date(jsonObject.getString("upload_date"));
                        experience.setNom_image(jsonObject.getString("image"));
                        experience.setDescription(jsonObject.getString("description"));


                        lstAnime.add(experience);
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


        requestQueue = Volley.newRequestQueue(ExperienceActivity.this);
        requestQueue.add(ArrayRequest);


    }

    private void setRvadapter(List<experience> lstAnime) {

        ExperienceAdapter experienceAdapter = new ExperienceAdapter(this,lstAnime);
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(experienceAdapter);


    }

}
