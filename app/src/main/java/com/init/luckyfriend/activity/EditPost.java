package com.init.luckyfriend.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.init.luckyfriend.activity.MyPost.PostDataBean;
import com.init.luckyfriend.activity.MyPost.PostFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 4/23/2016.
 */
public class EditPost extends AppCompatActivity {

    CircleImageView imgchoose;
    ImageButton upload,cross;
    public static int GALLERY_INTENT_CALLED=200;
    String imgEncoded,description;
    EditText desc;
    ProgressDialog prog;
    String date;
    String fname;
    String post_id,post_img;
    TextView text;
    PostDataBean pdb;
    int pos=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editpost);

        imgchoose=(CircleImageView)findViewById(R.id.imgchoose);
        upload=(ImageButton)findViewById(R.id.upload);
        cross=(ImageButton)findViewById(R.id.cross);
        desc=(EditText)findViewById(R.id.adddesc);
        text=(TextView)findViewById(R.id.text);


        prog=new ProgressDialog(this);
        prog.setMessage("wait uploading post...");

        text.setText("Edit Post");
        Bundle extra=getIntent().getExtras();
        if(extra!=null) {
            pdb = (PostDataBean) extra.get("data");
            post_id=pdb.getPost_id();
            post_img =pdb.getPost_img();
            pos=extra.getInt("pos");
        }
        //getPost(post_id);

        fname = new Date().getTime()+"luckyfriendsnewpost"+".jpg";

        Singleton.imageLoader.displayImage(post_img,imgchoose,Singleton.defaultOptions);

        imgchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);

            }
        });


        description=desc.getText().toString();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgEncoded == null) {
                    Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(imgEncoded!=null) {
                    sendpost();

                }
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Calendar cal = Calendar.getInstance();
        //cal.setTime(date);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        date=month+"/"+day+"/"+year;
        Log.e("date", date);
    }


    private void sendpost() {

        prog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //prog.setVisibility(View.GONE);
                Log.e("post", response.toString());
                prog.dismiss();

                    try {
                        JSONObject jobj=new JSONObject(response.toString());
                        String urlimage=jobj.getString("post_img");
                        Toast.makeText(getApplicationContext(),"uploaded successfully",Toast.LENGTH_LONG).show();
                        PostFragment.pf.refresh(urlimage, pos);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
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
                params.put("rqid", 27+"");
                params.put("person_id",Singleton.pref.getString("person_id", ""));
                params.put("encoded_image",imgEncoded);
                params.put("date",date+"");
                params.put("post_id",post_id);
                params.put("fname",fname);
               // Toast.makeText(getApplicationContext(),"fname"+ fname,Toast.LENGTH_LONG).show();
                Log.e("fname",fname);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_INTENT_CALLED&&resultCode==RESULT_OK)
        {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream =getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgchoose.setImageBitmap(selectedImage);

                if (selectedImage != null) {

                    imgchoose.setImageBitmap(selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    byte[] byteArray;

                    byteArray = stream.toByteArray();
                    imgEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


                    Log.e("encoded", imgEncoded);

                }


            }
            catch (Exception e) {
                e.printStackTrace();
                Log.e("error",e.getMessage());
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
