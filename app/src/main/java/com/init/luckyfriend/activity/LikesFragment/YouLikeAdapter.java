package com.init.luckyfriend.activity.LikesFragment;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.init.luckyfriend.activity.DATA.CommentData;
import com.init.luckyfriend.activity.DATA.FavouriteDataBean;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.HomeFragment.CommentArrayAdapter;
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 4/12/2016.
 */
public class YouLikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<FavouriteDataBean> data;
    public ArrayList<CommentData> loaded=new ArrayList<>();
    ProgressBar progress;
    public CommentArrayAdapter adapter;
    RecyclerView loadcomments;



    public YouLikeAdapter(Context context, List<FavouriteDataBean> list) {
        this.context = context;data=list;
    }
    public YouLikeAdapter(Context context) {
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.youlike_item_feed, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;

        FavouriteDataBean gd=data.get(position);
        if(gd.getIsliked()==1) {

            holder.likes.setImageResource(R.drawable.like_filled);
        }
        else
            holder.likes.setImageResource(R.drawable.like_icon);



        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

        FavouriteDataBean gd=data.get(position);

        holder.likecount.setText(gd.getPost_likes() + "");
if(gd.getPost_img()!=null) {
    Singleton.imageLoader.displayImage(gd.getPost_img(), holder.ivFeedCenter, Singleton.defaultOptions);

}
        else
holder.ivFeedCenter.setImageResource(R.drawable.iiiii);
    Singleton.imageLoader.displayImage(gd.getPost_user_profile_pic(), holder.userimg, Singleton.defaultOptions);
        holder.username.setText(gd.getPost_user_first_name() + " " + gd.getPost_user_last_name());

        holder.userdetails.setText(gd.getPost_user_dob() + "," + gd.getPost_user_country());

    }


    @Override
    public int getItemCount() {
        if(data==null)
            return 0;
        else
            return data.size();

    }

    public class CellFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivFeedCenter,userimg;
        TextView caption;
        ImageButton likes,comments,add,send;
        TextView likecount,username,userdetails;
        EditText comment;
        Dialog commentdialog;


        public CellFeedViewHolder(View view) {
            super(view);

            ivFeedCenter = (ImageView) view.findViewById(R.id.ivFeedCenter);
            userimg=(ImageView)view.findViewById(R.id.ivUserProfile);
            likecount=(TextView)view.findViewById(R.id.likescount);
            username=(TextView)view.findViewById(R.id.username);
            userdetails=(TextView)view.findViewById(R.id.details);
            caption = (TextView) view.findViewById(R.id.caption);
            likes=(ImageButton)view.findViewById(R.id.like);
            comments=(ImageButton)view.findViewById(R.id.comments);
            add=(ImageButton)view.findViewById(R.id.add);


            likes.setOnClickListener(this);
            comments.setOnClickListener(this);
            add.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final FavouriteDataBean wdb=data.get(getAdapterPosition());
           switch (view.getId()){
               case R.id.like:
                   updatelike(wdb.getPost_id(), wdb.getPerson_id(), getAdapterPosition());
                   break;

               case R.id.add:
                   Toast.makeText(context, "friend request sent", Toast.LENGTH_LONG).show();
//                    sendFriendRequest(wdb.getPerson_id(), getAdapterPosition());
                   String friend_person_id = wdb.getPerson_id();

                   sendFriendRequest(friend_person_id);
                   break;

               case R.id.comments:
                   loaded.clear();
                   commentdialog = new Dialog(context, R.style.MyDialog);

                   commentdialog.setContentView(R.layout.comments);
                   loadcomments=(RecyclerView)commentdialog.findViewById(R.id.cardList);
                   RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                   loadcomments.setLayoutManager(mLayoutManager);


                   comment=(EditText)commentdialog.findViewById(R.id.commentbox);
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
                           send(comment.getText().toString(),wdb.getPost_id(),wdb.getPerson_id());

                       }
                   });


                   commentdialog.show();
                   break;




           }

        }

        private void send(String commenttxt,final String post_id,final String person_id) {

            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date todayDate = new Date();
            String thisDate = currentDate.format(todayDate);

            CommentData commentData=new CommentData();
            commentData.setCommenttxxt(commenttxt);
            commentData.setUname(Singleton.pref.getString("uname", ""));
            commentData.setProfilepic(Singleton.pref.getString("profile_pic", ""));
            commentData.setCtime(thisDate);
            loaded.add(commentData);

            adapter.notifyDataSetChanged();
            comment.setText("");

            sendMessageToServer(commenttxt,thisDate,post_id,person_id);
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
                                FavouriteDataBean wdb = data.get(pos);
                                wdb.setPost_likes(wdb.getPost_likes() - 1);
                                wdb.setIsliked(0);
                                notifyItemChanged(pos);

                            } else {
                                FavouriteDataBean wdb = data.get(pos);
                                wdb.setPost_likes(wdb.getPost_likes() + 1);
                                wdb.setIsliked(1);
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

    }




