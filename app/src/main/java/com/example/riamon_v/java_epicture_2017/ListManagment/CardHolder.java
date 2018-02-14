package com.example.riamon_v.java_epicture_2017.ListManagment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.AllObjects;
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
    Context context;
    // public RelativeLayout viewBackground;
    //public LinearLayout viewForeground;
    // private LinearLayout mLinearLayout;
    // private Button drop;

    //itemView est la vue correspondante à 1 cellule
    public CardHolder(View itemView, Context ctx) {
        super(itemView);
        //c'est ici que l'on fait nos findView

        titleView = itemView.findViewById(R.id.titleCard);
        imageView = itemView.findViewById(R.id.imageCard);
        favButton = itemView.findViewById(R.id.buttonFav);
        context = ctx;
        // mLinearLayout = itemView.findViewById(R.id.expandable);
        //set visibility to GONE
        // mLinearLayout.setVisibility(View.GONE);
        //drop = itemView.findViewById(R.id.drop);

        //  viewBackground = itemView.findViewById(R.id.view_background);
        // viewForeground = itemView.findViewById(R.id.view_foreground);
    }

    /*private ValueAnimator slideAnimator(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    /*
     * Down the content
     *
    private void expand() {
        mLinearLayout.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mLinearLayout.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight());
        mAnimator.start();
    }

    /**
     * Hide the content
     *
    private void collapse() {
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mAnimator.start();
    }*/

    /**
     * Bind the "See more" button on a task item
     *
     * @param item
     * @param listener
     */
    public void bind(final CardClass item, final AdapterCard.OnItemClickListener listener) {

        if (item.fav == "true")
            favButton.setImageResource(R.drawable.ic_favorite_black_24dp);

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Log.d("FAV1", item.fav);
                reverseFav(MainActivity.user, item.getId(), item.fav);
                item.fav = Objects.equals(item.fav, "true") ? "false" : "true";
                //Log.d("FAV2", item.fav);
            }
        });
       /* drop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLinearLayout.getVisibility() == View.GONE){
                    expand();
                }
                else {
                    collapse();
                }
            }
        });*/

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // listener.onItemClick(item);
            }
        });

        titleView.setText(item.getTitle());
        if (item.getIdResources() != -1)
            imageView.setImageResource(item.getIdResources());
        else {
            Picasso.with(context).load(item.getUrl()).into(imageView);
        }

      /*  textViewView.setText(item.getTitle());
        dateViewView.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(item.getDate()));
        dateViewView.setText(dateViewView.getText() + " at " + item.getTime());
        contentViewView.setText(item.getContent());*/
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

