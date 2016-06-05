package com.example.weatherappkarthik.WUtil;

/**
 * Created by chethan on 8/4/2015.
 */
public class StringsUtil {

    public static String getValidString(String city){
        if(city != null && city.length() > 0){
            city = city.trim();
            city = city.replace(" ", "_");
        }
        return city;
    }
}
