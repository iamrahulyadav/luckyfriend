package com.init.luckyfriend.activity.WallSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.init.luckyfriend.R;

import java.util.Calendar;
import java.util.List;


import com.init.luckyfriend.activity.DATA.FavouriteDataBean;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Singleton;

/**
 * Created by user on 2/19/2016.
 */
public class FavouriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<FavouriteDataBean> data;
    public FavouriteAdapter(Context context, List<FavouriteDataBean> list) {
        this.context = context;data=list;
    }
    public FavouriteAdapter(Context context) {
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.wall_search_item_feed, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

        FavouriteDataBean gd=data.get(position);

        holder.likecount.setText(gd.getPost_likes() + "");
        Singleton.imageLoader.displayImage(gd.getPost_img(), holder.ivFeedCenter, Singleton.defaultOptions);
        Singleton.imageLoader.displayImage(gd.getPost_user_profile_pic(), holder.userimg, Singleton.defaultOptions);
        holder.username.setText(gd.getPost_user_first_name() + " " + gd.getPost_user_last_name());

        holder.userdetails.setText(gd.getPost_user_dob() + "," + gd.getPost_user_country());

       }


    @Override
    public int getItemCount() {
        if(data==null)
            return 0;
        else
        return data.size();

         }

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFeedCenter,userimg;
        TextView caption;
        ImageButton likes,comments;
        TextView likecount,username,userdetails;


        public CellFeedViewHolder(View view) {
            super(view);

            ivFeedCenter = (ImageView) view.findViewById(R.id.ivFeedCenter);
            userimg=(ImageView)view.findViewById(R.id.ivUserProfile);
            likecount=(TextView)view.findViewById(R.id.likescount);
            username=(TextView)view.findViewById(R.id.username);
            userdetails=(TextView)view.findViewById(R.id.details);
            caption = (TextView) view.findViewById(R.id.caption);
            likes=(ImageButton)view.findViewById(R.id.like);
            comments=(ImageButton)view.findViewById(R.id.comments);

        }
    }


}
