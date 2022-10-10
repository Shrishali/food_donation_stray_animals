package com.example.fooddonation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class EmailActivityDonor extends AppCompatActivity {
    RecyclerView recyclerView;
    emailAdapter emailAda;
    ArrayList<EmailUser> userArrayList;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    String userID;
    FirebaseAuth fAuth;
    Button deleteBtn;
    FirebaseUser user;
    TextView food,des,qty,loc;
    CollectionReference cf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_donor);
        db= FirebaseFirestore.getInstance();
        fAuth= FirebaseAuth.getInstance();
        FirebaseUser user=fAuth.getCurrentUser();


        userID=user.getUid();




        recyclerView = findViewById(R.id.recy1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        userArrayList=new ArrayList<EmailUser>();
        emailAda=new emailAdapter(EmailActivityDonor.this,userArrayList);
        recyclerView.setAdapter(emailAda);
        EventChangeListener();


    }




    private void EventChangeListener() {
        db.collection("Users").whereEqualTo("isVolu","1")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){

                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc:value.getDocumentChanges()){
                            if(dc.getType()==DocumentChange.Type.ADDED){
                                String did=dc.getDocument().getId();
                                EmailUser u=dc.getDocument().toObject(EmailUser.class);
                                // u.setDid(did);
                                userArrayList.add(u);

                                //userArrayList.add(dc.getDocument().toObject(User.class));

                            }
                            emailAda.notifyDataSetChanged();


                        }
                    }
                });
    }
}
