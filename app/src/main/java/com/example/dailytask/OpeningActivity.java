package com.example.dailytask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OpeningActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    
    private Button btnget;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        btnget = findViewById(R.id.btn_get);

        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Perintah Intent Explicit pindah halaman ke activity_login
              startActivity(new Intent(OpeningActivity.this, LoginActivity.class));
            }
        });

    }
}