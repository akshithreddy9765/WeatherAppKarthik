package com.example.weatherappkarthik;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.weatherappkarthik.common.WConstants;
import com.example.weatherappkarthik.fragments.DisplayDetailsFragment;
import com.example.weatherappkarthik.fragments.EnterDetailsFragment;
import com.example.weatherappkarthik.interfaces.IWeatherDataCallback;
import com.example.weatherappkarthik.location.GetLocation;
import com.example.weatherappkarthik.data.Info;

public class MainActivity extends AppCompatActivity {

    private EnterDetailsFragment mEnterDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        mEnterDetailsFragment = (EnterDetailsFragment) fm.findFragmentByTag(WConstants.ENTER_DETAILS_FRAGMENT_TAG);


        if (mEnterDetailsFragment == null) {


            mEnterDetailsFragment = new EnterDetailsFragment();
            mEnterDetailsFragment.setmWeatherDataCallback(weatherDataCallback);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.mainContainer, mEnterDetailsFragment, WConstants.ENTER_DETAILS_FRAGMENT_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    WConstants.MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {
            GetLocation getLocation = new GetLocation(MainActivity.this);
            getLocation.getLocationFromGPS();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WConstants.MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    GetLocation getLocation = new GetLocation(MainActivity.this);
                    getLocation.getLocationFromGPS();


                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.accept_permission), Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            WConstants.MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    IWeatherDataCallback weatherDataCallback = new IWeatherDataCallback() {
        @Override
        public void getWeatherData(Info info) {
            //Remove the Enter details fragment
            //Pass the info obj to the display details fragment
            //Attach the display details fragment

            DisplayDetailsFragment displayDetailsFragment = new DisplayDetailsFragment();
            displayDetailsFragment.setmInfo(info);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.mainContainer, displayDetailsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else
            super.onBackPressed();
    }
}
