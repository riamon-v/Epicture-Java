package com.example.riamon_v.java_epicture_2017.Api.Imgur.Services;

import android.content.Context;

import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.AllObjects;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.ImgurHandler;
import com.example.riamon_v.java_epicture_2017.Api.NetworkUtils;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by riamon_v on 14/02/2018.
 */

public class ImagesService extends Services {

    public ImagesService(Context ctx, User u) {
        super(ctx, u);
    }

    public void Execute(Callback<AllObjects.ListImageResponse> callback, boolean fav) {
        final Callback<AllObjects.ListImageResponse> cb = callback;

        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }

        RestAdapter restAdapter = buildRestAdapter();

        if (!fav)
            restAdapter.create(ImgurHandler.class).getImages(
                "Bearer " + mUser.getTokenImgur(),
                   cb);
        else
            restAdapter.create(ImgurHandler.class).getFavoriteImages(
                    "Bearer " + mUser.getTokenImgur(),
                    cb);
    }
}
