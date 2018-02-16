package com.example.allen.workoutcalendar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPass;
    private EditText etPass2;
    private Button btnRegister;

    final private String TAG = "Register";
    final private int passLenBound = 6;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register Account");

        //Initialize Views
        etEmail = findViewById(R.id.et_register_email);
        etPass = findViewById(R.id.et_register_pass);
        etPass2 = findViewById(R.id.et_register_pass2);
        btnRegister = findViewById(R.id.btn_register_register);

        //Initialize Firebase instance
        mAuth = FirebaseAuth.getInstance();

        //Register button listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();
                String pass2 = etPass2.getText().toString();

                //First check for empty or malformed data
                if(email.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please enter your email",
                            Toast.LENGTH_SHORT).show();
                }else if(pass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please enter a password",
                            Toast.LENGTH_SHORT).show();
                }else if (pass.length() < passLenBound) {
                    Toast.makeText(RegisterActivity.this, "Please enter a password of at least 6 characters",
                            Toast.LENGTH_SHORT).show();
                }else if (pass2.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please re-enter your password",
                            Toast.LENGTH_SHORT).show();
                }else if(!pass.equals(pass2)){
                    Toast.makeText(RegisterActivity.this, "Both passwords must be the same",
                            Toast.LENGTH_SHORT).show();
                }else{
                    registerWithEmailAndPassword(email, pass);
                }
            }
        });
    }

    //Registers a user to the Firebase Authentication Database
    protected void registerWithEmailAndPassword(final String email, String pass){

        Log.d(TAG, "New Account: " + email);

        //Call the Firebase createUserWithEmailAndPassword( ) method
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If the user is created, make a Toast
                        if(task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendEmailVerification(user);
                        }else{
                            //In case an error occurred
                            try{
                                throw task.getException();
                            }catch(FirebaseAuthWeakPasswordException e){
                                Toast.makeText(RegisterActivity.this,
                                        "Weak Password. Must be at least 6 characters", Toast.LENGTH_SHORT).show();
                            }catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(RegisterActivity.this,
                                        "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(RegisterActivity.this,
                                        "User already exists", Toast.LENGTH_SHORT).show();
                            }catch(NullPointerException e){
                                Log.d(TAG, "Null Pointer Exception");
                            } catch(Exception e){
                                Toast.makeText(RegisterActivity.this,
                                        "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    //Sends verification email to user
    protected void sendEmailVerification(FirebaseUser user){
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Verification Email Sent.");
                            Toast.makeText(RegisterActivity.this,
                                    "A verification email has been sent", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Log.d(TAG, "Verification email couldn't send");
                        }
                    }
                });
    }

}
