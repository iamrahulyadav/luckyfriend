package com.init.luckyfriend.activity.HomeFragment;

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
import com.init.luckyfriend.activity.DATA.CommentData;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/25/2016.
 */
public class CommentArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private ArrayList<CommentData> data;


    public CommentArrayAdapter(Context context, ArrayList<CommentData> list) {
        this.context = context;
        data=list;
    }
    public CommentArrayAdapter(Context context) {
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.commentrecycler, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

        CommentData gd=data.get(position);
        //holder.likes.setText(gd.getPost_likes() + "");
        Singleton.imageLoader.displayImage(gd.getProfilepic(), holder.commentpic, Singleton.defaultOptions);

        // post images
        //Singleton.imageLoader.displayImage(gd.getPost_img(), holder.ivFeedCenter, Singleton.defaultOptions);


        holder.cname.setText(gd.getUname());
        holder.comments.setText(gd.getCommenttxxt());
        holder.ctime.setText(gd.getCtime());

        //holder.likes.setText(gd.getPost_likes()+"");
    }



    @Override
    public int getItemCount() {
        if(data==null)
            return 0;
        else
            return data.size();
    }

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        CircleImageView commentpic;
        TextView cname,comments,ctime;

        public CellFeedViewHolder(View view) {
            super(view);


            commentpic = (CircleImageView) view.findViewById(R.id.commentpic);
            cname=(TextView)view.findViewById(R.id.cname);
            comments=(TextView)view.findViewById(R.id.comments);
            ctime=(TextView)view.findViewById(R.id.ctime);


        }
    }


}
