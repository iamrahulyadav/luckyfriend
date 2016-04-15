package com.init.luckyfriend.activity.MyPost;

import android.content.Context;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.init.luckyfriend.R;

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

//        WallDataBean gd=data.get(position);
       /* Glide.with(context)

                .load(gd.getImageurl())
                .into(holder.ivFeedCenter)
         ;*/
        // Animation anim = AnimationUtils.loadAnimation(context,R.anim.fadein);

     /*   Glide.with(context)
                .load(R.drawable.female_icon)
                        // The placeholder image is shown immediately and
                        // replaced by the remote image when Picasso has
                        // finished fetching it.
                .placeholder(R.mipmap.ic_launcher)
                        //A request will be retried three times before the error placeholder is shown.
                .error(R.mipmap.ic_launcher)
                        // Transform images to better fit into layouts and to
                        // reduce memory size.
         //       .animate(anim)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.ivFeedCenter);
*/


        // holder.caption.setText(gd.getCaption());
        // holder.btnComments.setTag(position);
        // holder.btnMore.setTag(position);
//        holder.ivFeedCenter.setTag(holder);
        // holder.btnLike.setTag(holder);

    }


    @Override
    public int getItemCount() {
        /*if(data==null)
            return 0;
        else
        return data.size();
        */
        return 10;
    }

    public class CellFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView profilepic, postimage, options;

        TextView name, date, comment, like;

        public CellFeedViewHolder(View view) {
            super(view);

            profilepic = (ImageView) view.findViewById(R.id.profilepic);
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