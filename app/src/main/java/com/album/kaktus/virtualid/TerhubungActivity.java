package com.album.kaktus.virtualid;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.album.kaktus.virtualid.Adapter.ContactAdapter;
import com.album.kaktus.virtualid.Model.Contact;

import java.util.ArrayList;

public class TerhubungActivity extends AppCompatActivity {

    Button btnTerhubung,btnSkip;
    DBHandler db;
    RecyclerView rc;
    ContactAdapter adapter;
    ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terhubung);
        getSupportActionBar().hide();

        db = new DBHandler(getApplicationContext());
        btnTerhubung = findViewById(R.id.btnTerhubung);
        btnTerhubung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TerhubungActivity.this, KebutuhanAcitivity.class);
                startActivity(myIntent);
            }
        });

        btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TerhubungActivity.this, KebutuhanAcitivity.class);
                startActivity(myIntent);
            }
        });

        rc = findViewById(R.id.scrollView);
        adapter = new ContactAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        rc.setAdapter(adapter);
        // Set layout manager to position the items
        rc.setLayoutManager(new LinearLayoutManager(this));

        if (ContextCompat.checkSelfPermission(TerhubungActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("Get Contact", "Permission not granted");
            ActivityCompat.requestPermissions(TerhubungActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},1);
        }else {
            Log.d("Get Contact", "Coba ambil kontak");
            getContactList();
            Toast.makeText(TerhubungActivity.this, "Contact data has been printed in the android monitor log..", Toast.LENGTH_SHORT).show();
        }
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            int itr = 0;
            while (cur != null && cur.moveToNext() && itr < 20) {
                itr++;
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                String uriPicture = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.PHOTO_URI));

                int idProfile = 0;
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Contact c = new Contact();
                    c.setName(name);
                    if(uriPicture != null){
                        c.setPicURI(uriPicture);
                    }
//                    idProfile = db.insertProfile(c);
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    int itr2 = 0;
                    String phone = "";
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if(itr2 > 0){
                            phone += "\n";
                        }
                        phone += phoneNo;
//                        db.insertNumber(idProfile,phoneNo);
                        Log.d("Get Contact", "Name: " + name);
                        Log.d("Get Contact", "Phone Number: " + phoneNo);
                        itr2++;
                    }
                    c.setNumber(phone);
                    pCur.close();
                    contacts.add(c);
                }
            }
            adapter.notifyDataSetChanged();
            btnTerhubung.setText("TERHUBUNG DENGAN " + String.valueOf(contacts.size()) + " ORANG");
        }
        if(cur!=null){
            cur.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),"Permission Granted", Toast.LENGTH_SHORT).show();
                    getContactList();
                } else {
                    Toast.makeText(getApplicationContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
