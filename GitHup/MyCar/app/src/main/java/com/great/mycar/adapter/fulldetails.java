package com.great.mycar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.great.mycar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class fulldetails extends RecyclerView.Adapter<Main>{
    Context context;
    List<Integer> logo=new ArrayList<>();
    List<String>text=new ArrayList<>();
    Integer src;
    public fulldetails(Context context, List<Integer> logo, List<String> text,Integer src) {
        this.context = context;
        this.logo = logo;
        this.text = text;
        this.src=src;
    }

    @NonNull
    @Override
    public Main onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(src, parent, false);
        return new Main(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Main holder, int position) {
        holder.icon.setImageResource(logo.get(position));
        holder.text.setText(text.get(position));
    }

    @Override
    public int getItemCount() {
        return logo.size();
    }
}
class Main extends RecyclerView.ViewHolder{
    TextView text;
    ImageView icon;
    public Main(@NonNull View itemView) {
        super(itemView);
        text=itemView.findViewById(R.id.text);
        icon=itemView.findViewById(R.id.logo);
    }
}
