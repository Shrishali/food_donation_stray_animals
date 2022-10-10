package com.example.fooddonation;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText password;
    EditText contactno;
    EditText address;
    Button register;
    Button goToLogin;
    boolean valid=true;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    CheckBox isDonorbox,isVolunbox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        name=findViewById(R.id.Name);
        email=findViewById(R.id.Email);
        password=findViewById(R.id.Password);
        contactno=findViewById(R.id.Contactno);
        address=findViewById(R.id.Address);
        register=findViewById(R.id.register);
        goToLogin=findViewById(R.id.tologin);
        isDonorbox=findViewById(R.id.Donorcheckbox);
        isVolunbox=findViewById(R.id.Volunteercheckbox);
        isVolunbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isDonorbox.setChecked(false);
                }
            }
        });
        isDonorbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isVolunbox.setChecked(false);
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



        checkField(name);
        checkField(email);
        checkField(password);
        checkField(contactno);
        checkField(address);
        if(!(isDonorbox.isChecked() || isVolunbox.isChecked()))
        {
            Toast.makeText(Register.this,"Select The Account Type",Toast.LENGTH_SHORT).show();
            return;

        }


        if(valid)
        {
            fAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user=fAuth.getCurrentUser();
                    Toast.makeText(Register.this,"Account Created",Toast.LENGTH_SHORT).show();
                    DocumentReference df=fstore.collection("Users").document(user.getUid());
                    Map<String,Object> userInfo=new HashMap<>();
                    userInfo.put("Name",name.getText().toString());
                    userInfo.put("Email",email.getText().toString());
                    userInfo.put("Contactno",contactno.getText().toString());
                    userInfo.put("Address",address.getText().toString());
                    if(isDonorbox.isChecked())
                    {
                        userInfo.put("isDonor","1");
                    }
                    if(isVolunbox.isChecked())
                    {
                        userInfo.put("isVolu","1");
                    }
                    df.set(userInfo);
                    if(isDonorbox.isChecked()){
                        startActivity(new Intent(getApplicationContext(),Donorpage.class));
                        finish();
                    }
                    if(isVolunbox.isChecked()){
                        startActivity(new Intent(getApplicationContext(),Volunteerpage.class));
                        finish();
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register.this,"Failed to create account",Toast.LENGTH_SHORT).show();
                }
            });
        }
            }
        });
        goToLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();

            }
        });

    }
    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }


}