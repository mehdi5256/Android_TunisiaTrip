package android.mehdi.soatunisitatrip;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.mehdi.soatunisitatrip.Common.Common;
import android.mehdi.soatunisitatrip.Model.APIResponse;
import android.mehdi.soatunisitatrip.Remote.IMyAPI;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Button signin,btnAcept;
    EditText mail1,pass,nom;
    ImageButton cnx;
    IMyAPI mService;
    CheckBox term;
    Dialog epicDialog;
    ImageView close;
    TextView txtwelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mService  = Common.getAPI();



        signin = (Button) findViewById(R.id.signin);
        mail1=(EditText)findViewById(R.id.email);
        nom=(EditText)findViewById(R.id.name);

        pass=(EditText)findViewById(R.id.password2);
        cnx = (ImageButton) findViewById(R.id.signup1);
        term = (CheckBox) findViewById(R.id.terms);

        epicDialog = new Dialog(this);


        cnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nom.getText().length()== 0)
                {

                    Toast.makeText(SignUpActivity.this, "Insérer un nom d'utilisateur", Toast.LENGTH_SHORT).show();


                }
                else if (mail1.getText().length()== 0)
                {

                    Toast.makeText(SignUpActivity.this, "Insérer une adresse mail", Toast.LENGTH_SHORT).show();


                }
                else if (pass.getText().length()== 0)
                {

                    Toast.makeText(SignUpActivity.this, "Insérer un mot de passe", Toast.LENGTH_SHORT).show();

                }



                else{
                    registerUser(nom.getText().toString(),mail1.getText().toString(),pass.getText().toString());

                }


            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser(String name, String mail, String password) {
        mService.RegisterUser(name,mail,password)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

                        APIResponse result = response.body();
                        if(result.isError()) {
                            Toast.makeText(SignUpActivity.this, result.getError_msg(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {


                            showpositiveDialog();


                            //Toast.makeText(SignUpActivity.this, "User CREATED" +result.getUid(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {




                        Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
    }

    private void showpositiveDialog() {

        final Intent intent = new Intent(SignUpActivity.this,ExperienceActivity.class);

        SharedPreferences prefs = getSharedPreferences( "login", MODE_PRIVATE);
        final  SharedPreferences.Editor editor = prefs.edit();

        if(prefs.contains("login")) {
            startActivity(intent);
            finish();
        }

        epicDialog.setContentView(R.layout.epic_popup_positive);
        close=(ImageView) epicDialog.findViewById(R.id.close);
        btnAcept=(Button) epicDialog.findViewById(R.id.btnclose);
        txtwelcome =(TextView) epicDialog.findViewById(R.id.tvwelcome);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("password", pass.getText().toString());
                editor.putString("nom", nom.getText().toString());
                editor.putString("mail", mail1.getText().toString());


                editor.commit();
                epicDialog.dismiss();
                startActivity(intent);
                finish();            }
        });


        btnAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("password", pass.getText().toString());
                editor.putString("nom", nom.getText().toString());
                editor.putString("mail", mail1.getText().toString());


                editor.commit();
                epicDialog.dismiss();
                startActivity(intent);
                finish();
            }
        });
        txtwelcome.setText("Bienvenue "+nom.getText().toString());
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();

    }


}
