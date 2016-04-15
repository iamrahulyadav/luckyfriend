package com.init.luckyfriend.activity.AppTour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.init.luckyfriend.R;

public class AppTour4 extends Fragment {

    RelativeLayout circle1, smallcircle;
    LinearLayout rectangle1;
   // ImageView bell, greycomment;
    RelativeLayout rectangle2;
    FrameLayout commentcontainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.apptour4, container, false);

        circle1 = (RelativeLayout) rootView.findViewById(R.id.circle1);
        rectangle1 = (LinearLayout) rootView.findViewById(R.id.rectangle1);
        rectangle2 = (RelativeLayout) rootView.findViewById(R.id.rectangle2);
        smallcircle = (RelativeLayout) rootView.findViewById(R.id.smallcircle);
        commentcontainer = (FrameLayout) rootView.findViewById(R.id.commentcontainer);

      /*  bell = (ImageView) rootView.findViewById(R.id.bell);
        greycomment = (ImageView) rootView.findViewById(R.id.greycomment);
*/

        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

        int width = displayMetrics.widthPixels - 100;

        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        circle1.setLayoutParams(parms);

// rectangle 1
       width=(displayMetrics.widthPixels *50) / 100;
      int  height = (displayMetrics.widthPixels *85) / 100;
        FrameLayout.LayoutParams param1 = new FrameLayout.LayoutParams(width,height);
        param1.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        param1.setMargins(0,0,0,-((width*28)/100));
        rectangle1.setLayoutParams(param1);




        // rectangle 2
        width = (displayMetrics.widthPixels * 40) / 100;
        height = (displayMetrics.widthPixels * 50) / 100;

        LinearLayout.LayoutParams param12 = new LinearLayout.LayoutParams(width,height);
        param12.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        param12.setMargins(0, 10, 0, 0);
        rectangle2.setLayoutParams(param12);
/*
        width = (displayMetrics.widthPixels * 50) / 100;

        bell.getLayoutParams().height = width + 20;
        bell.getLayoutParams().width = width;
*/

        // Inflate the layout for this fragment

     //small circle comment


         width = (displayMetrics.widthPixels*25)/100;

        FrameLayout.LayoutParams parms3 = new FrameLayout.LayoutParams(width, width);
        parms3.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
        smallcircle.setLayoutParams(parms3);

//comment container
        width=(displayMetrics.widthPixels *50) / 100+(displayMetrics.widthPixels*25)/100;
        height = (displayMetrics.widthPixels *68) / 100;
        FrameLayout.LayoutParams param4 = new FrameLayout.LayoutParams(width,height);
        param4.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;

        commentcontainer.setLayoutParams(param4);

        return rootView;

    }
}