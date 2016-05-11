package com.init.luckyfriend.activity.WallSearch;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.init.luckyfriend.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.init.luckyfriend.activity.DATA.CommentData;
import com.init.luckyfriend.activity.DATA.FavouriteDataBean;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.HomeFragment.CommentArrayAdapter;
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2/19/2016.
 */
public class FavouriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    public List<FavouriteDataBean> data;
    public ArrayList<CommentData> loaded=new ArrayList<>();
    ProgressBar progress;
    public CommentArrayAdapter adapter;
    RecyclerView loadcomments;


    public FavouriteAdapter(Context context, List<FavouriteDataBean> list) {
        this.context = context;data=list;
    }
    public FavouriteAdapter(Context context) {
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.wall_search_item_feed, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

        FavouriteDataBean gd=data.get(position);

        holder.likecount.setText(gd.getPost_likes() + "");
        Singleton.imageLoader.displayImage(gd.getPost_img(), holder.ivFeedCenter, Singleton.defaultOptions);
        Singleton.imageLoader.displayImage(gd.getPost_user_profile_pic(), holder.userimg, Singleton.defaultOptions);
        if(gd.getPost_user_last_name()==null){
          String last_name="";
        }
        else {
            String last_name = gd.getPost_user_last_name();
        }
        holder.username.setText(gd.getPost_user_first_name() );

        holder.userdetails.setText(gd.getPost_user_dob() + "," + gd.getPost_user_country());

       }


    @Override
    public int getItemCount() {
        if(data==null)
            return 0;
        else
        return data.size();

         }

    public  class CellFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivFeedCenter,userimg;
        TextView caption;
        ImageButton likes,comments;
        TextView likecount,username,userdetails;
        EditText commentsbox;
        Dialog commentdialog;
        ImageButton send;

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

            comments.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            final FavouriteDataBean fdb=data.get(getAdapterPosition());

            switch (view.getId()){
                case R.id.comments:
                    loaded.clear();
                    commentdialog = new Dialog(context, R.style.MyDialog);

                    commentdialog.setContentView(R.layout.comments);
                    loadcomments=(RecyclerView)commentdialog.findViewById(R.id.cardList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                    loadcomments.setLayoutManager(mLayoutManager);


                    commentsbox=(EditText)commentdialog.findViewById(R.id.commentbox);
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

                    readComments(fdb.getPost_id());

                    adapter=new CommentArrayAdapter(context,loaded);
                    loadcomments.setAdapter(adapter);


                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //   sendMessage(comments.getText().toString(), UserType.OTHER);
                            send(commentsbox.getText().toString(),fdb.getPost_id());

                        }
                    });


                    commentdialog.show();
                    break;

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
                    params.put("noti_type",0+"");

                    return params;
                }


            };
            queue.add(sr);



        }

        private void send(String comment,final String post_id) {

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
            commentsbox.setText("");

            sendMessageToServer(comment,thisDate,post_id);
        }

        private void sendMessageToServer(final String comment, final String thisDate,final String postid) {
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
                    params.put("comment",comment);
                    params.put("date",thisDate);
                    params.put("post_id",postid);
                    //params.put("noti_type",0+"");
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


}
