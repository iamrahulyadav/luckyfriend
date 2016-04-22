package com.init.luckyfriend.activity.MyPost;

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
import android.widget.ImageView;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostFragment extends Fragment {

    RecyclerView rvFeed;
    LinearLayoutManager linearLayoutManager;
    PostFeedAdapter feedAdapter;
    private ProgressDialog prog;
    private ArrayList<PostDataBean> items=new ArrayList<>();
    Toolbar topToolBar;
    TextView notification;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPost();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);


        rvFeed=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        //      notification=(TextView)rootView.findViewById(R.id.notification);

        linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new PostFeedAdapter(getActivity(),items);
        rvFeed.setAdapter(feedAdapter);


        prog=new ProgressDialog(getContext());
        prog.setMessage("wait loading data");

        // Inflate the layout for this fragment
        return rootView;

    }

    private void getPost() {
        prog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("mypost", response.toString());
                prog.dismiss();
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    if (jarray.length() == 0) {
                        // dataleft = false;
                        return;
                    }
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        PostDataBean pdb = new PostDataBean();
                        pdb.setPost_id(jo.getString("post_id"));
                        pdb.setPost_img(jo.getString("post_img"));
                        pdb.setPost_date(jo.getString("post_date"));
                        pdb.setPost_likes(jo.getString("post_likes"));
                        pdb.setPost_comments(jo.getString("post_comments"));
                        pdb.setPerson_profile_pic(jo.getString("person_profile_pic"));
                        pdb.setUser_name(jo.getString("user_name"));
                        pdb.setLast_name(jo.getString("last_name"));


                        items.add(pdb);
                    }


// rv.setAdapter(adapter);
                    // skipdata = shopdata.size();
                    feedAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    Log.e("error", ex.getMessage());
                }




                }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response",error.getMessage()+"");
                prog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 16+"");
                params.put("person_id", Singleton.pref.getString("person_id", ""));


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