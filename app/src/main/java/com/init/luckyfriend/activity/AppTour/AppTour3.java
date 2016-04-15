package com.init.luckyfriend.activity.AppTour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.init.luckyfriend.R;

public class AppTour3 extends Fragment {

    RelativeLayout circle1, circle2, circle3, circle4;
    View smallcircle2,smallcircle3;
    View leftcircle, rightcircle;
    ImageView image2, image3;
    FrameLayout mainframe, mainframe2,mainframe3,mainframe4;
    ImageView location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.apptour3, container, false);

        circle1 = (RelativeLayout) rootView.findViewById(R.id.circle1);
        mainframe = (FrameLayout) rootView.findViewById(R.id.mainframe);
        mainframe2 = (FrameLayout) rootView.findViewById(R.id.mainframe2);
        mainframe3 = (FrameLayout) rootView.findViewById(R.id.mainframe3);
        mainframe4 = (FrameLayout) rootView.findViewById(R.id.mainframe4);

        image2 = (ImageView) rootView.findViewById(R.id.image2);
        image3 = (ImageView) rootView.findViewById(R.id.image3);
        circle2 = (RelativeLayout) rootView.findViewById(R.id.circle2);
        circle3 = (RelativeLayout) rootView.findViewById(R.id.circle3);
        circle4 = (RelativeLayout) rootView.findViewById(R.id.circle4);

        smallcircle2=rootView.findViewById(R.id.smallcircle2);
        smallcircle3=rootView.findViewById(R.id.smallcircle3);

        location=(ImageView)rootView.findViewById(R.id.location);
        location.setPadding(0,0,0,30);

//circle1
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int width = (displayMetrics.widthPixels * 80) / 100;

        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        circle1.setLayoutParams(parms);

        // cirlce2 parent width and height
        parms = new FrameLayout.LayoutParams(width + 30, width + 30);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        mainframe.setLayoutParams(parms);

//image 2 top
        int leftmargin = (displayMetrics.widthPixels * 4) / 100;
        int topmargin = (displayMetrics.widthPixels * 5) / 100;

        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) image2.getLayoutParams();
        marginParams.setMargins(leftmargin, topmargin, 0, 0);
        marginParams.width = (displayMetrics.widthPixels * 17) / 100;
        marginParams.height = (displayMetrics.widthPixels * 17) / 100;



        //cirlce 2

        width = (displayMetrics.widthPixels*65)/100;

        parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        circle2.setLayoutParams(parms);

        //smallcircle2
        ViewGroup.MarginLayoutParams marginParam2 = (ViewGroup.MarginLayoutParams)smallcircle2.getLayoutParams();
        marginParam2.setMargins(0, 0, 0, (width*10)/100);


//mainframe 2
        width = (displayMetrics.widthPixels*72)/100;
        parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        mainframe2.setLayoutParams(parms);


        //cirlce 3

        width = (displayMetrics.widthPixels*45)/100;
        parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        circle3.setLayoutParams(parms);

        //smallcircle2
        ViewGroup.MarginLayoutParams marginParam3 = (ViewGroup.MarginLayoutParams)smallcircle3.getLayoutParams();
        marginParam3.setMargins(0, 0, 0, (width * 5) / 100);



        //mainframe 3
        width = (displayMetrics.widthPixels*60)/100;
        parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        mainframe3.setLayoutParams(parms);

        //cirlce 4

        width = (displayMetrics.widthPixels*55)/100;
        parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        circle4.setLayoutParams(parms);


        //mainframe 4
        width = (displayMetrics.widthPixels*66)/100;
        parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        mainframe4.setLayoutParams(parms);


        marginParams = (ViewGroup.MarginLayoutParams) image3.getLayoutParams();
        marginParams.setMargins(0,0,(width*10)/100,(width*8)/100);
        marginParams.width = (displayMetrics.widthPixels * 25) / 100;
        marginParams.height = (displayMetrics.widthPixels * 25) / 100;

        // Inflate the layout for this fragment
        return rootView;

    }
}