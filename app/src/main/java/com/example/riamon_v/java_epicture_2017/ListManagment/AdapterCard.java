package com.example.riamon_v.java_epicture_2017.ListManagment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.riamon_v.java_epicture_2017.AddActuality.AddActivity;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.AllObjects;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.UploadObject;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.Services.DeleteImageService;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.Services.UploadService;
import com.example.riamon_v.java_epicture_2017.MainActivity;
import com.example.riamon_v.java_epicture_2017.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdapterCard extends RecyclerView.Adapter<CardHolder> {

    private List<CardClass> list = new ArrayList<>();
    private OnItemClickListener listener;
    private Context ctx;
    private URI uri;

    public AdapterCard(List<CardClass> list, OnItemClickListener listener, Context ctx) {
        this.list = list;
        this.listener = listener;
        this.ctx = ctx;
    }

    public interface OnItemClickListener {
        void onLongClick(CardClass item);
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        return new CardHolder(view, ctx);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(final CardHolder cardHolder, final int position) {
        final CardClass item = list.get(position);
        ImageButton buttonMenu = cardHolder.getButtonMenu();

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardHolder.getPopup().setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick (MenuItem menuItem){
                        switch (menuItem.getItemId()) {
                            case R.id.action_edit:
                                editImage(item);
                                //Log.d("EDIT", "click");
                                return true;
                            case R.id.action_delete:
                                deleteImage(item, position);
                                return true;
                        }
                        return false;
                    }
                });
                cardHolder.getPopup().show();
            }
        });

        cardHolder.bind(item, listener);
    }

    private void editImage(CardClass item) {
        Intent intentUpload = new Intent(ctx, AddActivity.class);

        intentUpload.putExtra("item", item);
        intentUpload.putExtra("idUser", MainActivity.user.getId());
        ctx.startActivity(intentUpload);
        ((Activity) ctx).finish();
    }

    private void deleteImage(CardClass item, final int position) {
        new DeleteImageService(ctx, MainActivity.user, item.getId()).
                Execute(new Callback<AllObjects.ManageImg>() {
                    @Override
                    public void success(AllObjects.ManageImg imageResponse, Response response) {
                        final CardClass deletedItem = list.get(position);
                        removeItem(position);
                        Snackbar snackbar = Snackbar
                                .make(MainActivity.container, deletedItem.getTitle() + ctx.getString(R.string.deletedMessage), Snackbar.LENGTH_LONG);
                        snackbar.setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                restorImgImgur(deletedItem, position);
                            }
                        });
                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("updatefail", error.toString());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*
             * remove task
             * @param position
             *
             **/
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * restor task when undo
     * @param item the task
     * @param position position in adapter
     *
     **/
    public void restoreItem(CardClass item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    private void restorImgImgur(CardClass deletedItem, int position) {
        UploadObject upload = new UploadObject();

        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(deletedItem.getUrl());
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();

        upload.image = new File(ctx.getCacheDir(), "tmp");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(upload.image);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        upload.title = deletedItem.getTitle();
        upload.description = deletedItem.getContent();
        new UploadService(ctx, MainActivity.user).
                Execute(upload, new Callback<AllObjects.ImageResponse>() {
                    @Override
                    public void success(AllObjects.ImageResponse imageResponse, Response response) {
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
        restoreItem(deletedItem, position);
    }
}
