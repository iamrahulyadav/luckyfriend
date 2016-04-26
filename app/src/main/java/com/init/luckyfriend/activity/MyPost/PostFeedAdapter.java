package com.init.luckyfriend.activity.MyPost;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.EditPost;
import com.init.luckyfriend.activity.MainActivity;
import com.init.luckyfriend.activity.NewPost;
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PostFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Activity context;
    CircleImageView imgChoose;
    private List<PostDataBean> data;
    String imgEncoded,description,fileName,date;
    AlertDialog alertDialog;

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
        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {

       PostDataBean gd=data.get(position);




        Singleton.imageLoader.displayImage(gd.getPerson_profile_pic(),holder.profilepic,Singleton.defaultOptions);
        Singleton.imageLoader.displayImage(gd.getPost_img(),holder.postimage,Singleton.defaultOptions);

        holder.name.setText(gd.getUser_name() + " " + gd.getLast_name());
        holder.date.setText(gd.getPost_date());
        holder.like.setText(gd.getPost_likes());
        holder.comment.setText(gd.getPost_comments());
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

        public CellFeedViewHolder(View view) {
            super(view);

            profilepic = (CircleImageView) view.findViewById(R.id.profilepic);
            postimage = (ImageView) view.findViewById(R.id.ivFeedCenter);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            comment = (TextView) view.findViewById(R.id.comments);
            like = (TextView) view.findViewById(R.id.likes);

            options = (ImageView) view.findViewById(R.id.options);

            options.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.options) {
                Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
                PopupMenu popupMenu = new PopupMenu(wrapper, view);
                popupMenu.setOnMenuItemClickListener(new MenuClick(getPosition()));
                popupMenu.inflate(R.menu.action_menu);
                popupMenu.show();
            }

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
                    Edit.putExtra("post_id",post.getPost_id());
                    Edit.putExtra("post_img",post.getPost_img());


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
                            deletePost(post.getPost_id());


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


    private void deletePost(final String post_id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,context.getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("delete post", response.toString());
                //prog.dismiss();

                Toast.makeText(context,"post deleted",Toast.LENGTH_LONG).show();


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