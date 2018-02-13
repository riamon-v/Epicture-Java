package com.example.riamon_v.java_epicture_2017.Api.Imgur.Services;

import android.content.Context;

import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.ImageResponse;
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

public class UploadService {
        private WeakReference<Context> mContext;
        private User user;

        public UploadService(Context context, User user) {
            this.mContext = new WeakReference<>(context);
            this.user = user;
        }

        public void Execute(UploadObject upload, Callback<ImageResponse> callback) {
            final Callback<ImageResponse> cb = callback;

            if (!NetworkUtils.isConnected(mContext.get())) {
                //Callback will be called, so we prevent a unnecessary notification
                cb.failure(null);
                return;
            }

            RestAdapter restAdapter = buildRestAdapter();

            restAdapter.create(ImgurHandler.class).postImage(
                    "Bearer " + user.getTokenImgur(),
                    upload.title,
                    upload.description,
                    null,
                    null,
                    new TypedFile("image/*", upload.image),
                    cb);
        }

        private RestAdapter buildRestAdapter() {
            RestAdapter imgurAdapter = new RestAdapter.Builder()
                    .setEndpoint(ImgurHandler.server)
                    .build();
            imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
            return imgurAdapter;
        }
}
