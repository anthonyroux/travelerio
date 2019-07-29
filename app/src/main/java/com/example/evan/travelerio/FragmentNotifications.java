package com.example.evan.travelerio;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;


public class FragmentNotifications extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private RecyclerView recyclerView;
    private NotificationsAdapter notificationsAdapter;

    private RequestQueue mQueue;
    private Context mContext;
    private long mCurrentTime;

    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mDescription = new ArrayList<>();
    private ArrayList<String> mTime = new ArrayList<>();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RelativeLayout mNotificationsProgressLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();

        notificationsAdapter = new NotificationsAdapter(mTitle, mDescription, mTime, mContext);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);


        recyclerView = view.findViewById(R.id.fragment_notifications_recyclerview);
        recyclerView.setAdapter(notificationsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mNotificationsProgressLayout = (RelativeLayout) view.findViewById(R.id.notifications_progress_layout);
        mNotificationsProgressLayout.setVisibility(View.VISIBLE);

        jsonParse();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        return view;
    }




    private void jsonParse(){

        mQueue = Volley.newRequestQueue(mContext);

        String url = "https://64zepauzch.execute-api.us-east-1.amazonaws.com/FF/getnotifications";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    mCurrentTime = response.getLong("Server Time");

                    JSONArray notifications = response.getJSONArray("Notifications");

                    for(int i = 0; i < notifications.length(); i++){
                        JSONObject jsonObject = notifications.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        long date_posted = jsonObject.getLong("date_posted");

                        mTitle.add(title);
                        mDescription.add(description);
                        mTime.add(MethodCalculateTime.calculateNotificationsTime(mCurrentTime, date_posted));
                    }

                    mNotificationsProgressLayout.setVisibility(View.GONE);

                    notificationsAdapter.notifyDataSetChanged();


                }catch(JSONException e){
                    Log.d(TAG, "onResponse: " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "FragmentNotifications: Function is cold timeout " + error);

            }
        });

        mQueue.add(request);

    }

    private void clearArrays(){
        mTitle.clear();
        mDescription.clear();
        mTime.clear();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                //query for new notifications
                jsonParse();
                clearArrays();



            }
        }, 2000);
    }




}
