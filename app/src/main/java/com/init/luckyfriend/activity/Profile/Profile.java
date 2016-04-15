package com.init.luckyfriend.activity.Profile;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.ExtendedProfile.SlidingImage_Adapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {

    private RecyclerView mRecyclerView;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    View header;
    private static final Integer[] IMAGES= {R.drawable.girlicon,R.drawable.female_icon,R.drawable.girlicon,R.drawable.iiiii};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        createAdapter(mRecyclerView);

        init();
        // Inflate the layout for this fragment
        return rootView;

    }

    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager)header.findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(getActivity(),ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                header.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);



        NUM_PAGES =IMAGES.length;



        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        /*Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
*/
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }



    private void createAdapter(RecyclerView recyclerView) {

        final List<String> content = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            content.add("item " + i);
        }

        final ParallaxRecyclerAdapter<String> adapter = new ParallaxRecyclerAdapter<String>(content) {

            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<String> adapter, int i) {
                //( (Holder) viewHolder).textView.setText(adapter.getData().get(i));

            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<String> adapter, int i) {
                return new Holder(getActivity().getLayoutInflater().inflate(R.layout.row_recyclerview, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<String> adapter) {
                return 1;
            }
        };

        adapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {
                //Toast.makeText(getActivity(), "You clicked '" + position + "'", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        header = getActivity().getLayoutInflater().inflate(R.layout.header, recyclerView, false);
        adapter.setParallaxHeader(header, recyclerView);
        adapter.setData(content);
        recyclerView.setAdapter(adapter);

    }

    static class Holder extends RecyclerView.ViewHolder {
        public TextView textView;

        public Holder(View itemView) {
            super(itemView);
            //textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

}