package com.example.weatherappkarthik.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.weatherappkarthik.R;
import com.example.weatherappkarthik.WUtil.StringsUtil;
import com.example.weatherappkarthik.common.WConstants;
import com.example.weatherappkarthik.interfaces.IWeatherDataCallback;
import com.example.weatherappkarthik.interfaces.IWeatherTaskCallback;
import com.example.weatherappkarthik.data.Info;
import com.example.weatherappkarthik.data.LocationData;
import com.example.weatherappkarthik.tasks.WeatherTask;

import java.util.List;

/**
 * Created by Karthik on 4/5/2016.
 */
public class EnterDetailsFragment extends Fragment {

    private EditText mCityET, mStateET;
    private Button mGetDataBtn;
    private ProgressDialog mPd;
    private IWeatherDataCallback mWeatherDataCallback;
    private SharedPreferences mSharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    public void setmWeatherDataCallback(IWeatherDataCallback mWeatherDataCallback) {
        this.mWeatherDataCallback = mWeatherDataCallback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enter_details_layout, container, false);


        mCityET = (EditText) view.findViewById(R.id.cityET);
        mStateET = (EditText) view.findViewById(R.id.stateET);
        mGetDataBtn = (Button) view.findViewById(R.id.getDataButton);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mGetDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city = StringsUtil.getValidString(mCityET.getText().toString());
                String state = StringsUtil.getValidString(mStateET.getText().toString());

                List<Address> addressList = LocationData.getInstance().getAddressList();

                if (addressList != null && addressList.size() > 0 && (city.length() < 1 || state.length() < 1)) {
                    city = addressList.get(0).getLocality();
                    state = addressList.get(0).getCountryName();

                    mCityET.setText(city);
                    mStateET.setText(state);
                }


                //Validate city and state. Remove spaces in the end and beginning. replace spaces between the words with underscore

                String url = WConstants.URL_HEAD + state + "/" + city + WConstants.URL_TAIL;

                String[] urlArray = new String[]{url};

                WeatherTask weatherTask = new WeatherTask(weatherTaskCallback);
                weatherTask.execute(urlArray);


                mPd = ProgressDialog.show(getActivity(), "Loading..", "Please wait...");
            }
        });

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    IWeatherTaskCallback weatherTaskCallback = new IWeatherTaskCallback() {
        @Override
        public void getWeatherData(Info info) {
            if (mPd != null && mPd.isShowing())
                mPd.dismiss();


            //Error occured, show Error dialog box
            if (info == null || info.getmCurrentObservation().getTemp_c() == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage(R.string.error_message);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();

                return;
            }

            if (mWeatherDataCallback != null)
                mWeatherDataCallback.getWeatherData(info);
        }
    };
}
