package android.mehdi.soatunisitatrip;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExperienceActivity extends AppCompatActivity {
    Dialog epicDialog;
    ImageView close;
    Button signin,btnAcept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);
        TextView hello = (TextView) findViewById(R.id.textView2);
        Button btn = (Button) findViewById(R.id.button2);
        Button exp = (Button) findViewById(R.id.exp);

        epicDialog = new Dialog(this);


        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*
                showpositiveDialog();


*/
                Intent broswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
                startActivity(broswer);

            }
        });

        SharedPreferences sh = getSharedPreferences("login",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sh.edit();

        String login = sh.getString("mail","");
        String mdp = sh.getString("password","");
        String nom = sh.getString("nom","");

        hello.setText("Hello "+ nom + " mot de passe  :"+ mdp+ " login " + login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);
                final SharedPreferences.Editor editor = sh.edit();
                Intent intent = new Intent(ExperienceActivity.this, LoginActivity.class);
                editor.remove("login");
                editor.remove("password");
                editor.commit();

                startActivity(intent);
                finish();


            }
        });
    }

    private void showpositiveDialog() {

        epicDialog.setContentView(R.layout.epic_popup_positive);
        close=(ImageView) epicDialog.findViewById(R.id.close);
        btnAcept=(Button) epicDialog.findViewById(R.id.btnclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();

    }
}
