package com.example.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Volunteerpage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button Receivebtn,aboutus;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView nav_view;
    TextView nav_name,nav_contact,nav_type,nav_email,nav_location;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    private CollectionReference cf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteerpage);

        aboutus=findViewById(R.id.abt);
        Receivebtn=findViewById(R.id.rec);
        toolbar=findViewById(R.id.toolbar);

        Receivebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Receive.class));

            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AboutUs.class));

            }
        });






    setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feed Stray");
        drawerLayout=findViewById(R.id.drawerLayout);
        nav_view=findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(Volunteerpage.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_name=nav_view.getHeaderView(0).findViewById(R.id.Names);
        nav_email=nav_view.getHeaderView(0).findViewById(R.id.emaail);
        nav_contact=nav_view.getHeaderView(0).findViewById(R.id.contacct);
        nav_location=nav_view.getHeaderView(0).findViewById(R.id.address);
         nav_view.setNavigationItemSelectedListener(this);
        fstore=FirebaseFirestore.getInstance();

        fAuth= FirebaseAuth.getInstance();
       // FirebaseUser user=fAuth.getCurrentUser();




       // cf=fstore.collection("Users");
        loadNotes();
    }
    public void loadNotes() {
        fstore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null){
                        String name = task.getResult().getString("Name");
                        String email = task.getResult().getString("Email");
                        String phone = task.getResult().getString("Contactno");
                        String address = task.getResult().getString("Address");
                        nav_name.setText(name);
                        nav_email.setText(email);
                        nav_contact.setText(phone);
                        nav_location.setText(address);



                    }
       else {
                            Log.d("TAG", "Error fetching data: ", task.getException());
                        }

                });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                Intent intent3=new Intent(Volunteerpage.this,Volunteerpage.class);
                startActivity(intent3);
                break;
            case R.id.profile:
                Intent intent=new Intent(Volunteerpage.this,aboutuser.class);
                startActivity(intent);
                break;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent1=new Intent(Volunteerpage.this,MainActivity.class);

                startActivity(intent1);
                finish();
                break;
            case R.id.sentemail:
                Intent intent2=new Intent(Volunteerpage.this, EmailActivity.class);
                startActivity(intent2);
                break;


        }
       drawerLayout.closeDrawer(GravityCompat.START);
       return true;

    }
}