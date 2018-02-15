package com.example.riamon_v.java_epicture_2017.Api.Imgur.Services;

import android.content.Context;

import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.AllObjects;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.ImgurHandler;
import com.example.riamon_v.java_epicture_2017.Api.NetworkUtils;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by riamon_v on 15/02/2018.
 */

public class DeleteImageService extends Services {

    public DeleteImageService(Context ctx, User u, String id) {
        super(ctx, u, id);
    }

    public void Execute(Callback<AllObjects.DelImg> callback) {
        final Callback<AllObjects.DelImg> cb = callback;

        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }

        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(ImgurHandler.class).deleteImage(
                mId,
                "Bearer " + mUser.getTokenImgur(),
                cb);
    }
}
