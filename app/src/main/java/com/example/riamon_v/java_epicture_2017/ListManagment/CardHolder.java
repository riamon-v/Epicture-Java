package com.example.riamon_v.java_epicture_2017.ListManagment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.riamon_v.java_epicture_2017.R;

public class CardHolder extends RecyclerView.ViewHolder {
    private TextView titleView;
    private ImageView imageView;
   // public RelativeLayout viewBackground;
    //public LinearLayout viewForeground;
   // private LinearLayout mLinearLayout;
   // private Button drop;

    //itemView est la vue correspondante à 1 cellule
    public CardHolder(View itemView) {
        super(itemView);
        //c'est ici que l'on fait nos findView

        titleView = itemView.findViewById(R.id.titleCard);
        imageView = itemView.findViewById(R.id.imageCard);
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
     * @param item
     * @param listener
     */
    public void bind(final CardClass item, final AdapterCard.OnItemClickListener listener){

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
        imageView.setImageResource(item.getIdResources());
      /*  textViewView.setText(item.getTitle());
        dateViewView.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(item.getDate()));
        dateViewView.setText(dateViewView.getText() + " at " + item.getTime());
        contentViewView.setText(item.getContent());*/
    }

}

