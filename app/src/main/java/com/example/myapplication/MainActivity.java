package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toRegister, forgotPass;
    private EditText appUsername, appPassword;
    private MaterialButton signIn;
    private ImageView googleButton;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toRegister = (TextView) findViewById(R.id.toRegister);
        toRegister.setOnClickListener(this);
        forgotPass = (TextView) findViewById(R.id.forgotPassword);
        forgotPass.setOnClickListener(this);

        signIn = (MaterialButton) findViewById(R.id.loginbutton);
        signIn.setOnClickListener(this);

        appUsername = (EditText) findViewById(R.id.username);
        appPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        googleButton = (ImageView) findViewById(R.id.googleSignIn);
        googleButton.setClickable(true);
        googleButton.setOnClickListener(this);


        //verif daca avem utlizitator logat si daca da modificam UI


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this,ForgotPassword.class));
                overridePendingTransition(0,0);
                //redirect la reset pass
                break;
            case R.id.loginbutton:
                loginMethod();

                break;
            case R.id.googleSignIn:
                googleSignInMethod();
                break;
        }
    }

    private void googleSignInMethod(){
    /*
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();
                */

    }

    private void loginMethod() {
        String username = appUsername.getText().toString().trim();
        String password = appPassword.getText().toString().trim();

        if(username.isEmpty()){
            appUsername.setError("Username can't be empty");
            appUsername.requestFocus();
            return;
        }

        if(password.isEmpty()){
            appPassword.setError("Password can't be empty");
            appPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

       // mAuth = FirebaseAuth.getInstance("https://fooddeliveryandroidapp-default-rtdb.europe-west1.firebasedatabase.app/");
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //redirect la user profile
                    Toast.makeText(MainActivity.this, "Login Succesful", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this,UserProfileFinal.class));
                }else{
                    Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }
}