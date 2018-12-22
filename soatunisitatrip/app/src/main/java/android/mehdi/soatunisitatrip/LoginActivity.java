package android.mehdi.soatunisitatrip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.mehdi.soatunisitatrip.Common.Common;
import android.mehdi.soatunisitatrip.Common.SharedPrefManager;
import android.mehdi.soatunisitatrip.Model.APIResponse;
import android.mehdi.soatunisitatrip.Remote.IMyAPI;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button signupbtn;
    EditText mail, pass;
    ImageButton cnx;
    IMyAPI mService;
    CheckBox cb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Intent intent = new Intent(LoginActivity.this,ExperienceActivity.class);

        SharedPreferences prefs = getSharedPreferences( "login", MODE_PRIVATE);
        final  SharedPreferences.Editor editor = prefs.edit();

        if(prefs.contains("login")) {
            startActivity(intent);
            finish();
        }


        mService  = Common.getAPI();

        signupbtn = (Button) findViewById(R.id.signup);


        mail=(EditText)findViewById(R.id.email1);
        pass=(EditText)findViewById(R.id.password1);
        cnx = (ImageButton) findViewById(R.id.signin1);
        cb=(CheckBox) findViewById(R.id.checkbox1);




        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                finish();
            }
        });

        cnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mail.getText().length()== 0)
                {

                    Toast.makeText(LoginActivity.this, "Insérer nom utilisateur", Toast.LENGTH_SHORT).show();


                }
                else if (pass.getText().length()== 0)
                {

                    Toast.makeText(LoginActivity.this, "Insérer un mot de passe", Toast.LENGTH_SHORT).show();

                }
                else if(cb.isChecked()){
                    editor.putString("login",mail.getText().toString());
                    editor.putString("password",pass.getText().toString());
                    editor.commit();
                    finish();
                    authentificationUser(mail.getText().toString(),pass.getText().toString());


                }
                else {

                    authentificationUser(mail.getText().toString(), pass.getText().toString());
                }

            }
        });

    }

    private void authentificationUser(String email, String password) {
        mService.loginUser(email,password)
                .enqueue(new Callback<APIResponse>() {



                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

                        /*final Intent intent = new Intent(LoginActivity.this,ExperienceActivity.class);

                        SharedPreferences prefs = getSharedPreferences( "login", MODE_PRIVATE);
                        final  SharedPreferences.Editor editor = prefs.edit();

                        if(prefs.contains("login")) {
                            startActivity(intent);
                        }*/
                        APIResponse result = response.body();
                        if(result.isError()) {
                            Toast.makeText(LoginActivity.this, result.getError_msg(), Toast.LENGTH_SHORT).show();
                        }
                        else {

                            SharedPrefManager.getInstance(getApplicationContext())
                                    .userLogin(
                                            result.getUser().getId(),
                                            result.getUser().getName(),
                                            result.getUser().getEmail()
                                    );
                            /*editor.putString("login", mail.getText().toString());
                            editor.putString("password", pass.getText().toString());
                            editor.commit();
                            startActivity(intent);*/

                            Intent intent = new Intent(LoginActivity.this,ExperienceActivity.class);
                            startActivity(intent);


                            finish();
                        }


                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

}