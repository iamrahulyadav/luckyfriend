package com.init.luckyfriend.activity.Messages;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.init.luckyfriend.R;

import java.util.List;


import com.init.luckyfriend.activity.ChatFragment.ChatActivity;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Notification.AppConstant;

/**
 * Created by user on 2/25/2016.
 */
public class MessageMultiplerowAdapter extends RecyclerView.Adapter<MessageMultipleRowViewHolder> implements View.OnClickListener {

    private LayoutInflater inflater = null;
    private List<WallDataBean> multipleRowModelList;
    Context context;

    public MessageMultiplerowAdapter(Context context, List<WallDataBean> multipleRowModelList){
        this.multipleRowModelList = multipleRowModelList;
        inflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public MessageMultipleRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if (viewType == AppConstant.messageonline)
        {
            view = inflater.inflate(R.layout.allmessages_online, parent, false);
            view.setOnClickListener(this);

        }

        else if (viewType == AppConstant.messageoffline)

        {
            view = inflater.inflate(R.layout.allmessages_offline, parent, false);
            view.setOnClickListener(this);

        }
        return new MessageMultipleRowViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(MessageMultipleRowViewHolder holder, int position) {
        // holder.name.setText(multipleRowModelList.get(position).modelContent);

    }

    @Override
    public int getItemCount() {
        //return (multipleRowModelList != null && multipleRowModelList.size() > 0 ) ? multipleRowModelList.size() : 0;
        return 6;
    }

    @Override
    public int getItemViewType(int position) {

        WallDataBean multipleRowModel = multipleRowModelList.get(position);

        if (multipleRowModel != null)
            return multipleRowModel.type;

        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View view) {

        //Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();

        Intent chat=new Intent(context, ChatActivity.class);
        context.startActivity(chat);
    }
}

