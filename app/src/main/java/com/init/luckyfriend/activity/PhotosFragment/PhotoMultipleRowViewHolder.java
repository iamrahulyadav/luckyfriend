package com.init.luckyfriend.activity.PhotosFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.init.luckyfriend.R;

import com.init.luckyfriend.activity.ChatFragment.App;
import com.init.luckyfriend.activity.Notification.AppConstant;

/**
 * Created by user on 2/23/2016.
 */
public class PhotoMultipleRowViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    ImageButton add;
    TextView addphoto;

    public PhotoMultipleRowViewHolder(View itemView, int type) {
        super(itemView);

        if (type == AppConstant.addphoto) {
          image=(ImageView)itemView.findViewById(R.id.image);
        }
        else if (type == AppConstant.addmore) {
            addphoto=(TextView)itemView.findViewById(R.id.add);
            add=(ImageButton)itemView.findViewById(R.id.image);
            image=(ImageView)itemView.findViewById(R.id.photoaadded);
        }
        else if(type==5)
        {
            image=(ImageView)itemView.findViewById(R.id.image);
        }
    }
}