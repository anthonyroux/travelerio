package com.example.evan.travelerio;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static android.support.constraint.Constraints.TAG;


public class FragmentHome extends Fragment {

    private Context mContext;
    private String mCityCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        TextView appName = view.findViewById(R.id.appname_home);
        Drawable appNameIcon = AppCompatResources.getDrawable(mContext, R.drawable.travelorio_icon_home);
        appName.setCompoundDrawablesWithIntrinsicBounds(null, null, appNameIcon, null);

        final EditText editText = view.findViewById(R.id.homepage_edittext);

        TextView letsGo = view.findViewById(R.id.homepage_letsgo);
        letsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCityCode = editText.getText().toString();
                if(mCityCode.length() < 3){
                    Toast.makeText(mContext, "City code must be 3 letters", Toast.LENGTH_SHORT).show();
                }else{
                    MethodMisc.closeKeyboard(getActivity());
                    Fragment fragmentHotel = new FragmentHotel();
                    Bundle b = new Bundle();
                    b.putString("city_code", mCityCode);
                    fragmentHotel.setArguments(b);
                    getFragmentManager().beginTransaction().add(R.id.fragment_container,
                            fragmentHotel, "FRAGMENT_HOTEL").addToBackStack(null).commit();
                }
            }
        });


        return view;
    }




}
