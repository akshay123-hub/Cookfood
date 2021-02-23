package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText name,email,password;
    Button signUp;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth=FirebaseAuth.getInstance();
        name = findViewById(R.id.nameEditText);
        email=findViewById(R.id.emailText);
        password=findViewById(R.id.password_text);
        signUp=findViewById(R.id.signUpBtn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });
        
    }

    private void userRegister() {
        String str_name=name.getText().toString();
        String str_email=email.getText().toString().trim();
        String str_password=password.getText().toString().trim();
        if (str_email.isEmpty()){
            email.setError("Required to fill !");
            email.requestFocus();
            return;
        }else if(str_name.isEmpty()){
            name.setError("Required to fill !");
            name.requestFocus();
            return;
        }else if (str_password.isEmpty()){
            password.setError("Required to fill !");
            password.requestFocus();
            return;
        }else if (str_password.length()<=8){
            password.setError("Min 8 Character is Required !");
            password.requestFocus();
            return;
        }
        else{
            auth.createUserWithEmailAndPassword(str_email,str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUp.this, "Sign up Successfully ! ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(SignUp.this, "Wrong !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }
}