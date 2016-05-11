package com.init.luckyfriend.activity.MyPost;

import android.app.ProgressDialog;
import android.content.Intent;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostFragment extends Fragment {

    RecyclerView rvFeed;
    LinearLayoutManager linearLayoutManager;
    PostFeedAdapter feedAdapter;
    private ProgressDialog prog;
    ArrayList<PostDataBean> items=new ArrayList<>();
    Toolbar topToolBar;
    TextView notification;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static PostFragment  pf;
    String lastname,firstname,profilepic;
    private boolean loading = true;
    int visibleItemCount;
    int pastVisiblesItems, totalItemCount;
    int skipdata=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

             prog=new ProgressDialog(getActivity());
             prog.setMessage("Wait loading data....");
             pf=this;
             //getPost();
        Bundle extra = getArguments();
        if (extra == null)
            getPost();
        else {
            //  Toast.makeText(getActivity(),extra.getString("data"),Toast.LENGTH_SHORT).show();
            //  ParseData(extra.getString("data"));
            getPost();
             }

    }

    private void ParseData(String response) {

        try {
            JSONObject jobj = new JSONObject(response.toString());
            JSONArray jarray = jobj.getJSONArray("data");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);

                PostDataBean pdb = new PostDataBean();
                //pdb.setPost_id(jo.getString("post_id"));
                pdb.setPost_img(jo.getString("imgurl"));
                pdb.setPost_date(jo.getString("post_date"));
                pdb.setPost_likes(0);
                pdb.setPost_comments(0);
                pdb.setPerson_profile_pic(Singleton.pref.getString("uimage",""));
                pdb.setUser_name(firstname);
                pdb.setLast_name(lastname);
                pdb.setIsliked(0);
               // pdb.setPerson_id(jo.getString("person_id"));
                //  Log.e("postdetails",lastname+firstname+profilepic);
                items.add(pdb);
                Log.e("item new post",items+"");
            }


            feedAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
//                    Log.e("error", ex.getMessage());
        }



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

        rvFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            getPost();
//                            Toast.makeText(getActivity(), "called", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });





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
                items.clear();

                try {
                    JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    if (jarray.length() == 0) {
                        // dataleft = false;
                        Toast.makeText(getContext(),"No posts yet...",Toast.LENGTH_LONG).show();

                        return;
                    }
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);

                        PostDataBean pdb = new PostDataBean();
                        pdb.setPost_id(jo.getString("post_id"));
                        pdb.setPost_img(jo.getString("post_img"));
                        pdb.setPost_date(jo.getString("post_date"));
                        pdb.setPost_likes(jo.getInt("post_likes"));
                        pdb.setPost_comments(jo.getInt("post_comments"));
                        pdb.setPerson_profile_pic(jo.getString("person_profile_pic"));
                        pdb.setUser_name(jo.getString("user_name"));
                        pdb.setLast_name(jo.getString("last_name"));
                        pdb.setIsliked(jo.getInt("isliked"));
                        pdb.setPerson_id(jo.getString("person_id"));

                        lastname=jo.getString("last_name");;
                        firstname=jo.getString("user_name");
                        //profilepic=jo.getString("person_profile_pic");

                      //  Log.e("postdetails",lastname+firstname+profilepic);
                        items.add(pdb);
                    }


                    skipdata=items.size();
                    if(jarray.length()<5)
                        loading=false;
                    else
                        loading=true;
                    feedAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
//                    Log.e("error", ex.getMessage());
                }




                }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response",error.getMessage()+"");
      //          prog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 16+"");
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
public void refresh(String url,int pos)
{
    Toast.makeText(getActivity(),"called",Toast.LENGTH_LONG).show();
    PostDataBean pdb=items.get(pos);
    pdb.setPost_img(url);
    feedAdapter.notifyDataSetChanged();
}

}