package com.example.weatherappkarthik.data;

/**
 * Created by Karthik on 4/5/2016.
 */
public class CurrentObservation {
    private String mTemp_f, mTemp_c, fullName;


    public String getTemp_f() {
        return mTemp_f;
    }

    public void setTemp_f(String temp_f) {
        this.mTemp_f = temp_f;
    }

    public String getTemp_c() {
        return mTemp_c;
    }

    public void setTemp_c(String temp_c) {
        this.mTemp_c = temp_c;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
