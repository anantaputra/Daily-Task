package com.example.dailytask.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailytask.MainActivity;
import com.example.dailytask.OpeningActivity;
import com.example.dailytask.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private EditText edtEmail, edtPass;
    private Button btnMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set Toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.orange));

        // diatur sesuai id komponennya
        register    = findViewById(R.id.register_link);
        edtEmail    = findViewById(R.id.tvemail);
        edtPass     = findViewById(R.id.tv_pass);
        btnMasuk    = findViewById(R.id.btn_masuk);

        //variabel tadi untuk memanggil fungsi
        mDatabase   = FirebaseDatabase.getInstance().getReference();
        mAuth       = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        if (user != null){
            String userID = user.getUid();

            //when login success show Main Activity
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("userID", userID);
            startActivity(i);
            return;
        }

        //nambahin method onClick, biar tombolnya bisa diklik
        btnMasuk.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_masuk){
            signIn();
        } else{
            registerPage();
        }
    }

    private void registerPage() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void signIn() {
        //validate the form
        if (!validateForm()){
            return;
        }

        //get value from editText
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();

        //begin authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //get current user
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID = user.getUid();

                            //when login success show Main Activity
                            Intent i = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            i.putExtra("userID", userID);
                            startActivity(i);
                        } else {
                            Toast.makeText(LoginActivity.this, "Sign in Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("Required");
            result = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtPass.getText().toString())) {
            edtPass.setError("Required");
            result = false;
        } else {
            edtPass.setError(null);
        }

        return result;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(1);
    }
}