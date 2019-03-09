package com.example.aruna.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emails,passwords;
    Button registerButton,loginButton, reset_password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emails = (EditText) findViewById(R.id.uyeEmail);
        passwords = (EditText) findViewById(R.id.uyeParola);
        registerButton = (Button) findViewById(R.id.yeniUyeButton);
        loginButton = (Button) findViewById(R.id.uyeGirisButton);
        reset_password = (Button) findViewById(R.id.reset_password);

        firebaseAuth = FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emails.getText().toString();
                String password = passwords.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                }

                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String email = emails.getText().toString();
                    String password = passwords.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }
                    });
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "Please Add Email and Password", Toast.LENGTH_SHORT).show();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getApplicationContext(), "Please Add Email and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewPassActivity.class));
            }
        });

        if(firebaseAuth.getCurrentUser()!=null){
            try {
                Toast.makeText(getApplicationContext(), "You Have Logged From : " + firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {}
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}
