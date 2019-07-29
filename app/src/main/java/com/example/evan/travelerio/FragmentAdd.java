package com.example.evan.travelerio;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;



public class FragmentAdd extends BottomSheetDialogFragment {


    private RelativeLayout mScanTicket, mCheckIn, mAddReview, mAddReservations;
    private Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet_add, container, false);


        mScanTicket = (RelativeLayout) view.findViewById(R.id.layout_scan_ticket);
        mScanTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTicketButton();
                dismiss();

            }
        });

        mCheckIn = (RelativeLayout) view.findViewById(R.id.layout_check_in);
        mCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                getFragmentManager().beginTransaction().add(R.id.fragment_container,
                        new FragmentCheckIn(), "FRAGMENT_CHECKIN").addToBackStack(null).commit();

            }
        });

        mAddReview = (RelativeLayout) view.findViewById(R.id.layout_add_review);
        mAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Toast.makeText(mContext, "Feature coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        mAddReservations = (RelativeLayout) view.findViewById(R.id.layout_reservations);
        mAddReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Toast.makeText(mContext, "Feature coming soon", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }


    private void initTicketButton(){

        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("traveler.io QR Code Scanner");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();

    }





}
