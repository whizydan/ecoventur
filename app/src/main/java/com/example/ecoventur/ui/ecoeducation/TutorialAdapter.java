package com.example.ecoventur.ui.ecoeducation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoventur.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolder> {
    private ArrayList<Tutorial> catalogArrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TutorialAdapter(ArrayList<Tutorial> catalogArrayList) {
        this.catalogArrayList = catalogArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutorial, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tutorial tutorial = catalogArrayList.get(position);
        holder.vouchertitle.setText(tutorial.getTitle());
//        holder.articleImage.setImageResource(article.getImageUrl());
//        holder.coinbag.setImageResource(catalog.getCoinbag());
        Picasso.get().load(tutorial.getImageUrl()).into(holder.tutoImage);

        holder.itemView.setOnClickListener(v -> {
            int clickedPosition = holder.getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION && mListener != null) {
                mListener.onItemClick(clickedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catalogArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tutoImage;
        TextView vouchertitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tutoImage = itemView.findViewById(R.id.articleImage);

        }
    }
}

