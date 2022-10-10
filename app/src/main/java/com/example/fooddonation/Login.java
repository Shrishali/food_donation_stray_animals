package com.example.fooddonation;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button gotoregister;
    EditText email,password;
    Button loginbtn;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        gotoregister=findViewById(R.id.register);
        gotoregister.setOnClickListener(this);
        email = findViewById(R.id.EmailLogin);
        password = findViewById(R.id.PasswordLogin);
        loginbtn = findViewById(R.id.Login);
        gotoregister = findViewById(R.id.register);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(email);
                checkField(password);
                Log.d("TAG","onclick:"+email.getText().toString());
                if(valid){
                    fAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this,"Loggedin Successfully",Toast.LENGTH_SHORT).show();
                            checkUser(authResult.getUser().getUid());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this,"Invalid Email ID or Password",Toast.LENGTH_SHORT).show();


                        }
                    });

                }
            }
        });

    }

    private void checkUser(String uid) {
        DocumentReference df=fstore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess:"+documentSnapshot.getData());
                if(documentSnapshot.getString("isDonor")!=null)
                {
                    startActivity(new Intent(getApplicationContext(),Donorpage.class));
                    finish();
                }
                if(documentSnapshot.getString("isVolu")!=null)
                {
                    startActivity(new Intent(getApplicationContext(),Volunteerpage.class));
                    finish();
                }


            }
        });


    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid=false;
        }
        else
        {
            valid=true;
        }
        return valid;
    }
    @Override
    protected  void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            DocumentReference df=FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.getString("isDonor")!=null){
                        startActivity(new Intent(getApplicationContext(),Donorpage.class));
                        finish();
                    }
                    if(documentSnapshot.getString("isVolu")!=null){
                        startActivity(new Intent(getApplicationContext(),Volunteerpage.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }
            });
        }
    }





    @Override
    public void onClick(View view) {
        if (view.equals(gotoregister)) {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        }
    }

    }











