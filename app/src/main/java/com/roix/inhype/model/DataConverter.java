package com.roix.inhype.model;

import android.util.Log;
import android.util.Pair;

import com.roix.inhype.MyApplication;
import com.roix.inhype.R;
import com.roix.inhype.pojo.Comment;
import com.roix.inhype.pojo.ServerErrorResponse;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.ProfileShort;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roix on 21.06.2017.
 */

public class DataConverter {
    private static final Annotation[] ANNOTATIONS = new Annotation[0];

    public static String convertToStringIds(List<Photo> list){
        String ret="";
        for (Photo photo:list){
            ret+=photo.getOwnerUser()+",";
        }
        return ret;
    }
    public static String convertCommentsToStringIds(List<Comment> list){
        String ret="";
        for (Comment photo:list){
            ret+=photo.getAuthorId()+",";
        }
        return ret;
    }


    public static List<Photo> fetchProfilesAndPhotos(List<Photo> photos, List<ProfileShort> profiles){
        Log.d("DataConverter","photos "+photos.size()+" profile "+profiles.size());
        for(Photo photo:photos){
            for (ProfileShort profile:profiles){
                Log.d("DataConverter","photo "+photo.getOwnerUser()+" profile "+profile.getId());
                if(photo.getOwnerUser().equals(profile.getId()))photo.setProfile(profile);
            }
        }
        return photos;
    }
    public static List<Comment> fetchProfilesAndComments(List<Comment> photos, List<ProfileShort> profiles){
        Log.d("DataConverter","Comment "+photos.size()+" profile "+profiles.size());
        for(Comment photo:photos){
            for (ProfileShort profile:profiles){
                Log.d("DataConverter","Comment "+photo.getAuthorId()+" profile "+profile.getId());
                if(photo.getAuthorId().equals(profile.getId()))photo.setProfile(profile);
            }
        }
        return photos;
    }


    public static Pair<Integer, Integer> getCategoryContentById(int id ){
        switch (id){
            case 0: return new Pair<>(R.mipmap.ava,R.string.category_no);
            case 1: return new Pair<>(R.drawable.cat,R.string.category_animal);
            case 2: return new Pair<>(R.drawable.hamburger,R.string.category_food);
            case 3: return new Pair<>(R.drawable.goal,R.string.category_nature);
            case 4: return new Pair<>(R.drawable.weight,R.string.category_sport);
            case 5: return new Pair<>(R.drawable.game,R.string.category_games);
            case 6: return new Pair<>(R.drawable.carnaval,R.string.category_cosplay);
            case 7: return new Pair<>(R.drawable.network,R.string.category_peoples);
            case 8: return new Pair<>(R.drawable.lipstick,R.string.category_beauty);
            case 9: return new Pair<>(R.drawable.ingot,R.string.category_prestige);
            case 10: return new Pair<>(R.drawable.fireworks,R.string.category_events);
            case 11: return new Pair<>(R.drawable.heart_cat,R.string.category_erotic);
            case 12: return new Pair<>(0,0);
        }
        return new Pair<>(0,0);
    }

    public static int getLevelResLevel(int level){
        if(level<5)return R.drawable.l_4_bg;
        else if(level<10)return R.drawable.l_9_bg;
        else if(level<15)return R.drawable.l_14_bg;
        else if(level<20)return R.drawable.l_19_bg;
        else if(level<25)return R.drawable.l_24_bg;
        else if(level<30)return R.drawable.l_29_bg;
        else if(level<35)return R.drawable.l_33_bg;
        else if(level<40)return R.drawable.l_30_bg;
        else return R.drawable.l_40_bg;
    }

    public static String handleErrorMessage(Throwable throwable){
        if(throwable instanceof HttpException){
            try {
                HttpException exception = (HttpException) throwable;
                Response response = exception.response();

                final Converter<ResponseBody, ServerErrorResponse> converter =
                        MyApplication.getContentRepository().getRetrofit().responseBodyConverter(ServerErrorResponse.class, ANNOTATIONS);
                final ServerErrorResponse serverErrorResponse = converter.convert(response.errorBody());
                return serverErrorResponse.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return throwable.toString();
    }



}
