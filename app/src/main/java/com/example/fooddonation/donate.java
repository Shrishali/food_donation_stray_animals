package com.example.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class donate extends AppCompatActivity {
    Button submit;
    EditText name, phone, location, quantity, description;
    RadioButton raw, cooked;
    RadioGroup radiogrp;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    //String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        submit = findViewById(R.id.Submitbtn);
        name = findViewById(R.id.editTextTextPersonName);
        phone = findViewById(R.id.editTextPhone);
        location = findViewById(R.id.editTextTextPostalAddress);
        quantity = findViewById(R.id.editTextNumber);
        description = findViewById(R.id.editTextTextPersonName3);
        raw = findViewById(R.id.Rawbtn);
        cooked = findViewById(R.id.Cookedbtn);
        raw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    cooked.setChecked(false);
                }
            }
        });
        cooked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    raw.setChecked(false);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String donorName = name.getText().toString().trim();
                String donorLocation = location.getText().toString().trim();
                String qty = quantity.getText().toString().trim();
                String Phone = phone.getText().toString().trim();
                String des = description.getText().toString().trim();

                if (TextUtils.isEmpty(donorName)) {
                    name.setError("Name is Required.");
                    return;
                }

                if (TextUtils.isEmpty(donorLocation)) {
                    location.setError("Required.");
                    return;
                }

                if (Phone.length() < 10) {
                    phone.setError("Phone Number Must be >=10 Characters");
                    return;
                }
                if (TextUtils.isEmpty(qty)) {
                    quantity.setError("Required.");
                    return;
                }
                if (TextUtils.isEmpty(donorLocation)) {
                    location.setError("Required.");
                    return;
                }
                if (TextUtils.isEmpty(des)) {
                    description.setError("Required.");
                    return;
                }
                radiogrp = (RadioGroup) findViewById(R.id.radioGroup1);
                int selectedId = radiogrp.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);


                FirebaseUser user=fAuth.getCurrentUser();

                String userID =user.getUid();


                CollectionReference cf=fstore.collection("Users").document(userID).collection("Donatedata");
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("Name", name.getText().toString());
                userInfo.put("Location", location.getText().toString());
                userInfo.put("Quantity", quantity.getText().toString());
                userInfo.put("Phoneno", phone.getText().toString());
                userInfo.put("Description", description.getText().toString());
                userInfo.put("FoodType", radioButton.getText().toString());
                userInfo.put("Userid",userID );

                CollectionReference cf1=fstore.collection("Donation");
                Map<String, Object> userInfo1 = new HashMap<>();
                userInfo1.put("Name", name.getText().toString());
                userInfo1.put("Location", location.getText().toString());
                userInfo1.put("Quantity", quantity.getText().toString());
                userInfo1.put("Phoneno", phone.getText().toString());
                userInfo1.put("Description", description.getText().toString());
                userInfo1.put("FoodType", radioButton.getText().toString());
                userInfo1.put("Userid",userID );
                cf1.add(userInfo1)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "Success!");
                               
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                                Log.w("TAG", "Error!", e);
                            }
                        });









                        // find the radiobutton by returned id






                cf.add(userInfo)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "Success!");
                                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                Intent intent = new Intent(donate.this, Donorpage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                                Log.w("TAG", "Error!", e);
                            }
                        });

            }
        });


    }
}