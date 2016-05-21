package com.init.luckyfriend.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.appyvet.rangebar.RangeBar;
import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.HomeFragment.HomeFragment;
import com.init.luckyfriend.activity.Notification.MultipleRowModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Filter extends AppCompatActivity implements View.OnClickListener {

    Button woman,man;
    RangeBar rangebar,rangebar2;
    ImageButton close;
    ImageView nexticon,nexticon1,nexticon2,nexticon3;
    TextView search,country,countryval,state,stateval,advanced,statusselect,statusval,mothertongueselect,mothertongueval;
    String searchfor="female";
    String selectedCountry,selectedState,selectedStatus,selectedMothertongue;
    String agebetween;
    ProgressDialog searching;
    LinearLayout heightlayout;
    View heightview;
    FrameLayout statusLayout,mothertonguelayout;
    boolean advancedSearch=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

            woman=(Button)findViewById(R.id.m2);
            man=(Button)findViewById(R.id.m1);
            rangebar=(RangeBar)findViewById(R.id.rangebar1);
            rangebar2=(RangeBar)findViewById(R.id.rangebar2);
            close=(ImageButton)findViewById(R.id.close);
            search=(TextView)findViewById(R.id.search);
            country=(TextView)findViewById(R.id.countryselect);
            countryval=(TextView)findViewById(R.id.countryval);
            nexticon=(ImageView)findViewById(R.id.nexticon);

        state=(TextView)findViewById(R.id.stateselect);
        stateval=(TextView)findViewById(R.id.stateval);
        nexticon1=(ImageView)findViewById(R.id.nexticon1);

        heightlayout=(LinearLayout)findViewById(R.id.heightlayout);
        heightview=(View)findViewById(R.id.heightview);
        statusLayout=(FrameLayout)findViewById(R.id.statuslayout);
        mothertonguelayout=(FrameLayout)findViewById(R.id.mothertonguelayout);

        statusselect=(TextView)findViewById(R.id.statuselect);
        statusval=(TextView)findViewById(R.id.statusval);
        nexticon2=(ImageView)findViewById(R.id.next);

        mothertongueselect=(TextView)findViewById(R.id.motherTongueselect);
        mothertongueval=(TextView)findViewById(R.id.mothertongueval);
        nexticon3=(ImageView)findViewById(R.id.nexticon3);

        advanced=(TextView)findViewById(R.id.advanced);
        advanced.setOnClickListener(this);

        searching=new ProgressDialog(this);
        searching.setMessage("Searching...");

        country.setOnClickListener(this);
        state.setOnClickListener(this);
        statusselect.setOnClickListener(this);
        mothertongueselect.setOnClickListener(this);

        search.setOnClickListener(this);


        //Log.e("values",rangebar.getLeftPinValue()+rangebar.getRightPinValue());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent finish=new Intent();
                setResult(10, finish);
                finish();
            }
        });


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    public void switchbutton(View v)
    {

        switch(v.getId())
        {
            case R.id.m1:
                man.setTextColor(Color.WHITE);
                man.setBackgroundColor(Color.parseColor("#687484"));
                woman.setTextColor(Color.BLACK);
                woman.setBackgroundColor(Color.WHITE);

                searchfor="male";

                break;
            case R.id.m2:
                woman.setTextColor(Color.WHITE);
                woman.setBackgroundColor( Color.parseColor("#687484"));
                man.setTextColor(Color.BLACK);
                man.setBackgroundColor(Color.WHITE);

                searchfor="female";
                 break;
        }




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
            case R.id.countryselect:
                Intent country=new Intent(getApplicationContext(),CountryFilter.class);
                startActivityForResult(country, 4);
                break;

            case R.id.stateselect:
                if(selectedCountry==null){
                    Toast.makeText(getApplicationContext(), "Select country first", Toast.LENGTH_LONG).show();
                }

                else {
                    Intent state = new Intent(getApplicationContext(), StateFilter.class);
                    state.putExtra("countryselect", selectedCountry);
                    startActivityForResult(state, 5);
                }
                break;

            case R.id.search:
                if(advancedSearch==false){
                String maxValue=rangebar.getRightPinValue();
                String minValue=rangebar.getLeftPinValue();

                if(selectedCountry==null){
                    Toast.makeText(getApplicationContext(),"Select country",Toast.LENGTH_LONG).show();
                    return;
                }
                 else if(selectedState==null){
                    Toast.makeText(getApplicationContext(),"Select state",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                    search(maxValue,minValue);

                }
                else

                {
                    //Toast.makeText(getApplicationContext(),"Advanced Search",Toast.LENGTH_LONG).show();
                    String maxValue=rangebar.getRightPinValue();
                    String minValue=rangebar.getLeftPinValue();
                    String heightmaxValue=rangebar2.getRightPinValue();
                    String heightminValue=rangebar2.getLeftPinValue();


                    if(selectedStatus==null){
                        Toast.makeText(getApplicationContext(),"Select status",Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(selectedMothertongue==null){
                        Toast.makeText(getApplicationContext(),"Select mother tongue",Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(selectedCountry==null){
                        Toast.makeText(getApplicationContext(),"Select country",Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(selectedState==null){
                        Toast.makeText(getApplicationContext(),"Select state",Toast.LENGTH_LONG).show();
                        return;
                    }
                        else
                    {
                        advancedSearch(maxValue,minValue,heightminValue,heightmaxValue);
                    }
                }
                break;

            case R.id.advanced:
                heightlayout.setVisibility(View.VISIBLE);
                heightview.setVisibility(View.VISIBLE);
                statusLayout.setVisibility(View.VISIBLE);
                mothertonguelayout.setVisibility(View.VISIBLE);
                advancedSearch=true;

                break;

            case R.id.statuselect:
                    Intent status = new Intent(getApplicationContext(), MaritalFilter.class);
                    //state.putExtra("countryselect", selectedCountry);
                    startActivityForResult(status, 6);

                break;

            case R.id.motherTongueselect:
                    Intent state = new Intent(getApplicationContext(), MothertongueFilter.class);
                    //state.putExtra("countryselect", selectedCountry);
                    startActivityForResult(state, 7);
                break;



        }
    }

    private void advancedSearch(final String maxValue,final String minValue,final String heightminValue,final String heightmaxValue) {
        searching.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(" advanced searchdetails", response.toString());
                searching.dismiss();
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    if (jarray.length() == 0) {
                        // dataleft = false;
                        Toast.makeText(getApplicationContext(), "No data available according to your given data..", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("data", response);
                        setResult(14, intent);
                        finish();//finishing activity//  searching.dismiss();
                    }
                } catch (Exception ex) {
                    Log.e(" error...........", ex.getMessage() + "");
                    // Toast.makeText(getApplicationContext(), "No data available according to your given data..", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//showing snakebar here
                searching.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 37+ "");
                params.put("maxValue",maxValue);
                params.put("minValue",minValue);
                params.put("heightmaxValue",heightmaxValue);
                params.put("heightminValue",heightminValue);
                params.put("country",selectedCountry);
                params.put("maritalstatus",selectedStatus);
                params.put("mothertongue",selectedMothertongue);
                params.put("state",selectedState);
                params.put("gender",searchfor);
                params.put("person_id",Singleton.pref.getString("person_id",""));

                Log.e("values",maxValue+" "+minValue+" "+heightmaxValue+" "+heightminValue+" "+selectedCountry+" "+selectedStatus+" "+selectedMothertongue+" "+selectedState+" "+searchfor);

                return params;
            }


        };
        queue.add(sr);


    }

    private void search(final String maxValue,final String minValue) {
      searching.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("search  details", response.toString());
                    searching.dismiss();
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    if (jarray.length() == 0) {
                        // dataleft = false;
                        Toast.makeText(getApplicationContext(), "No data available according to your given data..", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("data", response);
                        setResult(10, intent);
                        finish();//finishing activity//  searching.dismiss();
                    }
                } catch (Exception ex) {
                    Log.e(" error...........", ex.getMessage() + "");
                   // Toast.makeText(getApplicationContext(), "No data available according to your given data..", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//showing snakebar here
                searching.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 34+ "");
                params.put("maxValue",maxValue);
                params.put("minValue",minValue);
                params.put("country",selectedCountry);
                params.put("state",selectedState);
                params.put("gender",searchfor);
                params.put("person_id",Singleton.pref.getString("person_id",""));

                return params;
            }


        };
        queue.add(sr);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4) {
            if(resultCode == RESULT_OK){
               selectedCountry=data.getStringExtra("countryval");
                countryval.setText(selectedCountry);
                nexticon.setVisibility(View.GONE);
            }
        }

        else if (requestCode == 5) {
            if(resultCode == RESULT_OK){
                selectedState=data.getStringExtra("stateval");
                stateval.setText(selectedState);
                nexticon1.setVisibility(View.GONE);
            }
        }

        else if (requestCode == 6) {
            if(resultCode == RESULT_OK){
                selectedStatus=data.getStringExtra("statusval");
                statusval.setText(selectedStatus);
                nexticon2.setVisibility(View.GONE);
            }
        }

        else if (requestCode == 7) {
            if(resultCode == RESULT_OK){
                selectedMothertongue=data.getStringExtra("mothertongueval");
                mothertongueval.setText(selectedMothertongue);
                nexticon3.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent finish=new Intent();
        setResult(10, finish);
        finish();


    }
}
