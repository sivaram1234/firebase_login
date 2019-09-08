package com.iitbbs.firebase_login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    final TextView logtxt = (TextView)findViewById(R.id.textView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseAuth mAuth;
        final EditText email = (EditText)findViewById(R.id.editText);
        final EditText pwd = (EditText)findViewById(R.id.editText2);
        Button signup = (Button)findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(MainActivity.this,"please fill all and try" ,
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(eml, pwwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Intent i = new Intent(MainActivity.this, home.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


    public void logact(View view) {
        Intent i = new Intent(MainActivity.this, login.class);
        startActivity(i);
    }
}
