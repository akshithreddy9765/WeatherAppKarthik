package com.example.weatherappkarthik.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherappkarthik.R;
import com.example.weatherappkarthik.data.Info;


/**
 * Created by Karthik on 4/5/2016.
 */
public class DisplayDetailsFragment extends Fragment {

    private TextView mResultTv;
    private Info mInfo;


    public void setmInfo(Info mInfo) {
        this.mInfo = mInfo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_details_layout, container, false);

        mResultTv = (TextView) view.findViewById(R.id.resultTv);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mInfo != null) {
            String result = getString(R.string.showing_result) + mInfo.getmCurrentObservation().getFullName() + "\n" +
                    getString(R.string.temp_c) + " " + mInfo.getmCurrentObservation().getTemp_c() + "\n" +
                    getString(R.string.temp_f) + " " + mInfo.getmCurrentObservation().getTemp_f();

            mResultTv.setText(result);
        }
    }
}
