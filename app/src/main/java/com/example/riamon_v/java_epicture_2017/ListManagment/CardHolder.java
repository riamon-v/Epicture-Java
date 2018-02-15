package com.example.riamon_v.java_epicture_2017.ListManagment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.AllObjects;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.Services.DeleteImageService;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.Services.FavoriteService;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;
import com.example.riamon_v.java_epicture_2017.MainActivity;
import com.example.riamon_v.java_epicture_2017.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CardHolder extends RecyclerView.ViewHolder {
    private TextView titleView;
    private ImageView imageView;
    private ImageButton favButton;
    private ImageButton buttonMenu;
    private AdapterCard adapterCard;
    Context context;


    public CardHolder(View itemView, Context ctx, AdapterCard a) {
        super(itemView);
        //c'est ici que l'on fait nos findView

        titleView = itemView.findViewById(R.id.titleCard);
        imageView = itemView.findViewById(R.id.imageCard);
        favButton = itemView.findViewById(R.id.buttonFav);
        buttonMenu = itemView.findViewById(R.id.buttonMenu);
        context = ctx;
        adapterCard = a;
    }

    /**
     * Bind the "See more" button on a task item
     *
     * @param item
     * @param listener
     */
    public void bind(final CardClass item, final AdapterCard.OnItemClickListener listener) {

        if (item == null)
            return;
        if (item.fav == "false")
            favButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reverseFav(MainActivity.user, item.getId(), item.fav);
                item.fav = Objects.equals(item.fav, "true") ? "false" : "true";
            }
        });

        final PopupMenu popup = new PopupMenu(context, buttonMenu);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_card_view, popup.getMenu());

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick (MenuItem menuItem){
                        switch (menuItem.getItemId()) {
                            case R.id.action_edit:
                                Log.d("EDIT", "click");
                                return true;
                            case R.id.action_delete:
                                new DeleteImageService(context, MainActivity.user, item.getId()).
                                        Execute(new Callback<AllObjects.ImageResponse>() {
                                            @Override
                                            public void success(AllObjects.ImageResponse imageResponse, Response response) {
                                                adapterCard.removeItem(getAdapterPosition());
                                                Log.i("updateok", "ok");
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                Log.i("updatefail", error.toString());
                                            }
                                        });
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        titleView.setText(item.getTitle());
        if (item.getIdResources() != -1)
            imageView.setImageResource(item.getIdResources());
        else {
            Picasso.with(context).load(item.getUrl()).into(imageView);
        }
    }

    private void reverseFav(User u, String id, final String fav) {
        new FavoriteService(context, u, id).
                Execute(new Callback<AllObjects.AddFav>() {
                    @Override
                    public void success(AllObjects.AddFav getImagesResponse, Response response) {
                        if (Objects.equals(fav, "false"))
                            favButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                        else
                            favButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        Log.i("updateok", "ok");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("updatefail", error.toString());
                    }
                });

    }
}

