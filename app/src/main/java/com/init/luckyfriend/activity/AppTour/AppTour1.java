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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.init.luckyfriend.R;

public class AppTour1 extends Fragment {

    RelativeLayout circle1, circle2, circle3, circle4, malecircle, girlcircle;
    ImageView app_logo;
    FrameLayout girlboycontainer;
    DisplayMetrics displayMetrics1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.apptour1, container, false);

        circle1 = (RelativeLayout) rootView.findViewById(R.id.circle1);
        circle2 = (RelativeLayout) rootView.findViewById(R.id.circle2);
        circle3 = (RelativeLayout) rootView.findViewById(R.id.circle3);
        circle4 = (RelativeLayout) rootView.findViewById(R.id.circle4);
        girlcircle = (RelativeLayout) rootView.findViewById(R.id.girl_circle);
        malecircle = (RelativeLayout) rootView.findViewById(R.id.male_circle);
        girlboycontainer = (FrameLayout) rootView.findViewById(R.id.girlboycontainer);
        app_logo=(ImageView)rootView.findViewById(R.id.app_logo);
        //device with and height
        displayMetrics1 = getResources().getDisplayMetrics();

        int width1 = displayMetrics1.widthPixels;
        int height1 =displayMetrics1.heightPixels;
        Log.e("width1",width1+ " "+height1+"");
//Toast.makeText(getActivity(), "width" + width, Toast.LENGTH_SHORT).show();
        ViewGroup.MarginLayoutParams marginParamsapp = (ViewGroup.MarginLayoutParams) app_logo.getLayoutParams();
        marginParamsapp.width=(displayMetrics1.widthPixels*60)/100;
        marginParamsapp.height=(displayMetrics1.widthPixels*60)/100;



        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels - 140;

            FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width, width);
            parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
            circle1.setLayoutParams(parms);

//cirlce 2

            width = displayMetrics.widthPixels - 180;

            parms = new FrameLayout.LayoutParams(width, width);
            parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
            circle2.setLayoutParams(parms);

//cirlce 2

            width = displayMetrics.widthPixels - 230;

            parms = new FrameLayout.LayoutParams(width, width);
            parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
            circle3.setLayoutParams(parms);


            //cirlce 2

            width = displayMetrics.widthPixels - 280;

            parms = new FrameLayout.LayoutParams(width, width);
            parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
            circle4.setLayoutParams(parms);


            //girlboycontainer
            width = displayMetrics.widthPixels - 140;

            parms = new FrameLayout.LayoutParams(width, width);
            parms.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
            girlboycontainer.setLayoutParams(parms);


            //girl
            width = (displayMetrics.widthPixels * 30) / 100;

            LinearLayout.LayoutParams girlparam = new LinearLayout.LayoutParams(width, width);
            girlcircle.setLayoutParams(girlparam);


            LinearLayout.LayoutParams maleparam = new LinearLayout.LayoutParams(width, width);


            malecircle.setLayoutParams(girlparam);


            Log.e("width and height", width + " " + width + "");


            // Inflate the layout for this fragment
            return rootView;

        }

}

