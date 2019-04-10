package android.mehdi.soatunisitatrip;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.mehdi.soatunisitatrip.SQLite.FavoriteDbHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonArrayRequest;

import java.util.ArrayList;
import java.util.List;

public class Favoris_activity extends AppCompatActivity {

    int id_attraction = TypeAttractionAdapter.id_attraction;

    private String URL_JSON = "http://41.226.11.252:1180/tunisiatrip/selectAttraction.php?id1=" + id_attraction;

    private AttractionByVilleAdapter adapter;
    private List<Attraction> lstAttraction = new ArrayList<>();
    private AppCompatActivity activity = Favoris_activity.this;
    private FavoriteDbHelper favoriteDbHelper;
    private RecyclerView myrvatt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris_activity);


        /* tooolbar */
        android.support.v7.widget.Toolbar tb = (android.support.v7.widget.Toolbar) findViewById(R.id.tbfav);
        setSupportActionBar(tb);
        tb.setLogo(R.drawable.hear);
        tb.setTitle("");
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle("Favori");
            tb.setTitle("   Favoris");
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        myrvatt = findViewById(R.id.rvv);

        adapter = new AttractionByVilleAdapter(this, lstAttraction);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            myrvatt.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            myrvatt.setLayoutManager(new GridLayoutManager(this, 4));
        }

        myrvatt.setItemAnimator(new DefaultItemAnimator());
        myrvatt.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        favoriteDbHelper = new FavoriteDbHelper(activity);

        getAllFavorite();
        setRvadapter(lstAttraction);

    }

    private void setRvadapter(List<Attraction> lstAttraction) {


        AttractionByVilleAdapter attractionByVilleAdapter = new AttractionByVilleAdapter(this, lstAttraction);
        myrvatt.setLayoutManager(new LinearLayoutManager(this));
        myrvatt.setAdapter(attractionByVilleAdapter);
    }

    public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;

    }

    private void getAllFavorite(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params){
                lstAttraction.clear();
                lstAttraction.addAll(favoriteDbHelper.getAllFavorite());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }


}