package com.example.evan.travelerio;


import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.PointOfInterest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Arrays;

import static android.support.constraint.Constraints.TAG;
import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;


public class FragmentSearch extends Fragment {

    private Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ImageView imageViewDiscover = (ImageView) view.findViewById(R.id.imageview_discover);
        Glide.with(mContext).load(R.drawable.photo_discover).apply(RequestOptions.centerCropTransform()).into(imageViewDiscover);

        imageViewDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentDiscover();
                getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "FRAGMENT_DISCOVER").addToBackStack(null).commit();
            }
        });


        ImageView imageViewBookHotel = (ImageView) view.findViewById(R.id.imageview_book_hotel);
        Glide.with(mContext).load(R.drawable.photo_hotel).apply(RequestOptions.centerCropTransform()).into(imageViewBookHotel);
        imageViewBookHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentHotel();
                getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "FRAGMENT_HOTEL").addToBackStack(null).commit();
            }
        });




        ImageView imageViewRestaurants = (ImageView) view.findViewById(R.id.imageview_restaurants);
        Glide.with(mContext).load(R.drawable.photo_restaurants).apply(RequestOptions.centerCropTransform()).into(imageViewRestaurants);
        imageViewRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "A future feature", Toast.LENGTH_SHORT).show();
            }
        });





        ImageView imageViewFlights = (ImageView) view.findViewById(R.id.imageview_book_flights);
        Glide.with(mContext).load(R.drawable.photo_flights).apply(RequestOptions.centerCropTransform()).into(imageViewFlights);
        imageViewFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "A future feature", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


}
