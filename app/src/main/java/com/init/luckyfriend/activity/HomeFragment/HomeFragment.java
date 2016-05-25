package com.init.luckyfriend.activity.HomeFragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {

    RecyclerView rvFeed;
    LinearLayoutManager linearLayoutManager;
    WallFeedAdapter feedAdapter;
    private ProgressDialog prog;
    private ArrayList<WallDataBean> items = new ArrayList<>();
    Toolbar topToolBar;
    TextView notification;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private boolean loading = true;
    int visibleItemCount;
    int pastVisiblesItems, totalItemCount;
    int skipdata=0;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        rvFeed = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        // getActivity().getSupportActionBar().setTitle("Homeeee");

        linearLayoutManager = new LinearLayoutManager(getActivity());

        rvFeed.setLayoutManager(linearLayoutManager);


        prog = new ProgressDialog(getActivity());
        prog.setMessage("Wait loading data ....");

        //Toast.makeText(getContext(),"called",Toast.LENGTH_LONG).show();
        getData();


        feedAdapter = new WallFeedAdapter(getActivity(), items);
        rvFeed.setAdapter(feedAdapter);

        rvFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            getData();
//                            Toast.makeText(getActivity(), "called", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getData() {
        prog.show();
        // String url ="http://192.168.0.7/test.php";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response.toString());
                prog.dismiss();
//                items.clear();
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        WallDataBean pdb = new WallDataBean();
                        pdb.setLast_name(jo.getString("last_name"));
                        pdb.setUser_name(jo.getString("user_name"));
                        pdb.setPost_img(jo.getString("post_img"));
                        pdb.setPost_comments(jo.getInt("post_comments"));
                        pdb.setPost_likes(jo.getInt("post_likes"));
                        pdb.setPerson_country(jo.getString("person_country"));
                        pdb.setPerson_profile_img(jo.getString("person_profile_img"));
                        pdb.setPost_id(jo.getString("post_id"));
                        pdb.setPerson_id(jo.getString("person_id"));
                        pdb.setPeron_dob(jo.getString("person_dob"));
                        pdb.setIsLiked(jo.getInt("isliked"));
                        pdb.setIsfriend(jo.getInt("isfriend"));

                        int year = 0, mon = 0, day = 0;
                        String[] data = pdb.getPeron_dob().split("-");
                        year = Integer.parseInt(data[0]);
                        mon = Integer.parseInt(data[1]);
                        day = Integer.parseInt(data[2]);
                        pdb.setPeron_dob(getAge(year, mon, day) +" "+"Years" + "");


                        items.add(pdb);

                    }


                    skipdata=items.size();
                    if(jarray.length()<5)
                        loading=false;
                    else
                    loading=true;
                    feedAdapter.notifyDataSetChanged();

                } catch (Exception ex) {
                    Log.e("error", ex.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
                loading=true;
//            Log.e("error",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 1 + "");
                params.put("person_id",Singleton.pref.getString("person_id", ""));
                params.put("skipdata",skipdata+"");
                params.put("ugender",Singleton.pref.getString("ugender", ""));
                params.put("ucountry",Singleton.pref.getString("ucountry", ""));

                Log.e("person_details", Singleton.pref.getString("ugender", "")+Singleton.pref.getString("ucountry", "")+Singleton.pref.getString("ucity", "")+Singleton.pref.getString("uname", ""));
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

    public int getAge(int DOByear, int DOBmonth, int DOBday) {

        int age;

        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if (DOBmonth > currentMonth) {
            --age;
        } else if (DOBmonth == currentMonth) {
            if (DOBday > todayDay) {
                --age;
            }
        }
        return age;
    }


}
