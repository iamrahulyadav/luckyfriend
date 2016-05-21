package com.init.luckyfriend.activity.LikesFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

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
import com.init.luckyfriend.activity.DATA.FavouriteDataBean;
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MutualLikes extends android.support.v4.app.Fragment {
    RecyclerView rvFeed;
    LinearLayoutManager linearLayoutManager;
    YouLikeAdapter feedAdapter;
    ProgressDialog prog;
    private ArrayList<FavouriteDataBean> items=new ArrayList<>();
    private boolean loading = true;
    int visibleItemCount;
    int pastVisiblesItems, totalItemCount;
    int skipdata=0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prog=new ProgressDialog(getContext());
        prog.setMessage("Wait loading data....");

        getLike();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        rvFeed=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        // getActivity().getSupportActionBar().setTitle("Homeeee");

        linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new YouLikeAdapter(getActivity(),items);
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
                            getLike();
//                            Toast.makeText(getActivity(), "called", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        // Inflate the layout for this fragment
        return rootView;

    }

    private void getLike() {
prog.show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prog.dismiss();
                Log.e("mutual like", response.toString());
                items.clear();
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    if (jarray.length() == 0) {
                        // dataleft = false;
                        Toast.makeText(getContext(), "No mutual likes yet", Toast.LENGTH_LONG).show();
                        return;
                    }
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        FavouriteDataBean fdb = new FavouriteDataBean();
                        fdb.setPost_img(jo.getString("post_img"));
                        fdb.setPost_likes(jo.getInt("post_likes"));
                        fdb.setPost_comments(jo.getInt("post_comments"));
                        fdb.setPost_user_first_name(jo.getString("user_name"));
                        fdb.setPost_user_last_name(jo.getString("last_name"));
                        fdb.setPost_user_profile_pic(jo.getString("person_profile_pic"));
                        fdb.setPost_user_country(jo.getString("person_country"));
                        fdb.setPost_user_dob(jo.getString("person_dob"));
                        fdb.setPost_id(jo.getString("post_id"));
                        fdb.setIsliked(jo.getInt("isliked"));
                        fdb.setPerson_id(jo.getString("person_id"));
                        fdb.setIsfriend(jo.getInt("isfriend"));


                        int year=0,mon=0,day=0;
                        String[] data=fdb.getPost_user_dob().split("-");
                        year=Integer.parseInt(data[0]);
                        mon=Integer.parseInt(data[1]);
                        day=Integer.parseInt(data[2]);
                        fdb.setPost_user_dob(getAge(year,mon,day)+"years"+"");
                        items.add(fdb);
                    }


                    feedAdapter.notifyDataSetChanged();

                } catch (Exception ex) {

                    Log.e("error", ex.getMessage());
                }

            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            prog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 21+"");
                params.put("person_id",Singleton.pref.getString("person_id",""));
                params.put("skipdata",skipdata+"");
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