package com.great.mycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.great.mycar.adapter.myDbAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        start();
    }

    private void start(){
        myDbAdapter DB = new myDbAdapter(getApplicationContext());
        if(DB.getData().length()>20)
            FirebaseDatabase.getInstance().getReference().child("User")
                    .child(DB.getData_inf()[1].substring(0,DB.getData_inf()[1].length()-4))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                                nextPage(true);
                            else
                                nextPage(false);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        else
            nextPage(false);
    }
    private void nextPage(boolean show){
        if(!show)
            startActivity(new Intent(this,SignIn.class));
        else
            startActivity(new Intent(this,problem_ask.class));
        finish();
    }
}
