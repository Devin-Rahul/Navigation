package com.cdap.safetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cdap.safetyapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private final DocumentReference documentReferenceUser=db.collection("users")
            .document(firebaseAuth.getCurrentUser().getUid());

    ImageButton ImageNavigation ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ImageNavigation = findViewById(R.id.imgnavi);

        ImageButton ImageSOS = findViewById(R.id.imgbtn);

        ImageSOS.setOnClickListener(v -> {
            documentReferenceUser.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.e("User", "DocumentSnapshot data: " + document.getData());


                                } else {
                                    Log.e("User", "No such document");
                                }
                            } else {
                                Log.e("User", "get failed with ", task.getException());
                                Toast.makeText(MainActivity.this,"No Internet Connection. Try Again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });



        Button btnLogout=findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);

            builder.setMessage("Do you want to Log Off?")
                    .setTitle("Log Off")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        firebaseAuth.signOut();
                        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                        startActivity(intent);
                        finish();
                    }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

            builder.show();
        });


        ImageNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,navigation_Interface.class);
                startActivity(intent);
            }
        });




    }
}