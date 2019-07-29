package com.example.evan.travelerio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class CitySpotsAdapter extends RecyclerView.Adapter<CitySpotsAdapter.ViewHolder> {

    private ArrayList<String> mImage, mName, mRating, mReviews, mFollowingChannelName, mFollowingTempToken;
    private Context mContext;


    public CitySpotsAdapter(ArrayList<String> mImage, ArrayList<String> mName, ArrayList<String> mRating,
                            ArrayList<String> mReviews, ArrayList<String> mFollowingChannelName,
                            ArrayList<String> mFollowingTempToken, Context mContext) {
        this.mImage = mImage;
        this.mName = mName;
        this.mRating = mRating;
        this.mReviews = mReviews;
        this.mFollowingChannelName = mFollowingChannelName;
        this.mFollowingTempToken = mFollowingTempToken;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_recyclerview_city_spots, viewGroup, false);
        CitySpotsAdapter.ViewHolder holder = new CitySpotsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CitySpotsAdapter.ViewHolder viewHolder, final int position) {


        Glide.with(mContext).load(mImage.get(position)).apply(RequestOptions.centerCropTransform()).into(viewHolder.viewHolderImage);

        viewHolder.viewHolderStreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), CameraActivity.class);
                Bundle b = new Bundle();
                b.putString("channel_name", mFollowingChannelName.get(position));
                b.putString("temp_token", mFollowingTempToken.get(position));
                b.putString("spot_name", mName.get(position));
                intent.putExtras(b);


                viewHolder.itemView.getContext().startActivity(intent);
            }
        });

        viewHolder.viewHolderName.setText(mName.get(position));
        viewHolder.viewHolderReviews.setText(mReviews.get(position));

        if(mRating.get(position).equals("No Rating")){
            viewHolder.viewHolderRating.setRating(0);
        }else{
            viewHolder.viewHolderRating.setRating(Float.parseFloat(mRating.get(position)));
        }




    }

    @Override
    public int getItemCount() {
        return mName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView viewHolderImage, viewHolderStreamButton;
        TextView viewHolderName, viewHolderReviews;
        RatingBar viewHolderRating;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            viewHolderImage = itemView.findViewById(R.id.cityspots_image);
            viewHolderStreamButton = itemView.findViewById(R.id.cityspots_stream_button);
            viewHolderName = itemView.findViewById(R.id.cityspots_name);
            viewHolderRating = itemView.findViewById(R.id.cityspots_rating);
            viewHolderReviews = itemView.findViewById(R.id.cityspots_reviews);


        }
    }


}

