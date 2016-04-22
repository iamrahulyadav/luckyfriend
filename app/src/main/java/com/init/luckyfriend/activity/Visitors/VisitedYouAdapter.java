package com.init.luckyfriend.activity.Visitors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.init.luckyfriend.R;

import java.util.List;


import com.init.luckyfriend.activity.CircleImageView;
import com.init.luckyfriend.activity.DATA.FavouriteDataBean;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Singleton;

import org.w3c.dom.Text;

/**
 * Created by user on 2/22/2016.
 */
public class VisitedYouAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<VisitedYouDataBean> data;
    public VisitedYouAdapter(Context context, List<VisitedYouDataBean> list) {
        this.context = context;data=list;
    }
    public VisitedYouAdapter(Context context) {
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.visitedyou_item_feed, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

        VisitedYouDataBean gd=data.get(position);
        Singleton.imageLoader.displayImage(gd.getPerson_profile_pic(),holder.ivUserProfile,Singleton.defaultOptions);
        Singleton.imageLoader.displayImage(gd.getPost_img(),holder.ivFeedCenter,Singleton.defaultOptions);

        holder.fullname.setText(gd.getUser_name()+" "+gd.getLast_name());
        holder.yearsandcountry.setText(gd.getPerson_dob()+","+" "+gd.getPerson_country());
        holder.likescount.setText(gd.getPost_likes());
        holder.commentcount.setText(gd.getPost_comments());
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
        TextView fullname,yearsandcountry,likescount,commentcount;
        ImageButton cross,like,comments;
        TextView caption;

        public CellFeedViewHolder(View view) {
            super(view);

            ivUserProfile = (CircleImageView) view.findViewById(R.id.ivUserProfile);
            ivFeedCenter = (ImageView) view.findViewById(R.id.ivFeedCenter);
            fullname=(TextView)view.findViewById(R.id.fullname);
            yearsandcountry=(TextView)view.findViewById(R.id.yearsandcountry);
            cross=(ImageButton)view.findViewById(R.id.cross);
            like=(ImageButton)view.findViewById(R.id.like);
            comments=(ImageButton)view.findViewById(R.id.comments);
            likescount=(TextView)view.findViewById(R.id.likescount);
            commentcount=(TextView)view.findViewById(R.id.commentcount);
            caption = (TextView) view.findViewById(R.id.caption);

        }
    }


}
