package com.rakesh_ap2016.hw5_rakeshne;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rakeshh91 on 2/22/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<Map<String, ?>> mDataSet;
    private Context mContext;
    RecyclerItemClickListener mItemClickListener;


    public MyRecyclerViewAdapter(Context myContext, List<Map<String, ?>> myDataSet) {
        mContext = myContext;
        mDataSet = myDataSet;

    }

    @Override
    public int getItemViewType(int position) {
        HashMap<String,?> mObject = (HashMap<String,?>)mDataSet.get(position);
        Double rating = (Double)mObject.get("rating");
        if(rating<8.0){
            return 2;
        }
        else{
            return 2;
        }

    }

    public void animate(MyRecyclerViewAdapter.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType){
            case 2:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_card_view, parent, false);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_card_view_rating, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_card_view, parent, false);
                break;
        }

        CardView card = (CardView) v.findViewById(R.id.card);
        card.setCardElevation(20f);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map<String, ?> movie = mDataSet.get(position);
        holder.bindMovieData(movie);
        animate(holder);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void SetOnItemClickListener(final RecyclerItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public ImageView vImageView;
        public TextView vRating;

        public void bindMovieData(Map<String, ?> movie) {
            vTitle.setText((String) movie.get("name"));
            vDescription.setText((String) movie.get("description"));
            vIcon.setImageResource((Integer) movie.get("image"));
            vRating.setText("Rating: " + ((Double) movie.get("rating")));
        }

        public ViewHolder(View v) {
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.icon);
            vTitle = (TextView) v.findViewById(R.id.movieNameTextView);
           vDescription = (TextView) v.findViewById(R.id.description);
            vImageView = (ImageView) v.findViewById(R.id.vertImage);
            vRating = (TextView) v.findViewById(R.id.ratingTextView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v, getPosition());
                    }
                    return true;
                }
            });

            if(vImageView!=null){
                vImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onOverflowMenuClick(v, getPosition());
                        }
                    }
                });
            }
        }
    }
}
