package com.album.kaktus.virtualid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin,btnDaftar;
    EditText editEmail,editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekLogin();
            }
        });
        btnDaftar = findViewById(R.id.btnDaftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, daftarActivity.class);
                startActivity(myIntent);
            }
        });
    }

    void cekLogin() {
        String email = String.valueOf(editEmail.getText());
        String password = String.valueOf(editPassword.getText());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String storedEmail = preferences.getString("email", "");
        String storedPassword = preferences.getString("password", "");
        if (email.compareTo("")==0 || password.compareTo("")==0){
            Toast.makeText(getApplicationContext(),"Email atau Password tidak boleh kosong",Toast.LENGTH_LONG).show();
        }else if(email.compareTo(storedEmail) == 0 && password.compareTo(storedPassword) == 0) {
            Intent myIntent = new Intent(LoginActivity.this, TambahFotoActivity.class);
            startActivity(myIntent);
        }else{
            Toast.makeText(getApplicationContext(),"Email atau Password salah",Toast.LENGTH_LONG).show();
        }
    }
}
