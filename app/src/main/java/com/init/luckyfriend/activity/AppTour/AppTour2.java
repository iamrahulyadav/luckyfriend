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

public class AppTour2 extends Fragment {

    RelativeLayout circle1,circle2;
    ImageView image1,image2,image3;
    FrameLayout searchcontainer,mainframe,handlecontainer;
    View searchhandle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.apptour2, container, false);

        circle1=(RelativeLayout)rootView.findViewById(R.id.circle1);
        circle2=(RelativeLayout)rootView.findViewById(R.id.circle2);
        searchcontainer=(FrameLayout)rootView.findViewById(R.id.searchcontainer);
        mainframe=(FrameLayout)rootView.findViewById(R.id.mainframe);
        handlecontainer=(FrameLayout)rootView.findViewById(R.id.handlecontainer);
        searchhandle=rootView.findViewById(R.id.searchh);
        image1=(ImageView)rootView.findViewById(R.id.image1);
        image2=(ImageView)rootView.findViewById(R.id.image2);
        image3= (ImageView) rootView.findViewById(R.id.image3);
        //search=(ImageView)rootView.findViewById(R.id.search);

        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

        int width = (displayMetrics.widthPixels*80)/100;
        //Log.e("width and height",displayMetrics.widthPixels+"  "+displayMetrics.heightPixels+"");

        // outer circle
        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width, width);
        parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        circle1.setLayoutParams(parms);
        handlecontainer.setLayoutParams(parms);
//main frame
        FrameLayout.LayoutParams parms2 = new FrameLayout.LayoutParams(width+50, width+50);
        parms2.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        mainframe.setLayoutParams(parms2);

         width = (displayMetrics.widthPixels*55)/100;
        FrameLayout.LayoutParams parms1 = new FrameLayout.LayoutParams(width, width);
        parms1.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;

        searchcontainer.setLayoutParams(parms1);





        // first image

        int leftmargin=(displayMetrics.widthPixels*6)/100;
        int topmargin=(displayMetrics.widthPixels*8)/100;

        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) image1.getLayoutParams();
        marginParams.setMargins(leftmargin,topmargin,0,0);
        marginParams.width=(displayMetrics.widthPixels*18)/100;
        marginParams.height=(displayMetrics.widthPixels*18)/100;
        //inner circle

        width=(displayMetrics.widthPixels*28)/100;
        FrameLayout.LayoutParams parms3 = new FrameLayout.LayoutParams(width, width);
        parms3.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        circle2.setLayoutParams(parms3);


        //center image width and height
        ViewGroup.MarginLayoutParams marginParams3 = (ViewGroup.MarginLayoutParams) image3.getLayoutParams();
        marginParams3.width=(displayMetrics.widthPixels*20)/100;
        marginParams3.height=(displayMetrics.widthPixels*20)/100;

        //top left image width and height
        ViewGroup.MarginLayoutParams marginParams4 = (ViewGroup.MarginLayoutParams) image1.getLayoutParams();
        marginParams4.width=(displayMetrics.widthPixels*15)/100;
        marginParams4.height=(displayMetrics.widthPixels*15)/100;

        //search handle width and height
         width = (displayMetrics.widthPixels*80)/100;
         marginParams3 = (ViewGroup.MarginLayoutParams) searchhandle.getLayoutParams();
        marginParams3.width=(displayMetrics.widthPixels*30)/100;
        marginParams3.height=(displayMetrics.widthPixels*22)/100;
        marginParams3.setMargins(0,0,0,(width*10)/100);

        // Inflate the layout for this fragment
        return rootView;

    }
}