package com.example.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class aboutuser extends AppCompatActivity {
    TextView name1, add, phone1, email1;
    Toolbar toolbar;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutuser);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        fstore=FirebaseFirestore.getInstance();

        fAuth= FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Profile");
        name1 = findViewById(R.id.name);
        add = findViewById(R.id.loc);
        phone1 = findViewById(R.id.ph);
        email1 = findViewById(R.id.em);
        fstore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String name = task.getResult().getString("Name");
                        String email = task.getResult().getString("Email");
                        String phone = task.getResult().getString("Contactno");
                        String address = task.getResult().getString("Address");
                        name1.setText(name);
                        email1.setText(email);
                        phone1.setText(phone);
                        add.setText(address);

                    } else {
                        Log.d("TAG", "Error fetching data: ", task.getException());
                    }

                });


    }
}