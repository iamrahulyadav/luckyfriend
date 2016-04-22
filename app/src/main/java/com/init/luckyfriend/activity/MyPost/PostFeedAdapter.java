package com.init.luckyfriend.activity.MyPost;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.CircleImageView;
import com.init.luckyfriend.activity.Singleton;

import java.util.List;


public class PostFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;

    private List<PostDataBean> data;

    public PostFeedAdapter(Context context, List<PostDataBean> list) {
        this.context = context;
        data = list;
    }

    public PostFeedAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.mypost_row_layout, parent, false);


        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

       PostDataBean gd=data.get(position);




        Singleton.imageLoader.displayImage(gd.getPerson_profile_pic(),holder.profilepic,Singleton.defaultOptions);
        Singleton.imageLoader.displayImage(gd.getPost_img(),holder.postimage,Singleton.defaultOptions);

        holder.name.setText(gd.getUser_name() + " " + gd.getLast_name());
        holder.date.setText(gd.getPost_date());
        holder.like.setText(gd.getPost_likes());
        holder.comment.setText(gd.getPost_comments());
       }


    @Override
    public int getItemCount() {
        if(data==null)
            return 0;
        else
        return data.size();

    }

    public class CellFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView  postimage, options;
        CircleImageView profilepic;
        TextView name, date, comment, like;

        public CellFeedViewHolder(View view) {
            super(view);

            profilepic = (CircleImageView) view.findViewById(R.id.profilepic);
            postimage = (ImageView) view.findViewById(R.id.ivFeedCenter);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            comment = (TextView) view.findViewById(R.id.comments);
            like = (TextView) view.findViewById(R.id.likes);

            options = (ImageView) view.findViewById(R.id.options);

            options.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.options) {
                Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
                PopupMenu popupMenu = new PopupMenu(wrapper, view);
                popupMenu.setOnMenuItemClickListener(new MenuClick(getPosition()));
                popupMenu.inflate(R.menu.action_menu);
                popupMenu.show();
            }

        }
    }

    private class MenuClick implements PopupMenu.OnMenuItemClickListener {
        int pos;

        public MenuClick(int pos) {
            this.pos = pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.user_edit:
                    Toast.makeText(context, "Edit Clicked" + pos, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.user_delete:
                    Toast.makeText(context, "Delete Clicked" + pos, Toast.LENGTH_SHORT).show();
                    return true;
                            }
            return false;
        }

    }
}