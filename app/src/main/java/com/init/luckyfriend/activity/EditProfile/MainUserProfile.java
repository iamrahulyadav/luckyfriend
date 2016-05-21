package com.init.luckyfriend.activity.EditProfile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.DATA.GetProfiledata;
import com.init.luckyfriend.activity.MainActivity;
import com.init.luckyfriend.activity.NewPost;
import com.init.luckyfriend.activity.Singleton;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

import android.support.v7.graphics.Palette;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainUserProfile extends AppCompatActivity implements View.OnClickListener{

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    @Bind(R.id.aboutuser) TextView aboutuser;
    @Bind(R.id.location) TextView location;
    @Bind(R.id.language) TextView language;
    String imageP;

   // @Bind(R.id.addpost)
    //FloatingActionButton addpost;
    @Bind(R.id.userprofileimage)
    ImageView profimg;
    private AlertDialog alert;
    public static int CAMERA_INTENT_CALLED=100;
    public static int GALLERY_INTENT_CALLED=200;
    ImageView check;
    String encodedImage;
    Uri fileUri;
    Uri selectedImage;
    String picturePath;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_profile);
//intializing butter knife
        ButterKnife.bind(this);
      /*  addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userprof = new Intent(MainUserProfile.this, NewPost.class);
                startActivity(userprof);
                finish();
            }
        });
*/

        String profilepic=Singleton.pref.getString("uimage", "");
       // byte[] decodedString = Base64.decode(profilepic, Base64.DEFAULT);
        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //profimg.setImageBitmap(decodedByte);
//        Singleton.imageLoader.displayImage(profilepic,profimg,Singleton.defaultOptions);
        Singleton.imageLoader.displayImage(Singleton.pref.getString("uimage",""),profimg,Singleton.defaultOptions);


        profimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // showDialog();
            }
        });
        //  setting typeface


        fileName=new Date().getTime()+"luckyfriendprofilepic"+".jpg";

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbarLayout.setTitle(Singleton.pref.getString("uname", ""));

        dynamicToolbarColor();

        toolbarTextAppernce();
        retrievedata();

        //Singleton.imageLoader.displayImage(profilepic,profimg,Singleton.defaultOptions);
        //userinfo.setText(city+","+country);
    }
    private void retrievedata() {

//        String url ="http://192.168.0.7/test.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST,getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("edt response", response.toString());
                try {
                    JSONObject jobj = new JSONObject(response.toString());
                    String lastname=jobj.getString("lastname");
                    String country=jobj.getString("country");
                    String profilepic=jobj.getString("profilepic");
                    String city=jobj.getString("location");
                    String dob=jobj.getString("dob");
                    String about=jobj.getString("aboutme");
                    String languages=jobj.getString("language");
                    String interests=jobj.getString("interest");

                    String uname=jobj.getString("name");
                    collapsingToolbarLayout.setTitle(uname);

                    SharedPreferences.Editor edit=Singleton.pref.edit();
                    edit.putString("uname ", uname);
                    edit.commit();

                    MainActivity.username.setText(uname);
                    Singleton.imageLoader.displayImage(profilepic, profimg, Singleton.defaultOptions);
                    location.setText(city+","+country);
                    aboutuser.setText(about);
                    language.setText(languages);
                    int year=0,mon=0,day=0;
                    String[] data=dob.split("-");
                    year=Integer.parseInt(data[0]);
                    mon=Integer.parseInt(data[1]);
                    day=Integer.parseInt(data[2]);
                   //userinfo .setText(getAge(year, mon, day) +" "+ "years" + ","+country);

                    String[] list=interests.split(",");
                    Log.e("list size",list.length+"");


                } catch (Exception ex) {
                    Log.e("error", ex.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //prog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 10+"");
                params.put("person_id", Singleton.pref.getString("person_id",""));
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

    public int getAge(int DOByear, int DOBmonth, int DOBday) {

        int age;

        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if(DOBmonth > currentMonth){
            --age;
        }
        else if(DOBmonth == currentMonth){
            if(DOBday > todayDay){
                --age;
            }
        }
        return age;
    }


    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.iiiii);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //  collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(Color.parseColor("#2196F3")));
                // collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(Color.parseColor("#2196F3")));
            }
        });
    }


    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_user_profile, menu);
       // check=(ImageView)findViewById(R.id.imgchk);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            Intent imagechange=new Intent();
            imagechange.putExtra("image",imageP);
            setResult(13,imagechange);
            finish();
        }
        else if(menuItem.getItemId()==R.id.editprofile)
        {
            Intent userprof = new Intent(this, EditProfile.class);

            startActivityForResult(userprof, 2);
        }
        else if(menuItem.getItemId()==R.id.imgchk)
        {
           Intent imageedit=new Intent(this,ImageEdit.class);
            startActivityForResult(imageedit, 3);
        }



        return super.onOptionsItemSelected(menuItem);
    }
    private void showDialog()
    {    AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        LayoutInflater inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.alertdialog_layout,null);
        // dialog.setTitle("Email Verification");
        //dialog .setMessage("A verfication mail has been sent to your registered email id.Please verify first to continue");
        dialog.setView(v);
        ((TextView)v.findViewById(R.id.gallery)).setOnClickListener(this);
        ((TextView)v.findViewById(R.id.camera)).setOnClickListener(this);


        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.dismiss();

            }
        });

        alert=dialog.create();
        alert.show();

    }
    @Override
    public void onClick(View v) {
        alert.dismiss();
        if(v.getId()==R.id.gallery)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_INTENT_CALLED);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //   super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == CAMERA_INTENT_CALLED &&resultCode==RESULT_OK) {

            try {


                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profimg.setImageBitmap(photo);
         //       check.setVisibility(View.VISIBLE);

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();

                Log.e("path", "----------------" + picturePath);
                // Image
                Bitmap bm = BitmapFactory.decodeFile(picturePath);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba = bao.toByteArray();
                encodedImage = Base64.encodeToString(ba,Base64.DEFAULT);

                Log.e("base64 profilepic", "-----" + encodedImage);



            }
            catch(Exception ex)
            {

            }
        }
        else if(requestCode==GALLERY_INTENT_CALLED&&resultCode==RESULT_OK)
        {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
           //     check.setVisibility(View.VISIBLE);

                if (selectedImage != null) {

                    profimg.setImageBitmap(selectedImage);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    byte[] byteArray;

                    byteArray = stream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Log.e("encoded profilepic",encodedImage);

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode==2){
           // Toast.makeText(getApplicationContext(),requestCode+"",Toast.LENGTH_LONG).show();
        retrievedata();
        }
        else if(requestCode==3){
             imageP=  data.getStringExtra("imgurl");
            if(imageP!=null) {
                Singleton.imageLoader.displayImage(imageP, profimg, Singleton.defaultOptions);

            }
        else
            {
                Singleton.imageLoader.displayImage(Singleton.pref.getString("uimage",""), profimg, Singleton.defaultOptions);

            }
        }


    }

    @Override
    public void onBackPressed() {
      //  Toast.makeText(getApplicationContext(),"called",Toast.LENGTH_LONG).show();
        Intent imagechange=new Intent();
        imagechange.putExtra("image",imageP);
        setResult(13,imagechange);
        finish();

    }
}


