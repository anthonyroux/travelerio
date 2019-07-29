package com.example.evan.travelerio;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MethodLoadIntoViews {

    public static void loadProfile(TextView textViewUsername, TextView textViewDescription, TextView textViewAccountDate, TextView textViewPoints,
                                   ImageView profilePic, ImageView coverPic,
                                   String username, String description, long mCurrentTime, long account_created, long points, String mProfileURL, String mCoverURL,
                                   Context mContext){
        textViewUsername.setText(username);
        textViewDescription.setText(description);
        textViewAccountDate.setText(MethodCalculateTime.calculateAge(mCurrentTime, account_created));


        if(points == 1){
            String pts = String.valueOf(points) + " " + "point";
            textViewPoints.setText(pts);
        }else{
            String pts = String.valueOf(points) + " " + "points";
            textViewPoints.setText(pts);
        }


        if(mProfileURL.equals("null")){
            Glide.with(mContext).load(R.drawable.photo_oil_painting).apply(RequestOptions.centerCropTransform()).into(profilePic);
        }else{
            Glide.with(mContext).load(mProfileURL).apply(RequestOptions.centerCropTransform()).into(profilePic);
        }

        if(!mCoverURL.equals("null")){
            Glide.with(mContext).load(mCoverURL).apply(RequestOptions.centerCropTransform()).into(coverPic);
        }
    }

}
