package com.great.mycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.great.mycar.model.CarDetails;
import com.great.mycar.model.Products;

public class Addprodactions extends AppCompatActivity {

    EditText Title,details,type,speed,qty,price,size,flex,timer,tools;
    TextView post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprodactions);
        setIds();
        onAddText();
    }

    private void setIds(){
        post=findViewById(R.id.post);
        Title=findViewById(R.id.name);
        details=findViewById(R.id.details);
        type=findViewById(R.id.type);
        speed=findViewById(R.id.speed);
        qty=findViewById(R.id.prod_qty);
        price=findViewById(R.id.price);
        size=findViewById(R.id.size);
        flex=findViewById(R.id.flex);
        timer=findViewById(R.id.timer);
        tools=findViewById(R.id.tools);
    }
    private void onAddText(){
        OcChangeText(1,Title);
        OcChangeText(2,details);
        OcChangeText(3,timer);
        OcChangeText(4,tools);
        OcChangeText(5,type);
        OcChangeText(6,speed);
        OcChangeText(7,size);
        OcChangeText(8,flex);
        OcChangeText(9,price);
        OcChangeText(10,qty);
    }
    
    boolean show[]=new boolean[20];
    int all=0;
    private void OcChangeText(final int x, EditText ed){
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().split(" ").length==0||s.toString().length()==0||s.toString().equals(" ")){
                    if(show[x])
                        all--;
                    show[x]=false;
                    post.setTextColor(getResources().getColor(R.color.gray)); }
                else{
                    if(!show[x])
                        all++;
                    show[x]=true;
                    if(all>=10)
                        post.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });
    }

    public void post(View view) {
        Integer id=(int)(System.currentTimeMillis()%((long)Integer.MAX_VALUE));
        id=Integer.MAX_VALUE-id;
        Products products=new Products(id,Title.getText().toString(),
                type.getText().toString(),price.getText().toString(),id,0);
        CarDetails carDetails=new CarDetails(id+"",Title.getText().toString(),speed.getText().toString()
                ,size.getText().toString(),flex.getText().toString(),type.getText().toString(),
                timer.getText().toString(),tools.getText().toString(),details.getText().toString());
        if(all>=10){
            FirebaseDatabase.getInstance().getReference().child("Car").child(id+"").setValue(products);
            FirebaseDatabase.getInstance().getReference().child("CarDetails").child(id+"").setValue(carDetails);
            Intent intent=new Intent(this,Colorprodaction.class);
            intent.putExtra("id",id+"");
            startActivity(intent);
        }
        else
            Toast.makeText(this,"You  Have Empty Faild",Toast.LENGTH_LONG).show();
    }
}
