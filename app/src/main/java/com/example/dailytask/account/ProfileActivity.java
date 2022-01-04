package com.example.dailytask.account;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dailytask.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView foto;
    LinearLayout email_layout, username_layout, tglLahir_layout, kelamin;
    TextView tv_kelamin, tv_email, tv_username, tv_tglLahir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //ambil komponen
        foto = findViewById(R.id.profil_foto);
        email_layout = findViewById(R.id.email_box);
        username_layout = findViewById(R.id.username_pro);
        tglLahir_layout = findViewById(R.id.ultah);
        kelamin = findViewById(R.id.kelamin);
        tv_email = findViewById(R.id.email);
        tv_kelamin = findViewById(R.id.jenis_kelamin);
        tv_username = findViewById(R.id.username);
        tv_tglLahir  = findViewById(R.id.tgl_lahir);

        //set on click listner
        foto.setOnClickListener(this);
        email_layout.setOnClickListener(this);
        username_layout.setOnClickListener(this);
        tglLahir_layout.setOnClickListener(this);
        kelamin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.profil_foto){
            // update foto
            updatePicture();
        } if (i == R.id.email_box){
            // intent update email
        } if (i == R.id.username_pro){
            // intent username
        } if (i == R.id.ultah){

        }
    }

    private void updatePicture() {
        selectImage(ProfileActivity.this);
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Choose a Media");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}