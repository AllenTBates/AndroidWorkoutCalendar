package com.example.allen.workoutcalendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    //Declare views
    private EditText etEmail;
    private EditText etPass;
    private TextView tvForgot;
    private Button btnLogin;
    private Button btnRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize views
        etEmail = findViewById(R.id.et_login_email);
        etPass = findViewById(R.id.et_login_pass);
        tvForgot = findViewById(R.id.tv_login_forgot);
        btnLogin = findViewById(R.id.btn_login_login);
        btnRegister = findViewById(R.id.btn_login_register);

        //Initialize Firebase instance
        mAuth = FirebaseAuth.getInstance();

        //Listener for Login Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();

                //If there is an email and password present, attempt logging in
                if(!email.isEmpty() && !pass.isEmpty()){
                    loginWithEmailAndPassword(email, pass);
                }else if(email.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter your email",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Please enter your password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Listener for Forgot Password link
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,
                        ForgotActivity.class));
            }
        });

        //Listener for Register Button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,
                        RegisterActivity.class));
            }
        });
    }

    //Uses Firebase Authentication to log user in with email and password
    protected void loginWithEmailAndPassword(String email, String pass){
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified()){
                                Toast.makeText(LoginActivity.this, "Successful login for " + user.getUid(),
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,
                                        MonthActivity.class));
                            }else{
                                Toast.makeText(LoginActivity.this, "Please verify your account with the email sent to " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            //In case an error occurred
                            try{
                                throw task.getException();
                            }catch(FirebaseAuthWeakPasswordException e){
                                Toast.makeText(LoginActivity.this,
                                        "Weak Password. Must be at least 6 characters", Toast.LENGTH_SHORT).show();
                            }catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(LoginActivity.this,
                                        "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(LoginActivity.this,
                                        "User already exists", Toast.LENGTH_SHORT).show();
                            }catch(Exception e){
                                Toast.makeText(LoginActivity.this,
                                        "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
