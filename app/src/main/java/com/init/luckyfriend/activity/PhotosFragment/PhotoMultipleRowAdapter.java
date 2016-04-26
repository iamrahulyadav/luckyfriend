package com.init.luckyfriend.activity.PhotosFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.init.luckyfriend.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;


import com.init.luckyfriend.activity.DATA.PhotosDataBean;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Notification.AppConstant;
import com.init.luckyfriend.activity.Notification.NotificationFragment;
import com.init.luckyfriend.activity.Singleton;

/**
 * Created by user on 2/23/2016.
 */
public class PhotoMultipleRowAdapter extends RecyclerView.Adapter<PhotoMultipleRowViewHolder> {

    private LayoutInflater inflater = null;
    private List<PhotosDataBean> multipleRowModelList;
    private static final int addphoto = AppConstant.addphoto;
    private static final int addmore = AppConstant.addmore;
    private static final int addphoto1 = 5;

    Activity context;
    PhotosFragment photos;
    PhotoMultipleRowAdapter adapter;
    Bitmap encoded;

    public PhotoMultipleRowAdapter(Activity context, List<PhotosDataBean> multipleRowModelList,PhotosFragment photos,Bitmap encoded) {
        this.multipleRowModelList = multipleRowModelList;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.photos=photos;
        this.encoded=encoded;
       }

    @Override
    public PhotoMultipleRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if (viewType == addphoto)
            view = inflater.inflate(R.layout.item, parent, false);
        else if (viewType == addmore)
            view = inflater.inflate(R.layout.layout_add_more_photos, parent, false);
        else if(viewType == 5 )
            view = inflater.inflate(R.layout.item, parent, false);

        return new PhotoMultipleRowViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final PhotoMultipleRowViewHolder holder, int position) {
        // holder.name.setText(multipleRowModelList.get(position).modelContent);
        if (PhotosFragment.items.get(position).getType() == 3)
            Singleton.imageLoader.displayImage(multipleRowModelList.get(position).getPerson_img_path(), holder.image, Singleton.defaultOptions);
        else if(PhotosFragment.items.get(position).getType() == 4){
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    photos.showDialog();
                }
            });

        }
        else {
//            if(encoded==null){
                holder.image.setImageResource(R.drawable.iiiii);
  //          }
    //        holder.image.setImageBitmap(encoded);
            // Singleton.imageLoader.displayImage(multipleRowModelList.get(position).getPerson_img_path(), holder.image, Singleton.defaultOptions);
            //holder.image.setImageResource(R.drawable.iiiii);
        }

    }



    @Override
    public int getItemCount() {
        //return (multipleRowModelList != null && multipleRowModelList.size() > 0 ) ? multipleRowModelList.size() : 0;
        return multipleRowModelList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (PhotosFragment.items.get(position).getType() == 3)
        {
            return addphoto;
        }
        else if(PhotosFragment.items.get(position).getType() == 4)
        {
            return addmore;
        }
       else if (PhotosFragment.items.get(position).getType() == 5)
        {
            return addphoto1;
        }
        else
            return -1;

    }



}