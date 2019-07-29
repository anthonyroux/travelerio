package com.example.evan.travelerio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private ArrayList<String> mTitle, mDescription, mTime;

    private Context mContext;


    public NotificationsAdapter(ArrayList<String> mTitle, ArrayList<String> mDescription, ArrayList<String> mTime, Context mContext) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mTime = mTime;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_recyclerview_notifications, viewGroup, false);
        NotificationsAdapter.ViewHolder holder = new NotificationsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationsAdapter.ViewHolder viewHolder, final int position) {


        viewHolder.viewHolderTitle.setText(mTitle.get(position));
        viewHolder.viewHolderDescription.setText(mDescription.get(position));
        viewHolder.viewHolderTime.setText(mTime.get(position));

        if((position%2) == 0){
            viewHolder.viewHolderTitle.setTextColor(Color.parseColor("#FFC518"));
        }else{
            viewHolder.viewHolderTitle.setTextColor(Color.parseColor("#42b1f3"));
        }

        viewHolder.viewHolderMoreButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, viewHolder.viewHolderMoreButton);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.popup_menu_stop_watching:
                                Toast.makeText(mContext, "Coming soon", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.popup_menu_remove:
                                Toast.makeText(mContext, "Coming soon", Toast.LENGTH_SHORT).show();
                                break;

                        }
                        return false;
                    }
                });

                @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(mContext, (MenuBuilder) popupMenu.getMenu(), viewHolder.viewHolderMoreButton);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();


            }
        });




    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView viewHolderTitle, viewHolderDescription, viewHolderTime;

        ImageButton viewHolderMoreButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            viewHolderTitle = itemView.findViewById(R.id.modelrecycler_notifications_title);
            viewHolderDescription = itemView.findViewById(R.id.modelrecycler_notifications_description);
            viewHolderTime = itemView.findViewById(R.id.modelrecycler_notifications_date);

            viewHolderMoreButton = itemView.findViewById(R.id.modelrecycler_notifications_more);


        }
    }


}

