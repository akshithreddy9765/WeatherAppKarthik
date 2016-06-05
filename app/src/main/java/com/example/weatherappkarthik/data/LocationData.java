package com.example.weatherappkarthik.data;

import android.location.Address;

import java.util.List;

/**
 * Created by chethan on 4/6/2016.
 */
public class LocationData {
    private static LocationData ourInstance = new LocationData();

    private List<Address> addressList;
    public static LocationData getInstance() {
        return ourInstance;
    }

    private LocationData() {
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
