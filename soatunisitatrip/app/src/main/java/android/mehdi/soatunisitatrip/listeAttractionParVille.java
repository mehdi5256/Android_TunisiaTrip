
package android.mehdi.soatunisitatrip;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class listeAttractionParVille extends AppCompatActivity {

    TextView tvnom_attraction,adresse_att,description_att;
    ImageView image_attrac, precedent,teleph,mail,site;
    int a =Integer.parseInt( AttractionByVilleAdapter.tel);
    BottomNavigationView btm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_attraction_par_ville);

        String nom_attraction = getIntent().getExtras().getString("attraction_name");
        String image = getIntent().getExtras().getString("attraction_image");
        String descrition = getIntent().getExtras().getString("attraction_description");
        String siteweb = getIntent().getExtras().getString("attraction_siteweb");
        String email = getIntent().getExtras().getString("attraction_email");
        int telephone = getIntent().getExtras().getInt("attraction_telephone");
        String Adresse = getIntent().getExtras().getString("attraction_adresse");



        /*---------------------------------------------------------------------------------*/


        tvnom_attraction = (TextView) findViewById(R.id.tvnom);
        image_attrac = (ImageView) findViewById(R.id.imageView4);
        description_att = (TextView) findViewById(R.id.textView7);
       /* site= (ImageView) findViewById(R.id.site);
        mail=(ImageView) findViewById(R.id.mail);
        teleph=(ImageView)findViewById(R.id.tele);*/
        adresse_att = (TextView) findViewById(R.id.textView6);

        precedent = (ImageView) findViewById(R.id.imageView2);

            btm= (BottomNavigationView) findViewById(R.id.bottom1);


            btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.web:
                            Intent broswer = new Intent(Intent.ACTION_VIEW, Uri.parse(siteweb));
                            startActivity(broswer);

                            break;
                        case R.id.call:
                            Intent call = new Intent(Intent.ACTION_DIAL);
                            call.setData(Uri.parse("tel:" + a));
                            startActivity(call);
                            break;


                        case R.id.meail:
                            String[] TO = {email};
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("text/plain");


                            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

                            try {
                                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                                finish();
                                System.out.println("Finished sending email...");
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(listeAttractionParVille.this,
                                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
                            }



                    }

                        return false;
                }
            });

        /*------------------------------------------------------------------------------------*/

        tvnom_attraction.setText(nom_attraction);
        description_att.setText(descrition);
        adresse_att.setText(Adresse);
        Glide.with(this).load(image).into(image_attrac);


        /*----------------------------*/


        precedent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*---------------------------------------------------*/


    }

}

