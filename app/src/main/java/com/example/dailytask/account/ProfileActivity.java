package com.example.dailytask.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailytask.R;
import com.example.dailytask.helper.DateScrollPicker;
import com.example.dailytask.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView foto;
    private LinearLayout email_layout, username_layout, tglLahir_layout, kelamin;
    private TextView tv_kelamin, tv_email, tv_username, tv_tglLahir;
    private String userID, jeniskelamin, email, username, tglLahir, fotoProfil;
    private DatabaseReference myRef;
    private ProgressBar progressBar;

    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //set Toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.orange));

        //set back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);

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
        progressBar = findViewById(R.id.progressBar);

        //get userID
        userID = getIntent().getStringExtra("userID");

        //get user profile
        myRef = FirebaseDatabase.getInstance().getReference("users").child(userID);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User data = snapshot.getValue(User.class);
                fotoProfil = data.getFoto();
                email = data.getEmail();
                username = data.getUsername();
                jeniskelamin = data.getJenisKelamin();
                tglLahir = data.getTgllahir();
                //set data to textview
                Picasso.get().load(fotoProfil).into(foto);
                tv_email.setText(email);
                tv_username.setText(username);
                if (jeniskelamin == ""){
                    tv_kelamin.setText("Tambahkan jenis kelamin");
                } else {
                    tv_kelamin.setText(jeniskelamin);
                }
                if (tglLahir == ""){
                    tv_tglLahir.setText("Tambahkan tanggal lahir");
                } else {
                    tv_tglLahir.setText(tglLahir);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        } if (i == R.id.kelamin){
            kelaminPicker();
        } if (i == R.id.ultah){
            ultahPicker();
        }
    }

    private void kelaminPicker() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_kelamin, null);
        Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner4);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jeniskelamin = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dialogBuilder.setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myRef.child("jenisKelamin").setValue(jeniskelamin);
//                        Toast.makeText(ProfileActivity.this, ""+jeniskelamin, Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void ultahPicker()
    {
        DateScrollPicker.initDatePicker(ProfileActivity.this, userID);
    }

    private void updatePicture() {
        selectImage(ProfileActivity.this);
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose a Media");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 10);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 20);//one can be replaced with any action code

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null){
                uploadToFirebase(imageUri);
            }else{
                Toast.makeText(ProfileActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
            }
        }

//        if (requestCode == 10 && resultCode == RESULT_OK) {
//            final Bundle extras = data.getExtras();
//            Thread thread = new Thread(() -> {
//                Bitmap bitmap = (Bitmap) extras.get("data");
//                foto.post(() -> {
//                    foto.setImageBitmap(bitmap);
//                });
//            });
//            thread.start();
//        }
    }

    private void uploadToFirebase(Uri uri){

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        myRef.child("foto").setValue(uri.toString());
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ProfileActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        foto.setImageURI(imageUri);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ProfileActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
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