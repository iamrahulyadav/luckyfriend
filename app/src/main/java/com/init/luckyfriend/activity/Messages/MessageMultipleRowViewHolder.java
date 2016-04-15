package com.init.luckyfriend.activity.Messages;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.init.luckyfriend.R;

import com.init.luckyfriend.activity.Notification.AppConstant;

/**
 * Created by user on 2/25/2016.
 */
public class MessageMultipleRowViewHolder  extends RecyclerView.ViewHolder {

    ImageView image;
    TextView username,subtitle;

    public MessageMultipleRowViewHolder(View itemView, int type) {
        super(itemView);

        if (type == AppConstant.messageonline) {
            image = (ImageView) itemView.findViewById(R.id.notipropic);
            username =(TextView) itemView.findViewById(R.id.notiuname);
            subtitle=(TextView)itemView.findViewById(R.id.follow);

        } else if (type == AppConstant.messageoffline) {
            image = (ImageView) itemView.findViewById(R.id.notipropic);
            username =(TextView) itemView.findViewById(R.id.notiuname);
            subtitle=(TextView)itemView.findViewById(R.id.follow);

        }

    }
}