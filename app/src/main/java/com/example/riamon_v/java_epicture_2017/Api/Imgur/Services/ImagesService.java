package com.example.riamon_v.java_epicture_2017.Api.Imgur.Services;

import android.content.Context;

import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.ImgurHandler;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.ListImageResponse;
import com.example.riamon_v.java_epicture_2017.Api.NetworkUtils;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by riamon_v on 14/02/2018.
 */

public class ImagesService {

    private WeakReference<Context> mContext;
    private User mUser;
    //private String mSort;

    public ImagesService(Context context, User user) {
        this.mContext = new WeakReference<>(context);
        this.mUser = user;
      //  mSort = "newest";
    }

    public ImagesService(Context context, User user, String sort) {
        this.mContext = new WeakReference<>(context);
        this.mUser = user;
       /* if (!sort.equals("oldest"))
            this.mSort = "newest";
        else this.mSort = sort;*/
    }

    public void Execute(Callback<ListImageResponse> callback) {
        final Callback<ListImageResponse> cb = callback;

        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }

        RestAdapter restAdapter = buildRestAdapter();

        restAdapter.create(ImgurHandler.class).getImages(
                "Bearer " + mUser.getTokenImgur(),
                cb
        );
    }

    private RestAdapter buildRestAdapter() {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(ImgurHandler.server)
                .build();
        imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return imgurAdapter;

    }
}
