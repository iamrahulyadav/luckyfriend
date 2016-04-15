package com.init.luckyfriend.activity.PhotosFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.init.luckyfriend.R;

import java.util.ArrayList;


import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Notification.AppConstant;

public class PhotosFragment extends Fragment {

    RecyclerView recyclerView;
    PhotoMultipleRowAdapter adapter;
    private ArrayList<WallDataBean> items=new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);


        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        fillAdapter();


        adapter=new PhotoMultipleRowAdapter(getActivity(),items);
        recyclerView.setAdapter(adapter);




        // Inflate the layout for this fragment
        return rootView;
    }
    private void fillAdapter() {

        int type;

        String content;

        for (int i = 0; i < 9; i++) {

            if (i == 0 || i == 1 || i == 2 ||  i==3 || i==4 || i==5 || i==6 || i==7 ) {
                type = AppConstant.addphoto;
                //content = "Type 1: This is Multiple row layout";
            }
            else
            {
                type = AppConstant.addmore;
                //content = "Type 1: This is Multiple row layout";
            }


            items.add(new WallDataBean( type ));
        }


    }


}
