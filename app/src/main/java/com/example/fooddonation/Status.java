package com.example.fooddonation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Status extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<User> userArrayList;
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
        setContentView(R.layout.activity_status);
        db= FirebaseFirestore.getInstance();
        fAuth= FirebaseAuth.getInstance();
        FirebaseUser user=fAuth.getCurrentUser();
        //food=findViewById(R.id.FoodType);
        //qty=findViewById(R.id.qty);
        //loc=findViewById(R.id.loc);
        //des=findViewById(R.id.desc);

        userID=user.getUid();


        //ProgressDialog progressDialog=new ProgressDialog(this);
       // progressDialog.setCancelable(false);
        //progressDialog.setMessage("Fetching Data..");
        //progressDialog.show();



        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        userArrayList=new ArrayList<User>();
        myAdapter=new MyAdapter(Status.this,userArrayList);
        recyclerView.setAdapter(myAdapter);
        EventChangeListener();


    }




    private void EventChangeListener() {
        db.collection("Donation").whereEqualTo("Userid",userID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            /*if(progressDialog != null&&progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }*/
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc:value.getDocumentChanges()){
                            if(dc.getType()==DocumentChange.Type.ADDED){
                                String did=dc.getDocument().getId();
                                User u=dc.getDocument().toObject(User.class);
                                u.setDid(did);
                                userArrayList.add(u);

                                //userArrayList.add(dc.getDocument().toObject(User.class));

                            }
                            myAdapter.notifyDataSetChanged();
                            /*if(progressDialog != null&&progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }*/

                        }
                    }
                });
    }
}
