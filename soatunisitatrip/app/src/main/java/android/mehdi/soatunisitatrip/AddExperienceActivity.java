package android.mehdi.soatunisitatrip;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.mehdi.soatunisitatrip.Common.SharedPrefManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;


import java.util.Calendar;
import java.util.UUID;

public class AddExperienceActivity extends AppCompatActivity {

    ImageView imageView;
    EditText editTextName ;
    Button btng;
    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private Bitmap bitmap;

    int year,month,day;

    public static String update_date;

     String id_u = String.valueOf(ExperienceAdapter.id_usr);

    public static final String UPLOAD_URL = "http://41.226.11.252:1180/tunisiatrip/upload.php";

    AwesomeValidation awesomeValidation;
    Dialog epicdialog;
    Button btnsucces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experience);

        android.support.v7.widget.Toolbar tb = (android.support.v7.widget.Toolbar) findViewById(R.id.tbback);
        setSupportActionBar(tb);
        tb.setLogo(R.drawable.icon_logo1);
        tb.setTitle("");
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            tb.setTitle("");
        }

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        epicdialog = new Dialog(this);



        Calendar calendar = Calendar.getInstance();
         year  = calendar.get(Calendar.YEAR);
         month = calendar.get(Calendar.MONTH)+1;
         day = calendar.get(Calendar.DAY_OF_MONTH);
        update_date = String.valueOf((year+"-"+month+"-"+day));



        requestStoragePermission();


        imageView = (ImageView) findViewById(R.id.imgex);
        editTextName = (EditText) findViewById(R.id.etdescription);
        btng=(Button) findViewById(R.id.btngallery);


        awesomeValidation.addValidation(AddExperienceActivity.this,R.id.etdescription,"[a-zA-Z\\s]+",R.string.dsesc);

    }

    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void ShowFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {

            filepath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);

            } catch (Exception ex) {

            }
        }
    }

    public void selectImage(View view) {


        ShowFileChooser();
    }

    private String getPath(Uri uri) {

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
        );
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }



    public void saveData(View view)

    {

        if (imageView.getVisibility() == View.GONE){
            Snackbar.make(view, "Upload une image",
                    Snackbar.LENGTH_SHORT).show();
        }else{

            if(awesomeValidation.validate()) {
                //uploadImage();

              showpositiveAlert();
            }
        }




    }

    private void showpositiveAlert() {
        epicdialog.setContentView(R.layout.popup_ville);
        btnsucces = (Button) epicdialog.findViewById(R.id.btnoui1);
        btnsucces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddExperienceActivity.this, ExperienceActivity.class);
                onRestart();
                startActivity(intent);
                finishAffinity();

            }
        });

        epicdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicdialog.show();

    }


    private void uploadImage() {
        String name = editTextName.getText().toString().trim();
        String upload_date = update_date;
        String path =getPath(filepath);
        String id_user= String.valueOf(SharedPrefManager.getInstance(this).getUserId());
        String id_ville= String.valueOf(RecyclerViewAdapter.a);

        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image")
                    .addParameter("description", name)
                    .addParameter("upload_date", upload_date)
                    .addParameter("id_user", id_user)
                    .addParameter("id_ville", id_ville)

                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(5)
                    .startUpload();

        } catch (Exception Ex) {

            System.out.println("MehdiError" );


        }

    }


}
