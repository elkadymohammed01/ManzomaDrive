package com.great.mycar.model;

import android.content.Context;

import com.great.mycar.adapter.myDbAdapter;

public class SaveOprations {
    public static String[] getUser(Context context){
        myDbAdapter DB=new myDbAdapter(context);
        return DB.getData_inf();
    }
}
