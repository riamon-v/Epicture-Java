package com.example.riamon_v.java_epicture_2017.ListManagment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.riamon_v.java_epicture_2017.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCard extends RecyclerView.Adapter<CardHolder> {

    private List<CardClass> list = new ArrayList<>();
    private OnItemClickListener listener;

    public AdapterCard(List<CardClass> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(CardClass item);
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        return new CardHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(CardHolder cardHolder, int position) {
        CardClass task = list.get(position);
        cardHolder.bind(task, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*
     * remove task
     * @param position
     *
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * restor task when undo
     * @param item the task
     * @param position position in adapter
     *
    public void restoreItem(CardClass item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }*/

}
