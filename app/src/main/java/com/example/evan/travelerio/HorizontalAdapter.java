package com.example.evan.travelerio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    private ArrayList<String> mHotelName;
    private ArrayList<String> mHotelImage;

    private Context mContext;


    public HorizontalAdapter(ArrayList<String> mHotelName, ArrayList<String> mHotelImage, Context mContext) {
        this.mHotelName = mHotelName;
        this.mHotelImage = mHotelImage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_recyclerview_horizontal_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        viewHolder.viewHolderHotelName.setText(mHotelName.get(position));

        Glide.with(mContext).load(mHotelImage.get(position)).apply(RequestOptions.centerCropTransform()).into(viewHolder.viewHolderHotelPreview);

        viewHolder.viewHolderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mHotelName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView viewHolderHotelPreview;
        TextView viewHolderHotelName;
        RelativeLayout viewHolderLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewHolderHotelName = itemView.findViewById(R.id.hotel_name);
            viewHolderHotelPreview = itemView.findViewById(R.id.hotel_preview);
            viewHolderLayout = itemView.findViewById(R.id.recycler_hotel_rel_layout);
        }
    }
}
