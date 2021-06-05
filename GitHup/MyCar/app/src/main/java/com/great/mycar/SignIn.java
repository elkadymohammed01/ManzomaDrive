package com.great.mycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.great.mycar.adapter.myDbAdapter;

public class SignIn extends AppCompatActivity {

    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        getId();
    }

    private void getId() {
        email=findViewById(R.id.mail);
        password=findViewById(R.id.password);
    }
    public void SignUp(View view) {
        startActivity(new Intent(this,SignUp.class));
        finish();
    }
    private void checkUser(String email, final String password){
        FirebaseDatabase.getInstance().getReference()
                .child("User").child(email.substring(0,email.length()-4))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()&&snapshot.child("password").getValue().toString().equals(password)){

                    myDbAdapter DB = new myDbAdapter(getApplicationContext());

                    DB.insertData(snapshot.child("name").getValue().toString(),
                            snapshot.child("phone").getValue().toString(),
                            snapshot.child("email").getValue().toString(),
                            snapshot.child("password").getValue().toString());
                    startActivity(new Intent(SignIn.this,problem_ask.class));
                    finish();
                }else
                    Toast.makeText(SignIn.this,"Check Email & Password Please .", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void SignIn(View view) {
        ObjectAnimator.ofFloat(view,"rotation",90f,360f).start();
        checkUser(email.getText().toString(),password.getText().toString());
    }
}
