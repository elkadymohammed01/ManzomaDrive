package com.great.mycar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.great.mycar.R;

import java.util.ArrayList;
import java.util.List;


public class LoadingAdapter extends RecyclerView.Adapter<LoadingAdapter.CardViewHolder> {
    Context context;
    int size,id;
    public LoadingAdapter(Context context,int size,int id) {
        this.context = context;
        this.size=size;
        this.id=id;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.loading, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        holder.mShimmerViewContainer.startShimmerAnimation();
    }


    @Override
    public int getItemCount() {
        return size;
    }


    public static final class CardViewHolder extends RecyclerView.ViewHolder{
        private ShimmerFrameLayout mShimmerViewContainer;
        View view ;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        }
    }

}