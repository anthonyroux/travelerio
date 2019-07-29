package com.example.evan.travelerio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;


public class PointsInterestAdapter extends RecyclerView.Adapter<PointsInterestAdapter.ViewHolder> {

    private ArrayList<String> mName;
    private ArrayList<String> mTags;

    private Context mContext;

    public PointsInterestAdapter(ArrayList<String> mName, ArrayList<String> mTags, Context mContext) {
        this.mName = mName;
        this.mTags = mTags;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_recyclerview_points_interest_item, viewGroup, false);
        PointsInterestAdapter.ViewHolder holder = new PointsInterestAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PointsInterestAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.viewHolderCityName.setText(mName.get(position));
        viewHolder.viewHolderTags.setText(mTags.get(position));

    }

    @Override
    public int getItemCount() {
        return mName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView viewHolderCityName, viewHolderTags;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewHolderCityName = itemView.findViewById(R.id.modelrecycler_city_name);
            viewHolderTags = itemView.findViewById(R.id.modelrecycler_tags);

        }
    }


}

