package com.cdap.safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class navigation_Interface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__interface);

        ImageButton hospitalIbtn;

        hospitalIbtn = findViewById(R.id.imgBtnhos);


        hospitalIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(navigation_Interface.this,Hospital.class);
                startActivity(intent);
            }
        });
    }
}