package com.great.mycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.great.mycar.adapter.myDbAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Settings extends AppCompatActivity {

    ImageView profile;
    TextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUserInformation();

}
    private void setUserInformation(){
        setDetails();

        user=findViewById(R.id.usernameTextView);
        profile=findViewById(R.id.profileCircleImageView);
        user.setText("Hello, "+name.split(" ")[0]+"!");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(mail);
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(profile);
            }
        });
    }
    String mail, name, phone;

    private void setDetails() {
        myDbAdapter Db = new myDbAdapter(getApplicationContext());
        name = Db.getData_inf()[0];
        mail = Db.getData_inf()[1];
        phone = Db.getData_inf()[2];
    }

    public void GoProfile(View view) {
        startActivity(new Intent(this,User.class));
    }

    public void PersonalSettings(View view) {

        setting();
    }
    private void setting(){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.user_list);
        dialog.show();
        final TextView name,moname,email,moemail,phone,cancel,log_out;
        final ImageView profile;
        name=dialog.findViewById(R.id.name);
        moname=dialog.findViewById(R.id.moname);
        email=dialog.findViewById(R.id.email);
        moemail=dialog.findViewById(R.id.moemail);
        phone=dialog.findViewById(R.id.phone);
        profile=dialog.findViewById(R.id.profile);
        cancel=dialog.findViewById(R.id.cancel);
        log_out=dialog.findViewById(R.id.log_out);
        name.setText(this.name);
        moname.setText(this.name);
        email.setText(this.mail);
        moemail.setText(this.mail);
        phone.setText(this.phone);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(mail);
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(profile);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edText("name",name.getText().toString(),0);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_data(new String[]{"image/*"});
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edText("phone",phone.getText().toString(),2);
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDbAdapter mydb = new myDbAdapter(getApplicationContext());
                mydb.delete(name.getText().toString());
                finishAffinity();
            }
        });

    }
    private void edText(final String Type, String val, final int call){
        final Dialog dod = new Dialog(this);
        dod.setContentView(R.layout.edtext);
        dod.show();
        TextView ok = dod.findViewById(R.id.ok),
                cancel = dod.findViewById(R.id.cancel),
                title=dod.findViewById(R.id.textView31);
        final EditText text = dod.findViewById(R.id.editText6);
        title.setText(Type);
        text.setHint("Add "+Type);
        text.setText(val);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().toString().length()>6||
                        text.getText().toString().split(" ").length>=1){
                    FirebaseDatabase.getInstance().getReference()
                            .child("User").child(mail.substring(0,mail.length()-4))
                            .child(Type).setValue(text.getText().toString());
                    myDbAdapter Db = new myDbAdapter(getApplicationContext());
                    if(call==0){
                        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
                        mydb.delete(name);
                        user.setText(text.getText().toString());
                        name=text.getText().toString();
                        Db.insertData(text.getText().toString(),phone,mail,"120012");}
                    else if(call==2) {
                        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
                        mydb.delete(name);
                        Db.insertData(name,text.getText().toString(),mail,"120012");}

                }
                else
                    Toast.makeText(Settings.this, "Find Problem", Toast.LENGTH_SHORT).show();

                dod.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dod.cancel();
            }
        });
    }
    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
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
                setItem();
                upload_file(mail);
            }
        }
    }

    Uri file;
    private void setItem() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(mail);
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getApplicationContext()).load(uri).into(profile);
            }
        });
        user.setText(name);

    }
    private void upload_file(String f){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference riversRef = storageRef.child(f);

        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
    }

    public void LogOut(View view) {

        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        mydb.delete(mydb.getData_inf()[0]);
        finishAffinity();
    }

    public void ChangePassword(View view) {
        edText("password","",3);
    }
}
