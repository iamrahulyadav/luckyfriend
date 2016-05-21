package com.init.luckyfriend.activity.EditProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.MyPost.PostFragment;
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ImageEdit extends AppCompatActivity implements View.OnClickListener {

    ImageView profilepic;
    ImageButton back,upload;
    public static int GALLERY_INTENT_CALLED=200;
    String imgEncoded;
ProgressDialog prog;
    String fname;
    String urlimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);

        profilepic=(ImageView)findViewById(R.id.profile);
        back=(ImageButton)findViewById(R.id.back);
        upload=(ImageButton)findViewById(R.id.upload);

        Singleton.imageLoader.displayImage(Singleton.pref.getString("uimage",""),profilepic,Singleton.defaultOptions);

        profilepic.setOnClickListener(this);
        back.setOnClickListener(this);
        upload.setOnClickListener(this);

        prog=new ProgressDialog(this);
        prog.setMessage("Wait uploading image....");

        fname = new Date().getTime()+"luckyfriendprofilepic"+".jpg";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_edit, menu);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.back:
                Intent back=new Intent();
                setResult(3,back);
                finish();
                break;
            case R.id.profile:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);
                break;
            case R.id.upload:
                if (imgEncoded == null) {
                    Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(imgEncoded!=null) {
                    sendpost();

                }


        }

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
                profilepic.setImageBitmap(selectedImage);

                if (selectedImage != null) {

                    profilepic.setImageBitmap(selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    byte[] byteArray;

                    byteArray = stream.toByteArray();
                    imgEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


                    Log.e("profile pic", imgEncoded);

                }


            }
            catch (Exception e) {
                e.printStackTrace();
                Log.e("error",e.getMessage());
            }
        }

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
                     urlimage=jobj.getString("imgurl");
                    Toast.makeText(getApplicationContext(),"uploaded successfully",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor edit=Singleton.pref.edit();
                    edit.putString("uimage",urlimage);
                    edit.commit();

                    Intent change=new Intent();
                    change.putExtra("imgurl",urlimage);
                    setResult(3, change);
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
                params.put("rqid", 35+"");
                params.put("person_id", Singleton.pref.getString("person_id", ""));
                params.put("encoded_image",imgEncoded);
                params.put("fname",fname);
                // Toast.makeText(getApplicationContext(),"fname"+ fname,Toast.LENGTH_LONG).show();
                Log.e("fname",imgEncoded);
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
    public void onBackPressed() {

        Intent back=new Intent();
        setResult(3,back);
        finish();

    }
}
