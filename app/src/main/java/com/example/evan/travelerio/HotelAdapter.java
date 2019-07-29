package com.example.evan.travelerio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Random;

import static android.support.constraint.Constraints.TAG;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {


    private ArrayList<String> mHotelImage, mHotelName, mDistance, mReviews, mPrice, mRatings, mDescription;
    private ArrayList<String> mAmenities;
    private ArrayList<String> mCurrencyType, mFollowingChannelName, mFollowingTempToken;
    private Context mContext;

    public HotelAdapter(ArrayList<String> mHotelImage, ArrayList<String> mHotelName, ArrayList<String> mDistance,
                        ArrayList<String> mReviews, ArrayList<String> mPrice, ArrayList<String> mRatings,
                        ArrayList<String> mDescription, ArrayList<String> mAmenities, ArrayList<String> mCurrencyType,
                        ArrayList<String> mFollowingChannelName, ArrayList<String> mFollowingTempToken, Context mContext) {
        this.mHotelImage = mHotelImage;
        this.mHotelName = mHotelName;
        this.mDistance = mDistance;
        this.mReviews = mReviews;
        this.mPrice = mPrice;
        this.mRatings = mRatings;
        this.mDescription = mDescription;
        this.mAmenities = mAmenities;
        this.mCurrencyType = mCurrencyType;
        this.mFollowingChannelName = mFollowingChannelName;
        this.mFollowingTempToken = mFollowingTempToken;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HotelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_recyclerview_hotel_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        if(mHotelImage.get(position) != null){
            Glide.with(mContext).load(mHotelImage.get(position)).apply(RequestOptions.centerCropTransform()).into(viewHolder.viewHolderImage);
        }

        viewHolder.viewHolderHotelName.setText(mHotelName.get(position));
        viewHolder.viewHolderDistance.setText(mDistance.get(position));
        viewHolder.viewHolderReviews.setText(mReviews.get(position));
        viewHolder.viewHolderPrice.setText(mPrice.get(position));
        viewHolder.viewHolderCurrencyType.setText(mCurrencyType.get(position));

        if(mRatings.get(position).equals("No Rating")){
            viewHolder.viewHolderRatingBar.setRating(0);
        }else{
            viewHolder.viewHolderRatingBar.setRating(Float.parseFloat(mRatings.get(position)));
        }


        Drawable webIcon = AppCompatResources.getDrawable(mContext, R.drawable.icon_visit_webpage_web_icon);
        Drawable arrowIcon = AppCompatResources.getDrawable(mContext, R.drawable.icon_visit_webpage_arrow_top_right);
        viewHolder.viewHolderVisitWebsite.setCompoundDrawablesWithIntrinsicBounds(webIcon, null, arrowIcon, null);

        viewHolder.viewHolderVisitWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Bookings not available in Self Service API", Toast.LENGTH_SHORT).show();
            }
        });


        viewHolder.viewHolderAmenitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAlertDialog(viewHolder, position);
            }
        });

        viewHolder.viewHolderMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAlertDialog(viewHolder, position);
            }
        });

        viewHolder.viewHolderStreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), CameraActivity.class);
                Bundle b = new Bundle();
                b.putString("channel_name", mFollowingChannelName.get(position));
                b.putString("temp_token", mFollowingTempToken.get(position));
                b.putString("spot_name", mHotelName.get(position));
                intent.putExtras(b);


                viewHolder.itemView.getContext().startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {

        return mHotelName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView viewHolderImage, viewHolderStreamButton;
        TextView viewHolderHotelName, viewHolderDistance, viewHolderReviews, viewHolderPrice, viewHolderAmenitiesButton;
        RatingBar viewHolderRatingBar;
        TextView viewHolderVisitWebsite, viewHolderCurrencyType;
        TextView viewHolderMoreInfo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewHolderImage = itemView.findViewById(R.id.modelhotel_image);
            viewHolderHotelName = itemView.findViewById(R.id.modelhotel_hotel_name);
            viewHolderDistance = itemView.findViewById(R.id.modelhotel_distance);
            viewHolderReviews = itemView.findViewById(R.id.modelhotel_reviews);
            viewHolderPrice = itemView.findViewById(R.id.modelhotel_price);
            viewHolderAmenitiesButton = itemView.findViewById(R.id.modelhotel_amenities_button);
            viewHolderRatingBar = itemView.findViewById(R.id.modelhotel_rating);
            viewHolderVisitWebsite = itemView.findViewById(R.id.modelhotel_visit_website);
            viewHolderCurrencyType = itemView.findViewById(R.id.modelhotel_currency_type);
            viewHolderMoreInfo = itemView.findViewById(R.id.modelhotel_more_info);
            viewHolderStreamButton = itemView.findViewById(R.id.modelhotel_stream_button);



        }
    }


    private void initAlertDialog(ViewHolder viewHolder, int position){
        final AppCompatActivity activity = (AppCompatActivity) viewHolder.itemView.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View mView = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_amenities, null);
        builder.setView(mView);

        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);

        final TextView textview_amenities = (TextView) mView.findViewById(R.id.alertdialog_amenities);
        final TextView textview_description = (TextView) mView.findViewById(R.id.alertdialog_description);

        Log.d(TAG, "onClick: AMENITIES: " + mAmenities.get(position));

        textview_amenities.setText(mAmenities.get(position));
        textview_description.setText(mDescription.get(position));


        alert.show();
    }


}
