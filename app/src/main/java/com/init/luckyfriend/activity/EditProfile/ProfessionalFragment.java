package com.init.luckyfriend.activity.EditProfile;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.init.luckyfriend.R;


public class ProfessionalFragment extends Fragment {
    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";
    static EditText edudetails;
    static EditText occupation;

    public static ProfessionalFragment createInstance(int itemsCount) {
        ProfessionalFragment partThreeFragment = new ProfessionalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    public ProfessionalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_professional, container, false);
        edudetails=(EditText)rootView.findViewById(R.id.edudetails);
        occupation=(EditText)rootView.findViewById(R.id.occupation);

        return rootView;



    }

}
