package com.init.luckyfriend.activity.Visitors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.init.luckyfriend.R;

import java.util.List;


import com.init.luckyfriend.activity.DATA.WallDataBean;

/**
 * Created by user on 2/22/2016.
 */
public class VisitedYouAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<WallDataBean> data;
    public VisitedYouAdapter(Context context, List<WallDataBean> list) {
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

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFeedCenter;

        //ImageButton btnComments;
        //ImageButton btnLike;
        //ImageButton btnMore;
        //TextSwitcher tsLikesCounter;
        //ImageView ivUserProfile;
        //FrameLayout vImageRoot;
        TextView caption;

        public CellFeedViewHolder(View view) {
            super(view);

            ivFeedCenter = (ImageView) view.findViewById(R.id.ivFeedCenter);

            // btnComments = (ImageButton) view.findViewById(R.id.btnComments);
            // btnLike = (ImageButton) view.findViewById(R.id.btnLike);
            // btnMore = (ImageButton) view.findViewById(R.id.btnMore);
            // tsLikesCounter = (TextSwitcher) view.findViewById(R.id.tsLikesCounter);
            // ivUserProfile = (ImageView) view.findViewById(R.id.ivUserProfile);

            caption = (TextView) view.findViewById(R.id.caption);

        }
    }


}
