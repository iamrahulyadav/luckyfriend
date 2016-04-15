package com.init.luckyfriend.activity.PhotosFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.init.luckyfriend.R;

import java.util.List;


import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Notification.AppConstant;

/**
 * Created by user on 2/23/2016.
 */
public class PhotoMultipleRowAdapter extends RecyclerView.Adapter<PhotoMultipleRowViewHolder> {

    private LayoutInflater inflater = null;
    private List<WallDataBean> multipleRowModelList;

    public PhotoMultipleRowAdapter(Context context, List<WallDataBean> multipleRowModelList){
        this.multipleRowModelList = multipleRowModelList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PhotoMultipleRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if (viewType == AppConstant.addphoto)
            view = inflater.inflate(R.layout.item, parent, false);
        else if (viewType == AppConstant.addmore)
            view = inflater.inflate(R.layout.layout_add_more_photos, parent, false);

        return new PhotoMultipleRowViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(PhotoMultipleRowViewHolder holder, int position) {
        // holder.name.setText(multipleRowModelList.get(position).modelContent);
    }

    @Override
    public int getItemCount() {
        //return (multipleRowModelList != null && multipleRowModelList.size() > 0 ) ? multipleRowModelList.size() : 0;
        return 9;
    }

    @Override
    public int getItemViewType(int position) {

        WallDataBean multipleRowModel = multipleRowModelList.get(position);

        if (multipleRowModel != null)
            return multipleRowModel.type;

        return super.getItemViewType(position);
    }
}
