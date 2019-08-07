package com.album.kaktus.virtualid;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class daftarActivity extends AppCompatActivity {

    EditText editEmail,editName,editNo;
    EditText editPass1,editPass2;
    Button btnDaftar;

    String email = "", name = "", phone = "";
    String pass1 = "", pass2 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        getSupportActionBar().hide();

        editEmail = findViewById(R.id.editEmail);
        editName = findViewById(R.id.editName);
        editNo = findViewById(R.id.editPhone);
        editPass1 = findViewById(R.id.editPassword);
        editPass2 = findViewById(R.id.editPassword2);
        btnDaftar = findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    insertData();
                }
            }
        });
    }

    boolean checkInput(){
        String mes = "";
        boolean bo = true;
        email = String.valueOf(editEmail.getText());
        name = String.valueOf(editName.getText());
        phone = String.valueOf(editNo.getText());
        pass1 = String.valueOf(editPass1.getText());
        pass2 = String.valueOf(editPass2.getText());

        if(email.compareTo("")==0){
            mes = "Email harus diisi";
            bo = false;
        }else if(name.compareTo("")==0){
            mes = "Nama harus diisi";
            bo = false;
        }else if(phone.compareTo("")==0){
            mes = "Nomor HP harus diisi";
            bo = false;
        }else if(pass1.compareTo("")==0 ||pass2.compareTo("")==0){
            mes = "Password harus diisi";
            bo = false;
        }else if(pass1.compareTo(pass2)!=0){
            mes = "Password yang dimasukkan tidak sama";
        }
        if(!bo)
            Toast.makeText(getApplicationContext(),mes,Toast.LENGTH_LONG).show();
        return bo;
    }

    void insertData(){
        SharedPreferences.Editor editor =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        editor.putString("email", email);
        editor.putString("name", name);
        editor.putString("phone", phone);
        editor.putString("pass", pass1);
        editor.apply();
        editor.commit();
    }
}
