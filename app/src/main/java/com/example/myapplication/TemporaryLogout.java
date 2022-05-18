package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TemporaryLogout extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID; //unique id care corespunde fiecarui user

    private MaterialButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary_logout);

        logout = (MaterialButton) findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                //redirect catre main page dupa logout
                startActivity(new Intent(TemporaryLogout.this,MainActivity.class));
            }
        });
        /*
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://fooddeliveryandroidapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();

        final TextView emailTextView = (TextView) findViewById(R.id.email);
        final TextView phoneTextView = (TextView) findViewById(R.id.phoneNumber);
        final TextView usernameTextView = (TextView) findViewById(R.id.username);
        final TextView helloTextView = (TextView) findViewById(R.id.hello);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String email = userProfile.email;
                    String username = userProfile.username;
                    String phoneNumber = userProfile.phoneNumber;

                    emailTextView.setText(email);
                    phoneTextView.setText(phoneNumber);
                    usernameTextView.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TemporaryLogout.this,"Something went wrong", Toast.LENGTH_LONG).show();

            }
        });*/
    }


}