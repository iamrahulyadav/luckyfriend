package com.init.luckyfriend.activity.HomeFragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.init.luckyfriend.activity.ChatFragment.ChatActivity;
import com.init.luckyfriend.activity.ChatFragment.ChatListAdapter;
import com.init.luckyfriend.activity.ChatFragment.ChatMessage;
import com.init.luckyfriend.activity.ChatFragment.Status;
import com.init.luckyfriend.activity.ChatFragment.UserType;
import com.init.luckyfriend.activity.CircleImageView;
import com.init.luckyfriend.activity.DATA.CommentData;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.ExtendedProfile.FullExtendedProfile;
import com.init.luckyfriend.activity.ExtendedProfile.FullProfile;
import com.init.luckyfriend.activity.MainActivity;
import com.init.luckyfriend.activity.PhotosFragment.PhotosFragment;
import com.init.luckyfriend.activity.Profile.Profile;
import com.init.luckyfriend.activity.Singleton;
import com.nostra13.universalimageloader.core.ImageLoader;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class WallFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    List<WallDataBean> data;
    ImageLoader imageLoader = ImageLoader.getInstance();
    public ImageButton likeicon;
    private boolean side = false;
    public ArrayList<CommentData> loaded=new ArrayList<>();
    ProgressBar progress;
    public CommentArrayAdapter adapter;
    RecyclerView loadcomments;
    RecyclerView recyclerView;
    private boolean loading;
    int visibleItemCount = 5;
    int pastVisiblesItems, totalItemCount;


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

        WallDataBean gd=data.get(position);
        if(gd.getIsLiked()==1) {

            holder.likeicon.setImageResource(R.drawable.like_filled);
        }
        else
            holder.likeicon.setImageResource(R.drawable.like_icon);


        if(gd.getIsfriend()==1) {

            holder.add.setVisibility(View.GONE);
        }
        else
            holder.add.setVisibility(View.VISIBLE);

        bindDefaultFeedItem(position, holder);


    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

        final int pos = position;
        WallDataBean gd = data.get(position);


        holder.likes.setText(gd.getPost_likes()+ "");
        Singleton.imageLoader.displayImage(gd.getPerson_profile_img(), holder.ivUserProfile, Singleton.defaultOptions);

        // post images
        if(gd.getPost_img()==null){
            holder.ivFeedCenter.setImageResource(R.drawable.iiiii);
            }
        else
           Singleton.imageLoader.displayImage(gd.getPost_img(), holder.ivFeedCenter, Singleton.defaultOptions);


        holder.country.setText(gd.getPeron_dob()+", "+gd.getPerson_country());
        //holder.comment.setText(2+"");
        holder.name.setText(gd.getUser_name() + " " + gd.getLast_name());


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
                            wdb.setIsLiked(0);
                            notifyItemChanged(pos);

                        } else {
                            WallDataBean wdb = data.get(pos);
                            wdb.setPost_likes(wdb.getPost_likes() + 1);
                            wdb.setIsLiked(1);
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
                params.put("receiver_id",personid);
                //params.put("noti_type",1+"");

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
        EditText comments;
    Dialog commentdialog;
        ImageButton likeicon,send;

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
            final WallDataBean wdb = data.get(getAdapterPosition());

            switch (view.getId()) {
                case R.id.like_icon:
             updatelike(wdb.getPost_id(), wdb.getPerson_id(), getAdapterPosition());
                    break;

                case R.id.add:
                    //Toast.makeText(context, "friend request sent", Toast.LENGTH_LONG).show();
                    String friend_person_id = wdb.getPerson_id();
                    String post_id=wdb.getPost_id();
                    sendFriendRequest(friend_person_id,post_id);
                    break;

                case R.id.comment_icon:
                    loaded.clear();
                    commentdialog = new Dialog(context, R.style.MyDialog);

                    commentdialog.setContentView(R.layout.comments);
                    loadcomments=(RecyclerView)commentdialog.findViewById(R.id.cardList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                    loadcomments.setLayoutManager(mLayoutManager);


                    comments=(EditText)commentdialog.findViewById(R.id.commentbox);
                     send=(ImageButton)commentdialog.findViewById(R.id.send);
                    TextView close=(TextView)commentdialog.findViewById(R.id.closedialog);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            commentdialog.dismiss();
                        }
                    });
                     progress = (ProgressBar) commentdialog.findViewById(R.id.progress);
                     progress.setVisibility(View.GONE);

                     //addList();

                    readComments(wdb.getPost_id());

                    adapter=new CommentArrayAdapter(context,loaded);
                    loadcomments.setAdapter(adapter);


                     send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        //   sendMessage(comments.getText().toString(), UserType.OTHER);
                        send(comments.getText().toString(),wdb.getPost_id(),wdb.getPerson_id());

                          }
                    });


                    commentdialog.show();
                    break;

                case R.id.ivFeedCenter:

                   // Toast.makeText(context, wdb.getPerson_id(), Toast.LENGTH_LONG).show();
                    youvisited(wdb.getPerson_id());
                    MainActivity.bottomLayout.setVisibility(View.GONE);
                    Intent newFragment = new Intent(context,FullExtendedProfile.class);

                    newFragment.putExtra("person_id",wdb.getPerson_id());
                    context.startActivity(newFragment);

                    break;
            }
        }



        private void send(String comment,final String post_id,final String person_id) {

            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date todayDate = new Date();
            String thisDate = currentDate.format(todayDate);

            CommentData commentData=new CommentData();
            commentData.setCommenttxxt(comment);
            commentData.setUname(Singleton.pref.getString("uname", ""));
            commentData.setProfilepic(Singleton.pref.getString("uimage", ""));
             commentData.setCtime(thisDate);
            loaded.add(commentData);

            adapter.notifyDataSetChanged();
            comments.setText("");

            sendMessageToServer(comment,thisDate,post_id,person_id);
        }

        private void sendMessageToServer(final String commenttext, final String thisDate,final String postid,final String person_id) {
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest sr = new StringRequest(Request.Method.POST,context.getString(R.string.url), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response.toString());
                    //  prog.dismiss();
                    // items.clear();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                 //   prog.dismiss();
//            Log.e("error",error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("rqid", 30+"");
                    params.put("person_id", Singleton.pref.getString("person_id",""));
                    params.put("comment",commenttext);
                    params.put("date",thisDate);
                    params.put("post_id",postid);
                    params.put("receiver_id",person_id);
                    //  params.put("noti_type",0+"");
                    //  Log.e("commenttext",commenttext);
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


        private void sendFriendRequest(final String friend_person_id,final String post_id) {
            //String url ="http://192.168.0.7/test.php";

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest sr = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.url), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("friend add", response.toString());
                    try {

                        JSONObject jobj = new JSONObject(response.toString());
                        if (jobj.getBoolean("status")) {
                            Toast.makeText(context,"Friend request already sent", Toast.LENGTH_LONG).show();
                        }
                            else
                            {
                                Toast.makeText(context,"Friend request sent",Toast.LENGTH_LONG).show();

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
                    params.put("post id", post_id);

                    return params;
                }


            };
            queue.add(sr);

        }


    }

    private void youvisited(final String person_id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("visited ", response.toString());
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
                params.put("rqid", 33 +"");
                params.put("person_id", Singleton.pref.getString("person_id", ""));
                params.put("person_visit_id",person_id );

                return params;
            }


        };
        queue.add(sr);



    }


    private void readComments(final String post_id) {

        progress.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("comments", response.toString());

                progress.setVisibility(View.GONE);
                loaded.clear();
                    try {
                        JSONObject jobj = new JSONObject(response.toString());
                        JSONArray jarray = jobj.getJSONArray("data");
                        if (jarray.length() == 0) {
                            // dataleft = false;
                            return;
                        }

                            for (int i = 0; i < jarray.length(); i++) {


                                JSONObject jo = jarray.getJSONObject(i);
                                CommentData commentData = new CommentData();
                                commentData.setCommenttxxt(jo.getString("comment_desc"));
                                commentData.setUname(jo.getString("user_name"));
                                commentData.setProfilepic(jo.getString("person_profile_pic"));
                                commentData.setCtime(jo.getString("comment_date"));

                                loaded.add(commentData);

                            }
                            //adapter=new CommentArrayAdapter(context,loaded);
                            //loadcomments.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (Exception ex) {
                    Log.e("comment error", ex.getMessage() + "");
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
                params.put("rqid", 29 +"");
                params.put("person_id", Singleton.pref.getString("person_id", ""));
                params.put("post_id", post_id);
 //               params.put("noti_type",0+"");

                return params;
            }


        };
        queue.add(sr);



    }
}