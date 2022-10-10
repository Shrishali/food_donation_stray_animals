package com.example.fooddonation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class emailAdapter extends RecyclerView.Adapter<emailAdapter.MyViewHolder> {
    Context context;
    ArrayList<EmailUser> userArrayList;
    FirebaseFirestore fstore;
    FirebaseAuth auth;


    public emailAdapter(Context context, ArrayList<EmailUser> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
        fstore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public emailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.email,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull emailAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        EmailUser user=userArrayList.get(position);
        holder.name.setText(user.Name);
        holder.add.setText(user.Address);
        holder.email.setText(user.Email);
        String nameofrec=user.getEmail();

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent=new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"+nameofrec));
                context.startActivity(emailIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, add, phone,email;
        Button btn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameuser);
            add = itemView.findViewById(R.id.nameAddress);
            email = itemView.findViewById(R.id.emailid);
            btn = itemView.findViewById(R.id.emailDonor);



        }
    }
    }
