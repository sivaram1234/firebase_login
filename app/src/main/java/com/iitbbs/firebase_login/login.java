package com.iitbbs.firebase_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private  FirebaseAuth.AuthStateListener mAuthStateListner;
    private  FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        final EditText email = (EditText)findViewById(R.id.editText);
        final EditText pwd = (EditText)findViewById(R.id.editText2);
        Button signin = (Button)findViewById(R.id.button3);
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mAuth.getCurrentUser();
                if(mUser!=null){
                    Toast.makeText(login.this, "looged already",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(login.this, home.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(login.this, "looged in please",Toast.LENGTH_SHORT).show();
                }
            }
        };

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eml = email.getText().toString();
                String pwwd = pwd.getText().toString();
                if(eml.isEmpty()){
                    email.setError("please fill");
                    email.requestFocus();
                }
                else if(pwwd.isEmpty()){
                    pwd.setError("please fill");
                    pwd.requestFocus();
                }
                else if(  eml.isEmpty() && pwwd.isEmpty()){
                    Toast.makeText(login.this,"please fill all and try" ,
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(eml,pwwd).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Intent i = new Intent(login.this, home.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(login.this, "failed try again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void signact(View view) {
        Intent i = new Intent(login.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListner);
    }
}
