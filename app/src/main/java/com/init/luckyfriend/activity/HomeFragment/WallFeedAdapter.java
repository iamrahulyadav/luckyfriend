package com.init.luckyfriend.activity.HomeFragment;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.CircleImageView;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.ExtendedProfile.FullExtendedProfile;
import com.init.luckyfriend.activity.ExtendedProfile.FullProfile;
import com.init.luckyfriend.activity.Profile.Profile;
import com.init.luckyfriend.activity.Singleton;
import com.nostra13.universalimageloader.core.ImageLoader;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WallFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    List<WallDataBean> data;
    ImageLoader imageLoader = ImageLoader.getInstance();
    public ImageButton likeicon;

    public WallFeedAdapter(Activity context, List<WallDataBean> list) {
        this.context = context;
        data = list;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
        bindDefaultFeedItem(position, holder);


    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

        final int pos = position;
        WallDataBean gd = data.get(position);


        holder.likes.setText(gd.getPost_likes() + "");
        Singleton.imageLoader.displayImage(gd.getPerson_profile_img(), holder.ivUserProfile, Singleton.defaultOptions);

        // post images
        Singleton.imageLoader.displayImage(gd.getPost_img(), holder.ivFeedCenter, Singleton.defaultOptions);


        holder.country.setText(gd.getPerson_country());
        holder.comment.setText(gd.getPost_comments());
        holder.name.setText(gd.getUser_name());


    }

    private void updatelike(final String postid, final String personid, final int pos) {
        // String url ="http://192.168.0.7/test.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response like", response.toString());
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    if (jobj.getBoolean("status")) {
                        int val = jobj.getInt("value");
                        if (val == 1) {
                            WallDataBean wdb = data.get(pos);
                            wdb.setPost_likes(wdb.getPost_likes() - 1);
                            notifyItemChanged(pos);

                        } else {
                            WallDataBean wdb = data.get(pos);
                            wdb.setPost_likes(wdb.getPost_likes() + 1);
                            notifyItemChanged(pos);

                        }
                    }

                } catch (Exception ex) {
                    Log.e("error", ex.getMessage() + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//showing snakebar here
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 3 + "");
                params.put("post_id", postid);
                params.put("person_id", Singleton.pref.getString("person_id", ""));


                return params;
            }


        };
        queue.add(sr);
    }


    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        else
            return data.size();

    }


    public class CellFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView ivUserProfile;
        ImageView ivFeedCenter;
        TextView caption, likes, comment, country, name;
        ImageButton add, commenticon;


        public CellFeedViewHolder(View view) {
            super(view);

            ivUserProfile = (CircleImageView) view.findViewById(R.id.ivUserProfile);
            ivFeedCenter = (ImageView) view.findViewById(R.id.ivFeedCenter);
            add = (ImageButton) view.findViewById(R.id.add);
            likeicon = (ImageButton) view.findViewById(R.id.like_icon);
            commenticon = (ImageButton) view.findViewById(R.id.comment_icon);
            caption = (TextView) view.findViewById(R.id.caption);
            likes = (TextView) view.findViewById(R.id.likes);
            comment = (TextView) view.findViewById(R.id.comments);
            country = (TextView) view.findViewById(R.id.country);
            name = (TextView) view.findViewById(R.id.name);

            likeicon.setOnClickListener(this);
            add.setOnClickListener(this);
            commenticon.setOnClickListener(this);
            ivFeedCenter.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            WallDataBean wdb = data.get(getAdapterPosition());

            switch (view.getId()) {
                case R.id.like_icon:
                    updatelike(wdb.getPost_id(), wdb.getPerson_id(), getAdapterPosition());
                    break;

                case R.id.add:
                    Toast.makeText(context, "friend request sent", Toast.LENGTH_LONG).show();
//                    sendFriendRequest(wdb.getPerson_id(), getAdapterPosition());
                    String friend_person_id = wdb.getPerson_id();

                    sendFriendRequest(friend_person_id);
                    break;

                case R.id.comment_icon:
                    Dialog comment = new Dialog(context, R.style.FullHeightDialog);
                    comment.setContentView(R.layout.comments);
                    ProgressBar progress = (ProgressBar) comment.findViewById(R.id.progress);
                    progress.setVisibility(View.GONE);
                    comment.show();
                    break;

                case R.id.ivFeedCenter:

                    Toast.makeText(context, wdb.getUser_name(), Toast.LENGTH_LONG).show();
                    android.app.FragmentManager manager=context.getFragmentManager();
                    Fragment newFragment = new FullProfile();
                    android.app.FragmentTransaction transaction = manager.beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                    transaction.replace(R.id.container_body ,new Profile());
                    transaction.addToBackStack(null);

// Commit the transaction
                    transaction.commit();

            }
        }

        private void sendFriendRequest(final String friend_person_id) {
            //String url ="http://192.168.0.7/test.php";

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest sr = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.url), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("friend add", response.toString());
                    try {

                        JSONObject jobj = new JSONObject(response.toString());
                        if (jobj.getBoolean("status")) {
                            int val = jobj.getInt("value");
                            if (val == 1) {
                                //WallDataBean wdb = data.get(pos);
                                // wdb.setPost_likes(wdb.getPost_likes() - 1);
                                // notifyItemChanged(pos);
                            } else {
                                //WallDataBean wdb = data.get(pos);
                                //wdb.setPost_likes(wdb.getPost_likes() + 1);
                                //notifyItemChanged(pos);

                            }
                        }

                    } catch (Exception ex) {
                        Log.e("json parsing error", ex.getMessage() + "");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//showing snakebar here
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("rqid", 12 + "");
                    params.put("person_id", Singleton.pref.getString("person_id", ""));
                    params.put("friend_reqperson_id", friend_person_id);
                    params.put("noti_type",2+"");

                    return params;
                }


            };
            queue.add(sr);

        }


    }
}