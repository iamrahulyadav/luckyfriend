package com.init.luckyfriend.activity.FriendSearch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FriendsOnlySearch extends Fragment {
    RecyclerView rvFeed;
    LinearLayoutManager linearLayoutManager;
    FriendsOnlyAdapter feedAdapter;
    private ProgressDialog prog;
    private ArrayList<WallDataBean> items=new ArrayList<>();
    Toolbar topToolBar;
    private boolean loading = true;
    int visibleItemCount;
    int pastVisiblesItems, totalItemCount;
    int skipdata=0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prog=new ProgressDialog(getActivity());
        prog.setMessage("Wait loading data ....");
        // getData();
        getFriends();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends_only_search, container, false);

        rvFeed=(RecyclerView)rootView.findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new FriendsOnlyAdapter(getActivity(),items);
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
                            getFriends();
//                            Toast.makeText(getActivity(), "called", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });



        // Inflate the layout for this fragment
        return rootView;

    }

    private void getFriends() {

        prog.show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST,getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("friends only", response.toString());
                prog.dismiss();
                items.clear();
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    if (jarray.length() == 0) {
                        // dataleft = false;
                        Toast.makeText(getContext(),"No friends yet..",Toast.LENGTH_LONG).show();
                        return;
                    }
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        WallDataBean pdb = new WallDataBean();
                        pdb.setLast_name(jo.getString("last_name"));
                        pdb.setUser_name(jo.getString("user_name"));
                        pdb.setPost_img(jo.getString("post_img"));
                        pdb.setPost_comments(jo.getInt("post_comments"));
                        pdb.setPost_likes(jo.getInt("post_likes"));
                        pdb.setPerson_country(jo.getString("person_country"));
                        pdb.setPerson_profile_img(jo.getString("person_profile_pic"));
                        pdb.setPeron_dob(jo.getString("person_dob"));
                        pdb.setPost_id(jo.getString("post_id"));
                        pdb.setIsLiked(jo.getInt("isliked"));
                        pdb.setPerson_id(jo.getString("person_id"));

                        int year=0,mon=0,day=0;
                        String[] data=pdb.getPeron_dob().split("-");
                        year=Integer.parseInt(data[0]);
                        mon=Integer.parseInt(data[1]);
                        day=Integer.parseInt(data[2]);
                        pdb.setPeron_dob(getAge(year,mon,day)+""+"Years");



                        items.add(pdb);
                    }

                    skipdata=items.size();
                    if(jarray.length()<5)
                        loading=false;
                    else
                        loading=true;


                    feedAdapter.notifyDataSetChanged();

                }

                catch (Exception ex) {
                    Log.e("error", ex.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
//                   Log.e("error",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 17+"");
                params.put("person_id", Singleton.pref.getString("person_id", ""));
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
