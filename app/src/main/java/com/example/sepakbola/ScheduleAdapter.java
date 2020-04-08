package com.example.sepakbola;

import android.content.ContentValues;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    Context mContext;
    List<Schedule> mData;

    public ScheduleAdapter(Context mContext, List<Schedule> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.match_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.home.setText(mData.get(position).getHome());
        holder.away.setText(mData.get(position).getAway());
        holder.when.setText(mData.get(position).getDate());
        holder.homeScore.setText(mData.get(position).getHomeScore());
        holder.awayScore.setText(mData.get(position).getAwayScore());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView home;
        private TextView away;
        private TextView when;
        private TextView homeScore;
        private TextView awayScore;
        private TextView vs;

        public ViewHolder(View itemView) {
            super(itemView);
            home = (TextView) itemView.findViewById(R.id.textView6);
            away = (TextView) itemView.findViewById(R.id.textView7);
            when = (TextView) itemView.findViewById(R.id.textView);
            homeScore = (TextView) itemView.findViewById(R.id.textView2);
            awayScore = (TextView) itemView.findViewById(R.id.textView3);
            vs = (TextView) itemView.findViewById(R.id.textView4);
        }
    }
}
