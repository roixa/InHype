package com.roix.inhype;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.roix.inhype.model.ContentRepository;
import com.roix.inhype.model.OurApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roix on 11.05.2017.
 */

public class MyApplication extends Application {

    private static Context context;
    private static ContentRepository contentRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    public static SharedPreferences getPreference(){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static ContentRepository getContentRepository(){
        if(contentRepository==null)return new ContentRepository(getContext());
        return contentRepository;
    }

    public static Context getContext(){
        return context;
    }

}
