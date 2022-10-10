package com.example.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Donorpage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button donatebtn;
    Button history,status;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView nav_view;
    TextView nav_name,nav_contact,nav_type,nav_email,nav_location;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donorpage);
     //   Button logoutbtn=findViewById(R.id.Logout);
         history=findViewById(R.id.historyBtn);
         donatebtn=findViewById(R.id.Donate);
         status=findViewById(R.id.statusbtn);
        toolbar=findViewById(R.id.toolbar1);
         donatebtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(),donate.class));

             }
         });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),History.class));

            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Status.class));

            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feed Stray");
        drawerLayout=findViewById(R.id.drawerLayout1);
        nav_view=findViewById(R.id.nav_view1);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(Donorpage.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_name=nav_view.getHeaderView(0).findViewById(R.id.NamesDonor);
        nav_email=nav_view.getHeaderView(0).findViewById(R.id.emaailDonor);
        nav_contact=nav_view.getHeaderView(0).findViewById(R.id.contactDonor);
        nav_location=nav_view.getHeaderView(0).findViewById(R.id.addressDonor);
        nav_view.setNavigationItemSelectedListener(this);
        fstore= FirebaseFirestore.getInstance();

        fAuth= FirebaseAuth.getInstance();

        loadNotes();

    }

    private void loadNotes() {
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
            case R.id.homeDonor:
                Intent intent3=new Intent(Donorpage.this,Donorpage.class);
                startActivity(intent3);
                break;
            case R.id.profileDonor:
                Intent intent=new Intent(Donorpage.this,aboutuser.class);
                startActivity(intent);
                break;
            case R.id.LogoutDonor:
                FirebaseAuth.getInstance().signOut();
                Intent intent1=new Intent(Donorpage.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.sentemailDonor:
                Intent intent2=new Intent(Donorpage.this, EmailActivityDonor.class);
                startActivity(intent2);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}