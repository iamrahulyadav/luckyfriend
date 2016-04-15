package com.init.luckyfriend.activity.AppTour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.init.luckyfriend.R;

public class Apptour5 extends Fragment {

    RelativeLayout circle1, circle2, circle3, circle4;
    ImageView image1,image2,image3,location;
    FrameLayout mainframe2,mainframe3,mainframe4,mainframe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.apptour5, container, false);

        circle1 = (RelativeLayout) rootView.findViewById(R.id.circle1);
        mainframe= (FrameLayout) rootView.findViewById(R.id.mainframe);
        mainframe2= (FrameLayout) rootView.findViewById(R.id.mainframe2);
        mainframe3= (FrameLayout) rootView.findViewById(R.id.mainframe3);
        mainframe4= (FrameLayout) rootView.findViewById(R.id.mainframe4);
        image1 =(ImageView) rootView.findViewById(R.id.image1);
        image2 =(ImageView) rootView.findViewById(R.id.image2);
        circle2 = (RelativeLayout) rootView.findViewById(R.id.circle2);
        circle3 = (RelativeLayout) rootView.findViewById(R.id.circle3);
        image3 =(ImageView) rootView.findViewById(R.id.image3);
        circle4 = (RelativeLayout) rootView.findViewById(R.id.circle4);
        location=(ImageView)rootView.findViewById(R.id.location);
        /*
        image4 =(ImageView)rootView.findViewById(R.id.image4);
        image5 =(ImageView)rootView.findViewById(R.id.image5);
        location=(ImageView)rootView.findViewById(R.id.location);*/




 DisplayMetrics       displayMetrics1 = getResources().getDisplayMetrics();

        int width1 = displayMetrics1.widthPixels;
        int height1 =displayMetrics1.heightPixels;
        Log.e("width1", width1 + " " + height1 + "");
//Toast.makeText(getActivity(), "width" + width, Toast.LENGTH_SHORT).show();
        ViewGroup.MarginLayoutParams marginParamsapp = (ViewGroup.MarginLayoutParams) location.getLayoutParams();
        marginParamsapp.width=(displayMetrics1.widthPixels*60)/100;
        marginParamsapp.height=(displayMetrics1.widthPixels*60)/100;
        if(height1>=1200)
        {
            location.setPadding(0,60,0,0);
        }


        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int width = (displayMetrics.widthPixels*80)/100;

        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        circle1.setLayoutParams(parms);
         parms = new FrameLayout.LayoutParams(width+50, width+50);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        mainframe.setLayoutParams(parms);

      //circle 2

        width = (displayMetrics.widthPixels*60)/100;

        parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        circle2.setLayoutParams(parms);


        //parent frame layout
        FrameLayout.LayoutParams parms2 = new FrameLayout.LayoutParams(width+50, width+50);
        parms2.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        mainframe2.setLayoutParams(parms2);
        mainframe4.setLayoutParams(parms2);

        //circle 3

        width = (displayMetrics.widthPixels*45)/100;

        parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        circle3.setLayoutParams(parms);


        //parent frame layout
        FrameLayout.LayoutParams parms3 = new FrameLayout.LayoutParams(width+50, width+50);
        parms3.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        mainframe3.setLayoutParams(parms3);



        //cirlce 4

        width = (displayMetrics.widthPixels*30)/100;

        parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        circle4.setLayoutParams(parms);





        // IMAGE1
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) image1.getLayoutParams();
        marginParams.setMargins((width*53)/100,0,0,(width*25)/100);

        // IMAGE2
        ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) image2.getLayoutParams();
        marginParams1.setMargins((width*45)/100,0,0,(width*2)/100);

        // IMAGE3
        ViewGroup.MarginLayoutParams marginParams2 = (ViewGroup.MarginLayoutParams) image3.getLayoutParams();
        marginParams2.setMargins(-(width*10)/100,0,0,0);
        return rootView;

    }
}