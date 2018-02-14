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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class ForgotActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSend;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        //Initialize views
        etEmail = findViewById(R.id.et_forgot_email);
        btnSend = findViewById(R.id.btn_forgot_send);

        //Initialize Firebase instance
        mAuth = FirebaseAuth.getInstance();

        //Listener for Send Button
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                if(!email.isEmpty()){
                    sendPassResetEmail(email);
                }else{
                    Toast.makeText(ForgotActivity.this, "Please enter your email",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Firebase Auth sends a reset password email to the specified email
    protected void sendPassResetEmail(String email){
        mAuth.sendPasswordResetEmail(email).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("Forgot", "Email sent.");
                            Toast.makeText(ForgotActivity.this,
                                    "Email sent", Toast.LENGTH_SHORT).show();
                        }else{
                            //In case an error occurs
                            try{
                                throw task.getException();
                            }catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(ForgotActivity.this,
                                        "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(ForgotActivity.this,
                                        "User doesn't exist", Toast.LENGTH_SHORT).show();
                            }catch(Exception e){
                                Toast.makeText(ForgotActivity.this,
                                        "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }
}
