package com.example.riamon_v.java_epicture_2017.ListManagment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.AllObjects;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.Services.DeleteImageService;
import com.example.riamon_v.java_epicture_2017.MainActivity;
import com.example.riamon_v.java_epicture_2017.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdapterCard extends RecyclerView.Adapter<CardHolder> {

    private List<CardClass> list = new ArrayList<>();
    private OnItemClickListener listener;
    private Context ctx;

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
                                Log.d("EDIT", "click");
                                return true;
                            case R.id.action_delete:
                                new DeleteImageService(ctx, MainActivity.user, item.getId()).
                                        Execute(new Callback<AllObjects.DelImg>() {
                                            @Override
                                            public void success(AllObjects.DelImg imageResponse, Response response) {
                                                removeItem(position);
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
                cardHolder.getPopup().show();
            }
        });

        cardHolder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CardClass> list) {
        this.list = list;
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

}
