package com.example.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnCompleteListener;



import com.google.android.gms.tasks.Task;


public class History extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    private TextView textViewData;
    private CollectionReference cf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        fstore= FirebaseFirestore.getInstance();
        fAuth= FirebaseAuth.getInstance();
        FirebaseUser user=fAuth.getCurrentUser();

        String userID =user.getUid();
        textViewData=findViewById(R.id.data1);

       cf=fstore.collection("Users").document(userID).collection("Donatedata");
        loadNotes();
    }
    public void loadNotes() {
        cf.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String data="";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                if (document.contains("Name")&& document.contains("FoodType") && document.contains("Location") && document.contains("Quantity") && document.contains("Phoneno")&&document.contains("Description")) {

                                    String name = (String) document.get("Name");
                                    String type = (String) document.get("FoodType");

                                   String location = (String) document.get("Location");
                                    String qty = (String) document.get("Quantity");
                                    String phone = (String) document.get("Phoneno");
                                    String description = (String) document.get("Description");
                                   // String userID = fAuth.getCurrentUser().getUid();
                                   // String Userid= (String) document.get("userid");
                                    //Timestamp ts = (Timestamp) document.get("timestamp");
                                    //String dateandtime=String.valueOf(ts);
                                   // String dateandtime=String.valueOf(ts.toDate());
                                    //String dateandtime = ts.toString();

                                            data += "Donor Name: " + name + "\nFood Type: " + type +"\nQuantity : " + qty + "\nLocation: " + location +"\nPhone No " + phone +"\nDescription: " + description +  "\n\n";
                                   // data+="Donor Name: " +description+"\n\n";
                                    textViewData.setText(data);
                                }
                            }
                            //textViewData.setText(data);
                        } else {
                            Log.d("TAG", "Error fetching data: ", task.getException());
                        }
                    }
                });
    }
}