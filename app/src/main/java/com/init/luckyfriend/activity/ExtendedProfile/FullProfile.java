package com.init.luckyfriend.activity.ExtendedProfile;

import android.app.Fragment;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.MyPost.PostDataBean;
import com.init.luckyfriend.activity.Singleton;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FullProfile extends Fragment {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private static final Integer[] IMAGES= {R.drawable.girlicon,R.drawable.female_icon,R.drawable.girlicon,R.drawable.iiiii};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_full_profile, container, false);
        init();

        // Inflate the layout for this fragment

        return rootView;
    }


    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager)rootView.findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(getActivity(),ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
         rootView.findViewById(R.id.indicator);

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


}


