package com.great.mycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.great.mycar.adapter.ProductAdapter;
import com.great.mycar.adapter.ProductCategoryAdapter;
import com.great.mycar.adapter.QuestionAdapter;
import com.great.mycar.adapter.myDbAdapter;
import com.great.mycar.model.ProductCategory;
import com.great.mycar.model.Products;
import com.great.mycar.model.Question;

import java.util.ArrayList;
import java.util.List;

public class problem_ask extends AppCompatActivity {

    ProductCategoryAdapter productCategoryAdapter;
    RecyclerView productCatRecycler, prodItemRecycler;
    ImageView image;
    TextView name;
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_problem_ask);
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(new ProductCategory(1, "Trending"));
        productCategoryList.add(new ProductCategory(2, "Most Popular"));
        productCategoryList.add(new ProductCategory(3, "Offers"));
        productCategoryList.add(new ProductCategory(4, "Bat"));
        productCategoryList.add(new ProductCategory(5, "Bus"));

        setId();

        setProductRecycler(productCategoryList);

        setList();


        setUserInformation();

    }
    private void setUserInformation(){
        setDetails();

        name=findViewById(R.id.textView5);
        image=findViewById(R.id.imageView3);
        name.setText("Hello, "+User_name.split(" ")[0]+"!");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(mail);
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(image);
            }
        });
    }

    String mail, User_name, phone;

    private void setDetails() {
        myDbAdapter Db = new myDbAdapter(getApplicationContext());
        User_name = Db.getData_inf()[0];
        mail = Db.getData_inf()[1];
        phone = Db.getData_inf()[2];
    }
    List<Question>questionList=new ArrayList<>();
    private void setList(){
        FirebaseDatabase.getInstance().getReference().child("Question").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    questionList.add(data.getValue(Question.class));
                }
                setProdItemRecycler(questionList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setProductRecycler(List<ProductCategory> productCategoryList){

        productCatRecycler = findViewById(R.id.cat_recycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCatRecycler.setLayoutManager(layoutManager);
        productCategoryAdapter = new ProductCategoryAdapter(this, productCategoryList);
        productCatRecycler.setAdapter(productCategoryAdapter);

    }

    private void setProdItemRecycler(List<Question>questionList){

        prodItemRecycler = findViewById(R.id.product_recycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        QuestionAdapter adapter =new QuestionAdapter(this,questionList,lottieAnimationView);
        prodItemRecycler.setAdapter(adapter);

    }
    ImageView search;
    FloatingActionButton[]icons=new FloatingActionButton[4];

    private  void setId(){
        search=findViewById(R.id.imageView2);
        icons[0]=findViewById(R.id.shop);
        icons[1]=findViewById(R.id.place);
        icons[2]=findViewById(R.id.ser);
        icons[3]=findViewById(R.id.setting);
        lottieAnimationView=findViewById(R.id.show_love);
    }
    public void show_page_button(View view) {
        if(Show){
            ObjectAnimator.ofFloat(view, "rotation", 90f, 675f).start();

            search.animate().translationX(0).setDuration(500).setStartDelay(200).start();
            int val =0;
            for(FloatingActionButton icon:icons){
                icon.animate().translationX(0).setDuration(500).setStartDelay(val).start();
                val+=50;
            }
        }else{
            ObjectAnimator.ofFloat(view, "rotation", 90f, 1800f).start();

            search.animate().translationX(-130).setDuration(500).setStartDelay(0).start();
            int val =200;
            for(FloatingActionButton icon:icons){
                icon.animate().translationX(-130).setDuration(500).setStartDelay(val).start();
                val-=50;
            }

        }
        Show=!Show;
    }
    boolean Show =false;

    public void profile(View view) {
        startActivity(new Intent(this,User.class));
    }

    public void GoShop(View view) {
        startActivity(new Intent(this,MainPage.class));
        finish();
    }

    public void GoSetting(View view) {
        startActivity(new Intent(this,Settings.class));
    }
}
