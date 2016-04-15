package com.init.luckyfriend.activity.PhotosFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;


import com.init.luckyfriend.R;

import com.init.luckyfriend.activity.Notification.AppConstant;

/**
 * Created by user on 2/23/2016.
 */
public class PhotoMultipleRowViewHolder extends RecyclerView.ViewHolder {

    ImageView image;

    public PhotoMultipleRowViewHolder(View itemView, int type) {
        super(itemView);

        if (type == AppConstant.addphoto) {
image=(ImageView)itemView.findViewById(R.id.image);
        }
        else if (type == AppConstant.addmore) {
            image=(ImageView)itemView.findViewById(R.id.image);

        }

    }
}