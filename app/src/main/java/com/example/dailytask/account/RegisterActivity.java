package com.example.dailytask.account;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dailytask.MainActivity;
import com.example.dailytask.R;
import com.example.dailytask.model.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_email, et_password;
    private Button btnDaftar;
    private String email, password;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //set Toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.orange));

        //set back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);

        // diatur sesuai id komponennya
        et_email    = findViewById(R.id.tvemail);
        et_password = findViewById(R.id.tv_pass);
        btnDaftar   = findViewById(R.id.btn_daftar);

        //variabel tadi untuk memanggil fungsi
        mDatabase   = FirebaseDatabase.getInstance().getReference();
        mAuth       = FirebaseAuth.getInstance();

        //nambahin method onClick, biar tombolnya bisa diklik
        btnDaftar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_daftar){
            signUp();
        }
    }

    private void signUp() {
        //validate the form
        if (!validateForm()){
            return;
        }

        //get value from editText
        email       = et_email.getText().toString();
        password    = et_password.getText().toString();

        //begin registration
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                            Toast.makeText(RegisterActivity.this, "Sign Up Successed",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        //write new admin to firebase
        writeNewAdmin(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

    private void writeNewAdmin(String uid, String username, String email) {
        Admin admin = new Admin(username, email);

        //push new user to Realtime database
        mDatabase.child("users").child(uid).setValue(admin);
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(et_email.getText().toString())) {
            et_email.setError("Required");
            result = false;
        } else {
            et_email.setError(null);
        }

        if (TextUtils.isEmpty(et_password.getText().toString())) {
            et_password.setError("Required");
            result = false;
        } else {
            et_password.setError(null);
        }

        return result;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}