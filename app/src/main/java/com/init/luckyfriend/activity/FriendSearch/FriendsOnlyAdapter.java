package com.init.luckyfriend.activity.FriendSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.CircleImageView;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Singleton;

import java.util.List;

/**
 * Created by user on 2/19/2016.
 */
public class FriendsOnlyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<WallDataBean> data;
    public FriendsOnlyAdapter(Context context, List<WallDataBean> list) {
        this.context = context;data=list;
    }
    public FriendsOnlyAdapter(Context context) {
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.friends_only_item_feed, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

        WallDataBean gd=data.get(position);
        //holder.likes.setText(gd.getPost_likes() + "");
        Singleton.imageLoader.displayImage(gd.getPerson_profile_img(), holder.ivUserProfile, Singleton.defaultOptions);

        // post images
        Singleton.imageLoader.displayImage(gd.getPerson_profile_img(), holder.ivFeedCenter, Singleton.defaultOptions);


        holder.country.setText(gd.getPerson_country());
        //holder.comment.setText(gd.getPost_comments());
        holder.name.setText(gd.getUser_name());

    }



    @Override
    public int getItemCount() {
        if(data==null)
            return 0;
        else
        return data.size();

       // return 10;
    }

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivUserProfile;
        ImageView ivFeedCenter;
        TextView caption, likes, comment, country, name;
        ImageButton commenticon,likeicon;

        public CellFeedViewHolder(View view) {
            super(view);


            ivUserProfile = (CircleImageView) view.findViewById(R.id.ivUserProfile);
            ivFeedCenter = (ImageView) view.findViewById(R.id.ivFeedCenter);
            likeicon = (ImageButton) view.findViewById(R.id.like_icon);
            commenticon = (ImageButton) view.findViewById(R.id.comment_icon);
            caption = (TextView) view.findViewById(R.id.caption);
            likes = (TextView) view.findViewById(R.id.likes);
            comment = (TextView) view.findViewById(R.id.comments);
            country = (TextView) view.findViewById(R.id.country);
            name = (TextView) view.findViewById(R.id.name);

        }
    }


}
