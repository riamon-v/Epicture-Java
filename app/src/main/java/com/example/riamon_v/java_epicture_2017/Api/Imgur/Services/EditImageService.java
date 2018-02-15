package com.example.riamon_v.java_epicture_2017.Api.Imgur.Services;

import android.content.Context;

import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.AllObjects;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.ImgurHandler;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.UploadObject;
import com.example.riamon_v.java_epicture_2017.Api.NetworkUtils;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by riamon_v on 15/02/2018.
 */

public class EditImageService extends Services {

    public EditImageService(Context ctx, User u, String id) {
        super(ctx, u, id);
    }

    public void Execute(UploadObject upload, Callback<AllObjects.ImageResponse> callback) {
        final Callback<AllObjects.ImageResponse> cb = callback;

        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }

        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(ImgurHandler.class).editImage(
                    mId,
                "Bearer " + mUser.getTokenImgur(),
                     upload.title,
                     upload.description,
                     cb);
    }
}
