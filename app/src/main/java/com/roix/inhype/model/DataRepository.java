package com.roix.inhype.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Pair;

import com.google.gson.Gson;
import com.roix.inhype.R;
import com.roix.inhype.pojo.LoginResponse;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by roix on 17.06.2017.
 */

public class DataRepository {

    public static final String filePath= Environment.getExternalStorageDirectory()+"/inhype/";
    private SharedPreferences preferences;

    public DataRepository(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveLoginInfo(LoginResponse response){
        String json=new Gson().toJson(response);
        preferences.edit().putString("loginInfo",json).apply();
    }

    public LoginResponse getLoginInfo(){
        String json=preferences.getString("loginInfo","");
        return new Gson().fromJson(json,LoginResponse.class);
    }
    public String getDid(){
        return preferences.getString("did",null);
    }

    public void saveDid(String did){
        preferences.edit().putString("did",did).commit();
    }

    ///public static Pair<Integer,Integer> getCategoryContentById(int id){}


}
