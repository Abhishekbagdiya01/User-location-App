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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {
TextView btn;
EditText name;
EditText email;
EditText password;
EditText rePassword;
ImageView signUpBtn;
TextView loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        name = findViewById(R.id.editTextTextpersonName);
        email = findViewById(R.id.editTextTextpersonemail);
        password =  findViewById(R.id.editTextTextPassword);
        rePassword = findViewById(R.id.editTextTextRePassword);
        signUpBtn = findViewById(R.id.imageView5);
        loginBtn=findViewById(R.id.login);

        Intent loginIntent = new Intent(this,LoginActivity.class);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginIntent);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }
    void signUp(){
        if (name.getText().length() != 0  && email.getText().length() != 0 && password.getText().length() != 0 && rePassword.getText().length() != 0 ){
            if(password.getText().toString().equals(rePassword.getText().toString())){
                Toast.makeText(this,"Account created successfully",Toast.LENGTH_SHORT).show();
                Intent signUpSuccess = new Intent(this, LoginActivity.class);
                startActivity(signUpSuccess);
            }else{
                Toast.makeText(this,"Password and confirm password are not same" ,Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(this,"Account created successfully",Toast.LENGTH_SHORT).show();
//            Intent signUpSuccess = new Intent(this, LoginActivity.class);
//            startActivity(signUpSuccess);
        }else{
            Toast.makeText(this,"Field cannot be empty",Toast.LENGTH_SHORT).show();
        }
    }
}