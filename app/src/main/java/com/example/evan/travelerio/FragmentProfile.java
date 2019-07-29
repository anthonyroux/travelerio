package com.example.evan.travelerio;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;


public class FragmentProfile extends Fragment {


    private RequestQueue mQueue;
    private Context mContext;
    private String mProfileURL, mCoverURL;

    private TextView textViewUsername, textViewDescription, textViewAccountDate, textViewPoints;
    private ImageView profilePic, coverPic;

    private long mCurrentTime;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        textViewUsername = view.findViewById(R.id.profile_username);
        textViewDescription = view.findViewById(R.id.profile_description);
        textViewAccountDate = view.findViewById(R.id.profile_account_age);
        textViewPoints = view.findViewById(R.id.profile_points);
        profilePic = (ImageView) view.findViewById(R.id.profile_profile_picture);
        coverPic = (ImageView) view.findViewById(R.id.profile_cover_picture);

        mViewPager = (ViewPager) view.findViewById(R.id.profile_view_pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.profile_tab_layout);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);


        jsonParse();

        ImageView signOutButton = (ImageView) view.findViewById(R.id.profile_settings);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AWSMobileClient.getInstance().signOut();
                Intent intentSignOut = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentSignOut);
                getActivity().finish();
            }
        });

        return view;
    }

    private void jsonParse(){

        mQueue = Volley.newRequestQueue(mContext);

        String url = "https://64zepauzch.execute-api.us-east-1.amazonaws.com/FF/userprofile";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    mCurrentTime = response.getLong("Server Time");

                    JSONObject profileInfo = response.getJSONArray("User Profile").getJSONObject(0);
                    String user_id = profileInfo.getString("user_id");
                    String username = profileInfo.getString("username");
                    Long account_created = profileInfo.getLong("account_created");
                    mProfileURL = profileInfo.getString("profile_picture");
                    mCoverURL = profileInfo.getString("cover_picture");
                    String description = profileInfo.getString("description");
                    Long points = profileInfo.getLong("points");

                    MethodLoadIntoViews.loadProfile(textViewUsername, textViewDescription, textViewAccountDate, textViewPoints, profilePic, coverPic,
                            username, description, mCurrentTime, account_created, points, mProfileURL, mCoverURL, mContext);


                    mViewPager.setAdapter(mSectionsPagerAdapter);


                }catch(JSONException e){
                    Log.d(TAG, "onResponse: " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "FragmentProfile: Function is cold timeout " + error);

            }
        });

        mQueue.add(request);

    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public SectionsPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ViewpagerFragmentRecentActivity();
                case 1:
                    return new ViewpagerFragmentReviews();
                default:
                    return null;

            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Recent Activity";
                case 1:
                    return "Reviews";
            }
            return null;
        }
    }






}
