package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerButton, alreadyGotAnAccount;
    private EditText AppUsername, AppEmail, AppPassword, AppPhoneNumber;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerButton = (MaterialButton) findViewById(R.id.registerButton);
        alreadyGotAnAccount = (TextView) findViewById(R.id.alreadyGotAccount);
        alreadyGotAnAccount.setOnClickListener(this);

        registerButton.setOnClickListener(this);

        AppUsername = (EditText) findViewById(R.id.username);
        AppEmail = (EditText) findViewById(R.id.email);
        AppPassword = (EditText) findViewById(R.id.password);
        AppPhoneNumber = (EditText) findViewById(R.id.phoneNumber);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);



    }

    @Override
    public void onClick(View view) {
        //consola pentru onlickuri sa stim ce buton se apeleaza
        switch (view.getId()){
            case R.id.alreadyGotAccount:
                startActivity(new Intent(this,MainActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.registerButton:
                registerUserMethod();
                break;
        }
    }

    private void registerUserMethod() {
        //validations
        String username = AppUsername.getText().toString().trim();
        String password = AppPassword.getText().toString().trim();
        String email = AppEmail.getText().toString().trim();
        String phoneNumber = AppPhoneNumber.getText().toString().trim();

        if(username.isEmpty()){
            AppUsername.setError("Username is required");
            AppUsername.requestFocus();
            return;
        }

        if(email.isEmpty()){
            AppEmail.setError("eMail is required");
            AppEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            AppEmail.setError("eMail adress is not valid");
            AppEmail.requestFocus();
            return;
        }


        if(phoneNumber.isEmpty()){
            AppPhoneNumber.setError("Phone Number is required");
            AppPhoneNumber.requestFocus();
            return;
        }

        if(!PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
            AppPhoneNumber.setError("Phone number is not valid");
            AppPhoneNumber.requestFocus();
            return;
        }

        //mai pot adauga validations la password
        if (password.isEmpty()){
            AppPassword.setError("Password is required");
            AppPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            AppPassword.setError("Password must have at least 6 characters");
            AppPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(username, email, phoneNumber);

                    //.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    //.child(email) aici face ceva

                    //Pot sa modific aici ca sa ii tin dupa email
                    FirebaseDatabase.getInstance("https://fooddeliveryandroidapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"User has been registered",Toast.LENGTH_LONG).show();
                                AppUsername.getText().clear();
                                AppEmail.getText().clear();
                                AppPassword.getText().clear();
                                AppPhoneNumber.getText().clear();
                                progressBar.setVisibility(View.GONE);
                                //redirect to app
                            }
                            else {
                                Toast.makeText(RegisterActivity.this,"An unexpected Error has occurred",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this,"An unexpected Error has occurred2",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });






    }
}