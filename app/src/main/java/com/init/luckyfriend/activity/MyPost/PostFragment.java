package com.init.luckyfriend.activity.MyPost;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.init.luckyfriend.R;

import java.util.ArrayList;

public class PostFragment extends Fragment {

    RecyclerView rvFeed;
    LinearLayoutManager linearLayoutManager;
    PostFeedAdapter feedAdapter;
    private ProgressDialog prog;
    private ArrayList<PostDataBean> items=new ArrayList<>();
    Toolbar topToolBar;
    TextView notification;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);


        rvFeed=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        //      notification=(TextView)rootView.findViewById(R.id.notification);

        linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new PostFeedAdapter(getActivity(),items);
        rvFeed.setAdapter(feedAdapter);


        // Inflate the layout for this fragment
        return rootView;

    }
}