package com.init.luckyfriend.activity.ExtendedProfile;



import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;


import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.init.luckyfriend.activity.EditProfile.EditProfile;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FullExtendedProfile extends AppCompatActivity implements View.OnClickListener {
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private static final Integer[] IMAGES= {R.drawable.girlicon,R.drawable.female_icon,R.drawable.girlicon,R.drawable.iiiii};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    View rootView;
    TextView place,languagestext,username;
    ImageButton back,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullextendedprofile);


        place=(TextView)findViewById(R.id.place);
        languagestext=(TextView)findViewById(R.id.languages);
        username=(TextView)findViewById(R.id.username);
        back=(ImageButton)findViewById(R.id.back);
        message=(ImageButton)findViewById(R.id.message);

        back.setOnClickListener(this);
        message.setOnClickListener(this);

        init();
        String person_id = getIntent().getStringExtra("person_id");
        getDetails(person_id);



         }



    private void getDetails(final String person_id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("itemclick response", response.toString());
                // prog.dismiss();
                // items.clear();


                try {
                    JSONObject jobj = new JSONObject(response.toString());
                    String uname=jobj.getString("uname");
                    String country=jobj.getString("person_country");
                    String loc=jobj.getString("person_location");
                    String languages=jobj.getString("person_languages");
                    username.setText(uname);

                    place.setText(loc+","+country);
                    languagestext.setText(languages);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    }






        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response",error.getMessage()+"");
                //          prog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 32+"");
                params.put("person_id", person_id);


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

    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager)findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(this,ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);



        NUM_PAGES =IMAGES.length;



        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        /*Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
*/
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.message:
                Toast.makeText(getApplicationContext(),"message",Toast.LENGTH_LONG).show();
                break;
        }
    }
}


