package com.init.luckyfriend.activity.Visitors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class YouVisited extends Fragment {
    RecyclerView rvFeed;
    LinearLayoutManager linearLayoutManager;
    VisitedYouAdapter feedAdapter;
    private ProgressDialog prog;
    private ArrayList<VisitedYouDataBean> items=new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getYouVisited();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_you_visited, container, false);

        rvFeed=(RecyclerView)rootView.findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {

                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new VisitedYouAdapter(getActivity(),items);
        rvFeed.setAdapter(feedAdapter);





        // Inflate the layout for this fragment
        return rootView;

    }


    private void getYouVisited() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   prog.dismiss();
                Log.e("you visited", response.toString());
                items.clear();
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    if (jarray.length() == 0) {
                        // dataleft = false;
                       // Toast.makeText(getActivity(), "No favourites yet", Toast.LENGTH_LONG).show();
                        return;
                    }
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        VisitedYouDataBean fdb = new VisitedYouDataBean();
                        fdb.setPost_img(jo.getString("post_img"));
                        fdb.setPost_likes(jo.getString("post_likes"));
                        fdb.setPost_comments(jo.getString("post_comments"));
                        fdb.setPerson_dob(jo.getString("person_dob"));
                        fdb.setPerson_country(jo.getString("person_country"));
                        fdb.setUser_name(jo.getString("user_name"));
                        fdb.setLast_name(jo.getString("last_name"));
                        fdb.setPerson_profile_pic(jo.getString("person_profile_pic"));

                        int year=0,mon=0,day=0;
                        String[] data=fdb.getPerson_dob().split("-");
                        year=Integer.parseInt(data[0]);
                        mon=Integer.parseInt(data[1]);
                        day=Integer.parseInt(data[2]);
                        fdb.setPerson_dob(getAge(year,mon,day)+"Years"+"");
                        items.add(fdb);
                    }


                    // rv.setAdapter(adapter);
                    // skipdata = shopdata.size();
                    feedAdapter.notifyDataSetChanged();

                } catch (Exception ex) {

                    Log.e("error", ex.getMessage());
                }

            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 20+"");
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
    public int getAge(int DOByear, int DOBmonth, int DOBday) {

        int age;

        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if(DOBmonth > currentMonth){
            --age;
        }
        else if(DOBmonth == currentMonth){
            if(DOBday > todayDay){
                --age;
            }
        }
        return age;
    }


}
