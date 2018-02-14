package com.example.riamon_v.java_epicture_2017.Api.Imgur.Services;

import android.content.Context;

import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.AllObjects;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.ImgurHandler;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.UploadObject;
import com.example.riamon_v.java_epicture_2017.Api.NetworkUtils;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.mime.TypedFile;

/**
 * Created by riamon_v on 13/02/2018.
 */

public class UploadService extends Services {

        public UploadService(Context ctx, User u) {
            super(ctx, u);
        }

        public void Execute(UploadObject upload, Callback<AllObjects.ImageResponse> callback) {
            final Callback<AllObjects.ImageResponse> cb = callback;

            if (!NetworkUtils.isConnected(mContext.get())) {
                //Callback will be called, so we prevent a unnecessary notification
                cb.failure(null);
                return;
            }

            RestAdapter restAdapter = buildRestAdapter();

            restAdapter.create(ImgurHandler.class).postImage(
                    "Bearer " + mUser.getTokenImgur(),
                    upload.title,
                    upload.description,
                    null,
                    null,
                    new TypedFile("image/*", upload.image),
                    cb);
        }

}
