package com.init.luckyfriend.activity.EditProfile;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.DATA.GetProfiledata;
import com.init.luckyfriend.activity.DatePickerFragment;
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BasicInfoFragment extends Fragment {
    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";

    static EditText firstname;
    static EditText lastname;
    static EditText dateofbirth;
    @Bind(R.id.country) Spinner spincountry;
    static EditText cityname;
    @Bind(R.id.maritalstatus) Spinner spinrel;
    @Bind(R.id.sexuality) Spinner spinsex;
    @Bind(R.id.lookingfor) Spinner spinlookingfor;
    static  TextView aboutme;
    static  EditText interests;
    @Bind(R.id.bodytype)  Spinner spinbodytype;
    static  EditText height;
    static  EditText weight;
    static  EditText eyes;
    static  EditText hair;
    static  EditText ethnicity;
    @Bind(R.id.starsign) Spinner starsign;

    static RadioButton male;
    static RadioButton female;
    static RadioGroup rg1;

    static String country;
    static  String relationstatus;
    static String sexstatus;
    static String lookingfor;
    static String  star_sign;
    static String bodytype;
    static String language;
    static  String genderstatus;
    static ArrayList<String> arrays=new ArrayList<String>();
    static String[] array = {"English", "Hindi", "Punjabi", "Chinese", "Spanish", "Bengali", "Arabic", "Russian", "Japanese", "German"};
    static MultiSelectionSpinner  multiSelectionSpinner;
    public static BasicInfoFragment createInstance(int itemsCount) {
        BasicInfoFragment partThreeFragment = new BasicInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = (View) inflater.inflate(
                R.layout.activity_edit_user_profile1, container, false);
        ButterKnife.bind(this, rootView);

        firstname=(EditText)rootView.findViewById(R.id.name);
        lastname=(EditText)rootView.findViewById(R.id.lastname);
        rg1=(RadioGroup)rootView.findViewById(R.id.radiogroup);
        dateofbirth=(EditText)rootView.findViewById(R.id.dob);
        cityname=(EditText)rootView.findViewById(R.id.cityname);
        aboutme=(TextView)rootView.findViewById(R.id.aboutme);
        interests=(EditText)rootView.findViewById(R.id.interest);
        height=(EditText)rootView.findViewById(R.id.height);
        weight=(EditText)rootView.findViewById(R.id.weight);
        eyes=(EditText)rootView.findViewById(R.id.eyes);
        hair=(EditText)rootView.findViewById(R.id.hair);
        ethnicity=(EditText)rootView.findViewById(R.id.ethnicity);
        male=(RadioButton)rootView.findViewById(R.id.maleselected);
        female=(RadioButton)rootView.findViewById(R.id.femaleselected);

        arrays.add("English");
        arrays.add("Hindi");
        arrays.add("Punjabi");
        arrays.add("Chinese");
        arrays.add("Japanese");
        arrays.add("Spanish");
        arrays.add("Bengali");
        arrays.add("Arabic");
        arrays.add("Russain");
        arrays.add("German");


        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    // Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                    genderstatus = rb.getText().toString();
                }

            }
        });

        retrieveInfo();

        spincountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = spincountry.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinrel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                relationstatus=spinrel.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinsex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sexstatus=spinsex.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinlookingfor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lookingfor=spinlookingfor.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        starsign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                star_sign=starsign.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinbodytype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bodytype=spinbodytype.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
          multiSelectionSpinner = (MultiSelectionSpinner) rootView.findViewById(R.id.language);
        multiSelectionSpinner.setItems(array);
        multiSelectionSpinner.setSelection(new int[]{2, 6});

        return rootView;
    }

    private void retrieveInfo() {
       // String url ="http://192.168.0.7/test.php";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST,getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("edt response", response.toString());
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    String last_name=jobj.getString("lastname");
                    String country=jobj.getString("country");
                    String city=jobj.getString("location");
                    String dob=jobj.getString("dob");
                    String about=jobj.getString("aboutme");
                    String name=jobj.getString("name");
                    String marital_status=jobj.getString("maritalstatus");
                    String gender=jobj.getString("gender");

                    firstname.setText(name);
                    lastname.setText(last_name);
                    dateofbirth.setText(dob);
                    spincountry.setSelection(((ArrayAdapter<String>) spincountry.getAdapter()).getPosition(country));
                    spinrel.setSelection(((ArrayAdapter<String>)spinrel.getAdapter()).getPosition(marital_status));

                    if(gender.compareToIgnoreCase("male")==0){
                        male.setChecked(true);
                    }
                    else
                        female.setChecked(true);

                    }
                catch (Exception ex) {
                    Log.e("error", ex.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //prog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 10+"");
                params.put("person_id", Singleton.pref.getString("person_id",""));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);

    }


}



