package com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

public interface ImgurHandler {

    String server = "https://api.imgur.com";

    /**
     * @param auth        #Type of authorization for upload
     * @param title       #Title of image
     * @param description #Description of image
     * @param albumId     #ID for album (if the user is adding this image to an album)
     * @param username    username for upload
     * @param file        image
     * @param cb          Callback used for success/failures
     */
    @POST("/3/image")
    void postImage(
            @Header("Authorization") String auth,
            @Query("title") String title,
            @Query("description") String description,
            @Query("album") String albumId,
            @Query("account_url") String username,
            @Body TypedFile file,
            Callback<AllObjects.ImageResponse> cb
    );

    @GET("/3/account/me/images")
    void getImages(
            @Header("Authorization") String auth,
            Callback<AllObjects.ListImageResponse> cb
    );

    /**
     * get all user's images favorite
     * @param imageHash   image ID
     * @param auth        #Type of authorization for upload
     * @param dummy
     */
    @POST("/3/image/{imageHash}/favorite")
    void postAddFavoriteImage(
            @Path("imageHash") String imageHash,
            @Header("Authorization") String auth,
            @Body Object dummy,
            Callback<AllObjects.AddFav> cb
    );

    @GET("/3/account/me/favorites")
    void getFavoriteImages(
            @Header("Authorization") String auth,
            Callback<AllObjects.ListImageResponse> cb
    );

    @DELETE("/3/image/{imageHash}")
    void deleteImage(
            @Path("imageHash") String imageHash,
            @Header("Authorization") String auth,
            Callback<AllObjects.ImageResponse> cb
    );

    @POST("3/image/{imageHash")
    void editImage(
            @Path("imageHash") String imageHash,
            @Header("Authorization") String auth,
            @Query("title") String title,
            @Query("description") String description,
            Callback<AllObjects.ImageResponse> cb
    );
}
