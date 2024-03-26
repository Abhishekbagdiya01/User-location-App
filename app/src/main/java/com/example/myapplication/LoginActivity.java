package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
EditText email;
EditText password;
ImageView btn;

TextView registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextTextpersonEmail);
        password = findViewById(R.id.editTextTextPassword);
        btn = findViewById(R.id.imageView5);
        registerBtn = findViewById(R.id.register);

        Intent signUpIntent = new Intent(this, SignUp.class);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUpIntent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    void login(){
        if (email.getText().length() != 0 && password.getText().length() != 0){
            Toast.makeText(this,"Login success",Toast.LENGTH_SHORT).show();
            Intent loginSuccess = new Intent(this,MainActivity.class);
            loginSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginSuccess);
        }else{
            Toast.makeText(this,"Email and password cannot be empty",Toast.LENGTH_SHORT).show();
        }
    }
}