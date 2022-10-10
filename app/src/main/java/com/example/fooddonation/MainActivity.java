package com.example.fooddonation;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button donor;
    Button volu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        donor=findViewById(R.id.button);
        volu=findViewById(R.id.button2);
        donor.setOnClickListener(this);
        volu.setOnClickListener(this);




    }


    @Override
    public void onClick(View view) {
        if(view.equals(donor))
        {
            Intent intent=new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }
        if(view.equals(volu))
        {
            Intent intent=new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }
    }
}
