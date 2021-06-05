package com.great.mycar;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.great.mycar.adapter.myDbAdapter;
import com.great.mycar.model.User;

import org.w3c.dom.Text;

public class SignUp extends AppCompatActivity {
    EditText name ,email,phone,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        getId();
    }
    private void getId(){
        name=findViewById(R.id.name);
        email=findViewById(R.id.mail);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
    }
    private void AddingUser(){
        User user =new User();
        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setPhone(phone.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("User")
                .child(user.getEmail().substring(0,user.getEmail().length()-4))
                .setValue(user);
        myDbAdapter DB = new myDbAdapter(this);
        DB.insertData(user.getName(),user.getPhone(),user.getEmail(),user.getPassword());
    }

    public void SignIn(View view) {
        startActivity(new Intent(this,SignIn.class));
        finish();
    }

    public void newUser(View view) {
        ObjectAnimator.ofFloat(view, "rotation", 90f, 900f).start();
        boolean check=checkLength(name,6,18,"Check User Name Size .");
        check&=checkLength(phone,11,11,"Check Phone Size .");
        check&=checkLength(email,10,100,"Check  Email Size .");
        check&=checkLength(password,6,20,"Check Password Size .");

        if(check)
            AddingUser();
        else{
            ObjectAnimator.ofFloat(view, "rotation", 90f, 360f).start();
        }
    }
    private boolean checkLength(EditText text,Integer length,Integer max_length,String error){
        String check=text.getText().toString();
        int size=check.length();
        for(char c:check.toCharArray())
            if(c==' ')
                size--;
            if(size>=length&&size<=max_length)
                return true;
            else {
                text.setHintTextColor(getResources().getColor(R.color.red));
                text.setHint(error);
                text.setText("");
                return false;
            }
    }
}
