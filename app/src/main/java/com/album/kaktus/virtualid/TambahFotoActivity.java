package com.album.kaktus.virtualid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TambahFotoActivity extends AppCompatActivity {

    Button btnTambah,btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_foto);
        getSupportActionBar().hide();

        btnTambah = findViewById(R.id.btnTambahFoto);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(TambahFotoActivity.this,
                            new String[]{Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }else {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent,1);
                }
            }
        });

        btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TambahFotoActivity.this, TerhubungActivity.class);
                startActivity(myIntent);
            }
        });
    }

    Boolean checkPermission(){
        if(ContextCompat.checkSelfPermission(TambahFotoActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            return true;
        }else if(ContextCompat.checkSelfPermission(TambahFotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            return true;
        }else if(ContextCompat.checkSelfPermission(TambahFotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(data.getExtras() != null){
                try{
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    // Assume block needs to be inside a Try/Catch block.
                    String path = Environment.getExternalStorageDirectory().toString();
                    OutputStream fOut = null;
                    Integer counter = 0;
                    File file = new File(path, "Profile.jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
                    fOut = new FileOutputStream(file);

                    image.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                    fOut.flush(); // Not really required
                    fOut.close(); // do not forget to close the stream

                    MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                    Toast.makeText(getApplicationContext(),"Picture saved successfully",Toast.LENGTH_SHORT).show();

                    Intent myIntent = new Intent(TambahFotoActivity.this, TerhubungActivity.class);
                    startActivity(myIntent);
                }catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"File Not Found",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Failed to save picture",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(),"No Picture",Toast.LENGTH_SHORT).show();
            }

        }else if(requestCode == 2){
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent,1);
        }
    }
}
