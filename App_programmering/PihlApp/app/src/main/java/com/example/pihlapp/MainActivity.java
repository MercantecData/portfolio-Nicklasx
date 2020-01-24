package com.example.pihlapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    // Det bliver brugt til kamera
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 1;
    Button photo, valg;
    ImageView imageView;
    Uri image_uri;

    //start
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // variabler til de forskellige ting i appen
        final Switch sw1 = findViewById(R.id.DarkMode);
        final TextView txt = findViewById(R.id.hello);
        final View main = findViewById(R.id.test);
        final TableLayout t1 = findViewById(R.id.table);
        final TableLayout t2 = findViewById(R.id.table2);
        final TableLayout t3 = findViewById(R.id.table3);
        final EditText user = findViewById(R.id.username);
        final EditText pass = findViewById(R.id.password);
        final Button login = findViewById(R.id.login);
        final Button logout = findViewById(R.id.logout);
        final Button kamera = findViewById(R.id.kamera);
        final Button getLocation = findViewById(R.id.location);
        photo = findViewById(R.id.photo);
        valg = findViewById(R.id.valg);
        final Button back = findViewById(R.id.back);
        final TextView userTxt = findViewById(R.id.usernameTxt);
        imageView = findViewById(R.id.image);

        // sætter table 2 og 3 til at være hidden
        t2.setVisibility(t2.INVISIBLE);
        t3.setVisibility(t3.INVISIBLE);

        // dette er til at skifte mellem dark mode og light mode
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton SwitchView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    sw1.setChecked(true);
                    txt.setText(R.string.dark_mode);
                    txt.setTextColor(getColor(R.color.Light));
                    main.setBackgroundColor(getColor(R.color.Dark));
                    sw1.setTextColor(getColor(R.color.Light));
                    sw1.setText("Dark mode on");
                    user.setTextColor(getColor(R.color.Light));
                    pass.setTextColor(getColor(R.color.Light));
                    userTxt.setTextColor(getColor(R.color.Light));
                    user.setHintTextColor(getColor(R.color.Light));
                    pass.setHintTextColor(getColor(R.color.Light));
                } else {
                    // The toggle is disabled
                    sw1.setChecked(false);
                    txt.setText(R.string.light_mode);
                    txt.setTextColor(getColor(R.color.Dark));
                    main.setBackgroundColor(getColor(R.color.Light));
                    sw1.setTextColor(getColor(R.color.Dark));
                    sw1.setText("Dark mode off");
                    user.setTextColor(getColor(R.color.Dark));
                    pass.setTextColor(getColor(R.color.Dark));
                    userTxt.setTextColor(getColor(R.color.Dark));
                    user.setHintTextColor(getColor(R.color.Dark));
                    pass.setHintTextColor(getColor(R.color.Dark));
                }
            }
        });

        //hardcoded bruger til at logge ind
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().equals("admin") &&
                        pass.getText().toString().equals("admin")) {
                    userTxt.setText(user.getText());
                    //table 1 bliver hidden og 2 kommer frem
                    t1.setVisibility(t1.INVISIBLE);
                    t2.setVisibility(t2.VISIBLE);
                }else{
                    // kommer frem vis du skriver forkert når du logger ind
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // log ud knap hidder table 2 og 3 og gør table 1 synlig
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setVisibility(t2.INVISIBLE);
                t1.setVisibility(t1.VISIBLE);
                t3.setVisibility(t3.INVISIBLE);
            }
        });

        // kamera knap gør table 2 hidden og table 3 synlig
        kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setVisibility(t2.INVISIBLE);
                t3.setVisibility(t3.VISIBLE);
            }
        });

        // get location knap åbner google maps
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", 56.464923,9.411768);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        // tag billed knap
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hvis OS er nyere eller er marshmallow, spørg om lov til at bruge kamera og gallari
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        //permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        openCamera();
                    }
                }
                else {
                    //system os < marshmallow
                    openCamera();
                }
            }
        });

        valg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        // back knap gør table 2 synlig og table 3 hidden
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setVisibility(t2.VISIBLE);
                t3.setVisibility(t3.INVISIBLE);
            }
        });
    }
    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    //åben kamera
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera();
                }
                else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //called when image was captured from camera

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //set the image captured to our ImageView
            imageView.setImageURI(image_uri);
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                    break;

            }
        }
    }
}

