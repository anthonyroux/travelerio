package com.example.evan.travelerio;

import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {

    private ArrayList<String> mImagePreview, mCityName;
    private ArrayList<Object> mLat, mLng;
    private Context mContext;


    public DiscoverAdapter(ArrayList<String> mImagePreview, ArrayList<String> mCityName,
                           ArrayList<Object> mLat, ArrayList<Object> mLng, Context mContext) {
        this.mImagePreview = mImagePreview;
        this.mCityName = mCityName;
        this.mLat = mLat;
        this.mLng = mLng;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_recyclerview_discover_item, viewGroup, false);
        DiscoverAdapter.ViewHolder holder = new DiscoverAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DiscoverAdapter.ViewHolder viewHolder, final int position) {


        Glide.with(mContext).load(mImagePreview.get(position)).apply(RequestOptions.centerCropTransform()).into(viewHolder.viewHolderImage);

        viewHolder.viewHolderCity.setText(mCityName.get(position));

        viewHolder.viewHolderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Lat: " + mLat.get(position));
                Log.d(TAG, "onClick: Lng: " + mLng.get(position));

                Fragment fragment = new FragmentPointsInterest();
                Bundle b = new Bundle();
                b.putFloat("Lat", Float.valueOf(String.valueOf(mLat.get(position))));
                b.putFloat("Lng", Float.valueOf(String.valueOf(mLng.get(position))));
                b.putString("CityName", mCityName.get(position));
                fragment.setArguments(b);
                AppCompatActivity activity = (AppCompatActivity) mContext;
                activity.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "FRAGMENT_POINTSINTEREST").addToBackStack(null).commit();
            }
        });




    }

    @Override
    public int getItemCount() {
        return mCityName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView viewHolderImage;
        TextView viewHolderCity;

        RelativeLayout viewHolderLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            viewHolderImage = itemView.findViewById(R.id.modelrecycler_image);
            viewHolderCity = itemView.findViewById(R.id.modelrecycler_city_name);

            viewHolderLayout = itemView.findViewById(R.id.discover_item_rel_layout);


        }
    }


}

