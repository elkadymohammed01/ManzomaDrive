package com.great.mycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Colorprodaction extends AppCompatActivity {
    String id;
    Integer x,val;
    ImageView image,colorcode;
    FloatingActionButton go;
    ProgressBar bar;
    ArrayList<Integer>color=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorprodaction);
        setId();
    }
    private void setId(){
        x=0;val=0;
        id=getIntent().getStringExtra("id");
        image=findViewById(R.id.image);
        colorcode=findViewById(R.id.color);
        go=findViewById(R.id.open);
        bar=findViewById(R.id.bar);
        color.add(R.drawable.bg);color.add(R.color.blue);
        color.add(R.color.white);color.add(R.color.black);
        color.add(R.color.orange);color.add(R.color.yellow);
        color.add(R.color.green);color.add(R.color.gray);
    }
    public void next(View view) {
        ++val;
        if(val>=color.size())
            finish();
        else
        colorcode.setBackgroundColor(getResources().getColor(color.get(val)));
    }

    public void Go(View view) {
        ObjectAnimator.ofFloat(view, "rotation", 90f, 675f).start();
        if(x==0){
            FirebaseDatabase.getInstance().getReference()
                    .child("Car").child(id+"").child("color").setValue(val);
        }
        get_data(new String[]{"image/*"});
    }
    void get_data(String[] lop){
        try {
            String[] mimeTypes =
                    lop;

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
            } else {
                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }
                intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
            }
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 100);

        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            if (resultCode == Activity.RESULT_OK) {

                //             textTargetUri.setText(targetUri.toString());
                Uri targetUri = data.getData();
                file = targetUri;
                String main;
                main=System.currentTimeMillis()+"";
                if(x==0)
                main=id;
                FirebaseDatabase.getInstance().getReference().child("CarColor").child(id)
                        .child(val+"").setValue(main);
                upload_file(main);

                Glide.with(this).load(file).into(image);
            }
        }
    }
    Uri file;

    private void upload_file(String f){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference riversRef = storageRef.child(f);
        bar.setVisibility(View.VISIBLE);
        UploadTask uploadTask = riversRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                bar.setVisibility(View.INVISIBLE);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                bar.setVisibility(View.INVISIBLE);
                val++;
                x++;
                if(val>=color.size())
                    finish();
                else
                    colorcode.setBackgroundColor(getResources().getColor(color.get(val)));
            }
        });

    }
}
