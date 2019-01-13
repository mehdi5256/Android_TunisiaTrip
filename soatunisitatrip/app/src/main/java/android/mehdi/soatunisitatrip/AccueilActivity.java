package android.mehdi.soatunisitatrip;

import android.content.Intent;
import android.mehdi.soatunisitatrip.Accueil.AdapterViewPager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
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
import java.util.Timer;
import java.util.TimerTask;

public class AccueilActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout slidedots;
    private  int dotscount;
    private ImageView[] dots;
    private int mCurrentpage;
    RequestQueue rq;
    List<Ville> sliderimg;
    Button decouvirir;
    Button next,prec;
    String requestUrl="http://192.168.1.8/tunisiatrip/selectAccueil.php";
    AdapterViewPager adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        decouvirir=(Button) findViewById(R.id.angry_btn) ;
        next=(Button) findViewById(R.id.next);
        prec=(Button) findViewById(R.id.prec);


        rq = Volley.newRequestQueue(this);
        sliderimg = new ArrayList<>();

        viewPager =(ViewPager) findViewById(R.id.viewpager);

        slidedots=(LinearLayout) findViewById(R.id.sliderdots);
        sendReqestUrl();



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mCurrentpage+1);
            }
        });
        prec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mCurrentpage-1);

            }
        });
        decouvirir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AccueilActivity.this, MainActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.goup,R.anim.godown);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i=0 ; i<dotscount;i++)
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
            }

            @Override
            public void onPageSelected(int position) {

                mCurrentpage=position;
                if(position==0)
                {
                    next.setEnabled(true);
                    prec.setEnabled(false);
                    prec.setVisibility(View.INVISIBLE);
                    next.setText("Suivant");
                    prec.setText("");
                }
                else if (position== dots.length -1)
                {
                    next.setEnabled(true);
                    prec.setEnabled(true);
                    prec.setVisibility(View.VISIBLE);
                    next.setText("Fin");
                    prec.setText("Précedent");
                }

                else{

                    next.setEnabled(true);
                    prec.setEnabled(true);
                    prec.setVisibility(View.VISIBLE);
                    next.setText("Suivant");
                    prec.setText("Précedent");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerMask(),1000000,5000);
    }

    public class  MyTimerMask extends TimerTask{

        @Override
        public void run() {
            AccueilActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    }
                    else if(viewPager.getCurrentItem()==1)
                    {
                        viewPager.setCurrentItem(2);
                    }
                    else
                    {
                        viewPager.setCurrentItem(0);
                    }


                }});
        }

    }

    public void sendReqestUrl(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, requestUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for(int i=0; i<response.length();i++)
                {
                    Ville ville = new Ville();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ville.setId(Integer.parseInt(jsonObject.getString("id")));
                        ville.setImage(jsonObject.getString("image"));
/*
                        ville.setNomville(jsonObject.getString("name"));
*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sliderimg.add(ville);
                }

                 adapterViewPager = new AdapterViewPager(sliderimg,AccueilActivity.this);
                viewPager.setAdapter(adapterViewPager);

                dotscount  = adapterViewPager.getCount();
                dots= new ImageView[dotscount];
                for(int i=0;i<dotscount;i++)
                {
                    dots[i]= new ImageView(AccueilActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8,0,8,0);
                    slidedots.addView(dots[i],params);
                }
                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        rq.add(jsonArrayRequest);
    }
}

