package android.mehdi.soatunisitatrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfilActivity extends AppCompatActivity {


    int idville = RecyclerViewAdapter.a;
    int user_id =  ExperienceAdapter.id_usr;
    String name_user = ExperienceAdapter.nom_usr;
    String em_user= ExperienceAdapter.email_usr;
    String photo_pro = ExperienceAdapter.photo_usr;


    private String URL_JSON = "http://41.226.11.252:1180/tunisiatrip/userprofil.php?id1="+idville+"&id2="+user_id;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<UserProfil> lstAnime  = new ArrayList<>();

    private RecyclerView myrv;
     TextView nom_user,mail_user;
     CircleImageView photoprofil1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);


/*
00*/



        myrv = (RecyclerView) findViewById(R.id.rvuser);
        nom_user= (TextView) findViewById(R.id.nomuser) ;
        mail_user= (TextView) findViewById(R.id.mailuser);
        photoprofil1=(CircleImageView)findViewById(R.id.photoprofil);

        nom_user.setText(name_user);
        mail_user.setText(em_user);


        Glide.with(UserProfilActivity.this).load(photo_pro).into(photoprofil1);

        jsoncall();


    }

    private void jsoncall() {
        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0 ; i<response.length();i++) {


                    try {

                        jsonObject = response.getJSONObject(i);
                        UserProfil experience = new UserProfil();

                        experience.setNom(jsonObject.getString("nom"));
                        experience.setEmail(jsonObject.getString("email"));
                        experience.setUpload_date(jsonObject.getString("upload_date"));
                        experience.setImage(jsonObject.getString("image"));
                        experience.setPhotoprofil(jsonObject.getString("photoprofil"));

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


        requestQueue = Volley.newRequestQueue(UserProfilActivity.this);
        requestQueue.add(ArrayRequest);


    }

    private void setRvadapter(List<UserProfil> lstAnime) {

        UserAdapter experienceAdapter = new UserAdapter(this,lstAnime);
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(experienceAdapter);


    }

}