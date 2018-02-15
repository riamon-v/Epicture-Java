package com.example.riamon_v.java_epicture_2017.Api.Imgur.Services;

import android.content.Context;

import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.ImgurHandler;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;

import java.lang.ref.WeakReference;

import retrofit.RestAdapter;

/**
 * Created by riamon_v on 14/02/2018.
 */

public class Services {

    protected WeakReference<Context> mContext;
    protected User mUser;
    protected String mId = null;

    public Services(Context ctx, User u) {
        mUser = u;
        mContext = new WeakReference<>(ctx);
    }

    public Services(Context ctx, User u, String id) {
        mUser = u;
        mContext = new WeakReference<>(ctx);
        mId = id;
    }

    protected RestAdapter buildRestAdapter() {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(ImgurHandler.server)
                .build();
        imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return imgurAdapter;

    }
}
