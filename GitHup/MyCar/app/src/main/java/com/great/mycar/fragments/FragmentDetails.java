package com.great.mycar.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.great.mycar.R;
import com.great.mycar.adapter.fulldetails;
import com.great.mycar.model.CarDetails;

import java.util.ArrayList;
import java.util.List;

public class FragmentDetails extends Fragment {
    ImageView image,type;
    ScrollView bg;
    Integer color,res;
    String im;
    TextView name,details,callUs,size;
    RecyclerView list;
    String id;
    public FragmentDetails(Integer color , String im ,Integer res,String id) {
        this.im=im;
        this.color=color;
        this.res=res;
        this.id=id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_details, container, false);

        getId(v);
        return v;
    }
    private void getId(View view){
        image =view.findViewById(R.id.CarImage);
        bg = view.findViewById(R.id.page);
        type =view.findViewById(R.id.type);
        callUs =view.findViewById(R.id.callus);
        size =view.findViewById(R.id.size);
        name=view.findViewById(R.id.name);
        details=view.findViewById(R.id.details);
        setList(view);
        setDetails();
        if(res==R.layout.fullstackblack)
            name.setTextColor(getContext().getResources().getColor(R.color.black));
    }
    private void setDetails(){
        bg.setBackgroundResource(color);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(im+"");
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getActivity()).load(uri).into(image);
            }
        });
    }
    private void setItem(String text){
        List<String> val=new ArrayList<>();
        List<Integer> src=new ArrayList<>();
        val.add("BMW");val.add("FER");val.add("HYU");val.add("CHE");val.add("LAM");
        val.add("NIS");val.add("MAR");val.add("LAN");val.add("ALI");
        Integer sol=R.drawable.alien;
        src.add(R.drawable.bmw);src.add(R.drawable.ferrari);src.add(R.drawable.hyundai);
        src.add(R.drawable.chevrolet);src.add(R.drawable.lamborghini);src.add(R.drawable.nissan);
        src.add(R.drawable.marcedace);src.add(R.drawable.land_rover);src.add(R.drawable.alien);
        for(int i=0;i<val.size();i++){
            int x=0;
            for(int j=0;j<val.get(i).length()&&j<text.length();j++){
                if(text.charAt(j)==val.get(i).charAt(j))
                    x++;
            }
            if(x==3){
                type.setImageResource(src.get(i));
                return ;
            }
            else if(x==2){
                sol=src.get(i);
            }

        }
        type.setImageResource(sol);
    }
    private void setList(View v){
        list=v.findViewById(R.id.list);
        final List<Integer> scr=new ArrayList<>();
        final List<String>text=new ArrayList<>();
        scr.add(R.drawable.ic_flexible);scr.add(R.drawable.ic_type);scr.add(R.drawable.ic_speed);
        scr.add(R.drawable.ic_timer);scr.add(R.drawable.ic_tools);
        if(res==R.layout.fullstackblack){
            name.setTextColor(getContext().getResources().getColor(R.color.black));
            details.setTextColor(getContext().getResources().getColor(R.color.black));
            size.setTextColor(getContext().getResources().getColor(R.color.black));
            callUs.setBackgroundResource(R.drawable.custom_buttton2);
        }
        FirebaseDatabase.getInstance().getReference().child("CarDetails").child(id+"")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CarDetails carDetails=snapshot.getValue(CarDetails.class);
                text.add(carDetails.getFlax());text.add(carDetails.getType());
                text.add(carDetails.getSpeed());text.add(carDetails.getTimer());
                text.add(carDetails.getTools());
                name.setText(carDetails.getName());
                size.setText(carDetails.getSize());
                setItem(carDetails.getType().toUpperCase());
                details.setText(carDetails.getDetails());
                list.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                fulldetails fullD=new fulldetails(getActivity(),scr,text,FragmentDetails.this.res);
                list.setAdapter(fullD);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
