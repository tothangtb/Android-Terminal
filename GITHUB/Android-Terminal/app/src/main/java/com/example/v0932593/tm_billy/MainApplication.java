package com.example.v0932593.tm_billy;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MAINAPP",this.toString());

    }


}
