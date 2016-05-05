package com.init.luckyfriend.activity.MyPost;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.init.luckyfriend.activity.CircleImageView;
import com.init.luckyfriend.activity.DATA.CommentData;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.EditPost;
import com.init.luckyfriend.activity.HomeFragment.CommentArrayAdapter;
import com.init.luckyfriend.activity.MainActivity;
import com.init.luckyfriend.activity.NewPost;
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PostFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Activity context;
    CircleImageView imgChoose;
    List<PostDataBean> data;
    String imgEncoded,description,fileName,date;
    AlertDialog alertDialog;
    public ArrayList<CommentData> loaded=new ArrayList<>();
    ProgressBar progress;
    public CommentArrayAdapter adapter;
    RecyclerView loadcomments;


    public PostFeedAdapter(Activity context, List<PostDataBean> list) {
        this.context = context;
        data = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.mypost_row_layout, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;

        PostDataBean gd=data.get(position);
        if(gd.getIsliked()==1) {

            holder.like_icon.setImageResource(R.drawable.like_filled);
        }
        else
            holder.like_icon.setImageResource(R.drawable.like_icon);



        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

       PostDataBean gd=data.get(position);




        Singleton.imageLoader.displayImage(gd.getPerson_profile_pic(),holder.profilepic,Singleton.defaultOptions);
        Singleton.imageLoader.displayImage(gd.getPost_img(),holder.postimage,Singleton.defaultOptions);

        holder.name.setText(gd.getUser_name() + " " + gd.getLast_name());
        holder.date.setText(gd.getPost_date());
        holder.like.setText(gd.getPost_likes()+"");
        holder.comment.setText(gd.getPost_comments()+"");
       }


    @Override
    public int getItemCount() {
        if(data==null)
            return 0;
        else
        return data.size();

    }

    public class CellFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView  postimage, options;
        CircleImageView profilepic;
        TextView name, date, comment, like;
        ImageButton like_icon,comment_icon,send;
        EditText comments;
        Dialog commentdialog;

        public CellFeedViewHolder(View view) {
            super(view);

            profilepic = (CircleImageView) view.findViewById(R.id.profilepic);
            postimage = (ImageView) view.findViewById(R.id.ivFeedCenter);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            comment = (TextView) view.findViewById(R.id.comments);
            like = (TextView) view.findViewById(R.id.likes);
            like_icon = (ImageButton) view.findViewById(R.id.like_icon);
            comment_icon = (ImageButton) view.findViewById(R.id.comment_icon);


            options = (ImageView) view.findViewById(R.id.options);

            options.setOnClickListener(this);
            like_icon.setOnClickListener(this);
            comment_icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final PostDataBean wdb=data.get(getAdapterPosition());

            switch (view.getId()) {

                case R.id.options:
                    Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
                    PopupMenu popupMenu = new PopupMenu(wrapper, view);
                    popupMenu.setOnMenuItemClickListener(new MenuClick(getPosition()));
                    popupMenu.inflate(R.menu.action_menu);
                    popupMenu.show();
                    break;

                case R.id.like_icon:
                    updatelike(wdb.getPost_id(), wdb.getPerson_id(), getAdapterPosition());
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
        private void send(String comment,final String post_id,final String person_id) {

            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date todayDate = new Date();
            String thisDate = currentDate.format(todayDate);

            CommentData commentData=new CommentData();
            commentData.setCommenttxxt(comment);
            commentData.setUname(Singleton.pref.getString("uname", ""));
            commentData.setProfilepic(Singleton.pref.getString("profile_pic", ""));
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
                                PostDataBean wdb = data.get(pos);
                                wdb.setPost_likes(wdb.getPost_likes() - 1);
                                wdb.setIsliked(0);
                                notifyItemChanged(pos);

                            } else {
                                PostDataBean wdb = data.get(pos);
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

    private class MenuClick implements PopupMenu.OnMenuItemClickListener {
        int pos;

        public MenuClick(int pos) {
            this.pos = pos;
        }

        @Override
        public boolean onMenuItemClick(final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.user_edit:
                    //Toast.makeText(context, "Edit Clicked" + pos, Toast.LENGTH_SHORT).show();
                    PostDataBean post=data.get(pos);
                    Intent Edit=new Intent(context, EditPost.class);
                    Edit.putExtra("data",post);
                    Edit.putExtra("post",pos);

                    context.startActivity(Edit);
                    break;



                case R.id.user_delete:
//                    Toast.makeText(context, "Delete Clicked" + pos, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Do you really want to delete the post ?");

                    alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                           // Toast.makeText(context, "You clicked yes button", Toast.LENGTH_LONG).show();
                            PostDataBean post=data.get(pos);
                            deletePost(post.getPost_id(),pos);


                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        }
                    });

                     alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    break;
                            }
            return false;
        }

    }


    private void deletePost(final String post_id,final int pos) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,context.getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("delete post", response.toString());
                //prog.dismiss();

                Toast.makeText(context,"post deleted",Toast.LENGTH_LONG).show();
                data.remove(pos);
                notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //prog.dismiss();
                Log.e("error",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 28+"");
                params.put("person_id",Singleton.pref.getString("person_id",""));
                params.put("post_id", post_id);
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