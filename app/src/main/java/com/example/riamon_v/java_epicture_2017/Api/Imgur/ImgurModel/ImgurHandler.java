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

    /**
     * get all images
     * @param auth  #Type of authorization for upload
     * @param cb  Callback used for success/failures
     */
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

    /**
     * Request to get all the favorite pictures
     * @param auth  #Type of authorization for upload
     * @param cb  Callback used for success/failures
     */
    @GET("/3/account/me/favorites")
    void getFavoriteImages(
            @Header("Authorization") String auth,
            Callback<AllObjects.ListImageResponse> cb
    );

    /**
     * Request to delete an image by this hash
     * @param imageHash image ID
     * @param auth  #Type of authorization for upload
     * @param cb  Callback used for success/failures
     */
    @DELETE("/3/image/{imageHash}")
    void deleteImage(
            @Path("imageHash") String imageHash,
            @Header("Authorization") String auth,
            Callback<AllObjects.ManageImg> cb
    );

    /**
     * Request to ddit an image by this hash
     * @param imageHash image ID
     * @param auth #Type of authorization for upload
     * @param title title of the image
     * @param description description of the image
     * @param cb Callback used for success/failures
     */
    @POST("/3/image/{imageHash}")
    void editImage(
            @Path("imageHash") String imageHash,
            @Header("Authorization") String auth,
            @Query("title") String title,
            @Query("description") String description,
            Callback<AllObjects.ManageImg> cb
    );
}
