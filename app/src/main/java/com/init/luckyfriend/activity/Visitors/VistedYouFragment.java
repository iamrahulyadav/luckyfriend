package com.init.luckyfriend.activity.Visitors;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.init.luckyfriend.R;

import java.util.ArrayList;


import com.init.luckyfriend.activity.DATA.WallDataBean;

public class VistedYouFragment extends Fragment {

    RecyclerView rvFeed;
    LinearLayoutManager linearLayoutManager;
    VisitedYouAdapter feedAdapter;
    private ProgressDialog prog;
    private ArrayList<WallDataBean> items=new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_visted_you, container, false);


        rvFeed=(RecyclerView)rootView.findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new VisitedYouAdapter(getActivity(),items);
        rvFeed.setAdapter(feedAdapter);




        // Inflate the layout for this fragment
        return rootView;

    }


}