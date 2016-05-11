package com.init.luckyfriend.activity.NearByTinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
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
import com.bumptech.glide.Glide;
import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Singleton;
import com.init.luckyfriend.activity.tindercard.FlingCardListener;
import com.init.luckyfriend.activity.tindercard.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nearby extends Fragment implements FlingCardListener.ActionDownInterface {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> al=new ArrayList<>();
    private SwipeFlingAdapterView flingContainer;
    ProgressDialog prog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prog=new ProgressDialog(getActivity());
        prog.setMessage("Wait loading data ....");



        getnearby();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nearby, container, false);

        flingContainer = (SwipeFlingAdapterView)rootView.findViewById(R.id.frame);

        myAppAdapter = new MyAppAdapter(al, getActivity());
        flingContainer.setAdapter(myAppAdapter);



        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                String person_like_id=al.get(0).getPerson_id();
                sendlike(person_like_id);

                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "like", Toast.LENGTH_LONG).show();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {

                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "dislike", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();

//                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                //view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });




        // Inflate the layout for this fragment
        return rootView;

    }

    private void sendlike(final String person_like_id) {

        //String url ="http://192.168.0.7/test.php";

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST,getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("like", response.toString());
                //prog.dismiss();
                try {

                   // Toast.makeText(getContext(),"No nearby available...",Toast.LENGTH_LONG).show();
                  /*  JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    if (jarray.length() == 0) {
                        // dataleft = false;
                        return;
                    }
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        Data pdb=new Data();
                        pdb.setLast_name(jo.getString("last_name"));
                        pdb.setPerson_name(jo.getString("person_name"));
                        pdb.setPerson_country(jo.getString("person_country"));
                        pdb.setPerson_profile_pic(jo.getString("person_profile_pic"));
                        pdb.setPerson_id(jo.getString("person_id"));

                        al.add(pdb);
                    }


                    // rv.setAdapter(adapter);
                    // skipdata = shopdata.size();
                    myAppAdapter.notifyDataSetChanged();
                    */
                } catch (Exception ex) {
                    Log.e("error", ex.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 9+"");
                params.put("person_like_id",person_like_id);
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

    private void getnearby() {
        prog.show();
       // String url ="http://108.178.10.78/androidios/test.php";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST,getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("nearby response", response.toString());
            prog.dismiss();
                al.clear();
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    if (jarray.length() == 0) {
                        // dataleft = false;
                       Toast.makeText(getActivity(),"No matches for nearby...",Toast.LENGTH_LONG).show();
                        return;
                    }
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        Data pdb=new Data();
                        pdb.setLast_name(jo.getString("last_name"));
                        pdb.setPerson_name(jo.getString("person_name"));
                        pdb.setPerson_country(jo.getString("person_country"));
                        pdb.setPerson_profile_pic(jo.getString("person_profile_pic"));
                        pdb.setPerson_id(jo.getString("person_id"));

                        al.add(pdb);
                    }


                    // rv.setAdapter(adapter);
                    // skipdata = shopdata.size();
                    myAppAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    Log.e("json parsing error", ex.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 8+"");
                params.put("person_country",Singleton.pref.getString("person_country",""));
                params.put("person_loc", Singleton.pref.getString("person_city", ""));
                params.put("person_id", Singleton.pref.getString("person_id", ""));

               // Log.e("location details",Singleton.pref.getString("person_country","")+""+Singleton.pref.getString("person_city", "")+""+Singleton.pref.getString("person_id", ""));


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
    public void onActionDownPerform() {

    }
    public static void removeBackground() {


        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();

    }


public static class ViewHolder
{
    public static FrameLayout background;
    // public TextView DataText;
    public ImageView cardImage;
    public TextView name,dob;

}

public class MyAppAdapter extends BaseAdapter {


    public List<Data> parkingList;
    public Context context;

    private MyAppAdapter(List<Data> apps, Context context) {
        this.parkingList = apps;
        this.context = context;
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;


        if (rowView == null) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_tinder, parent, false);

            // configure view holder
            viewHolder = new ViewHolder();
            //   viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
         viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
            viewHolder.name=(TextView)rowView.findViewById(R.id.name);
            viewHolder.dob=(TextView)rowView.findViewById(R.id.dob);
           viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Singleton.imageLoader.displayImage(parkingList.get(position).getPerson_profile_pic(), viewHolder.cardImage, Singleton.defaultOptions);
        viewHolder.name.setText(parkingList.get(position).getPerson_name()+parkingList.get(position).getLast_name());
        viewHolder.dob.setText(parkingList.get(position).getPerson_country());
        return rowView;
    }
}
}
