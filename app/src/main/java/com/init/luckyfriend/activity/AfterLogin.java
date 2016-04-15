package com.init.luckyfriend.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class AfterLogin extends AppCompatActivity {

    Button submit;
    TextView appname,tagline;
    Typeface custom,customtagline;
    Integer type;
    TextView location,maritalstatus,gender,looking_for,whatyoulookingfor;
    ProgressDialog prog;
    String person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_after_login);
        submit=(Button)findViewById(R.id.submit);

        appname=(TextView)findViewById(R.id.appname);
        tagline=(TextView)findViewById(R.id.tagline);
        custom=Typeface.createFromAsset(getAssets(),"NeoSansPro-Regular.ttf");
        appname.setTypeface(custom);

        customtagline=Typeface.createFromAsset(getAssets(),"SF-UI-Display-Regular.ttf");
        tagline.setTypeface(customtagline);

        prog=new ProgressDialog(this);
        prog.setMessage("Please wait....");

        location= (TextView)findViewById(R.id.location);
        maritalstatus=(TextView)findViewById(R.id.marital_status);
        gender=(TextView)findViewById(R.id.gender);
        whatyoulookingfor= (TextView)findViewById(R.id.whatlookingfor);
        looking_for=(TextView)findViewById(R.id.lookingfor);

        person_id=Singleton.pref.getString("person_id","");

        type=getIntent().getIntExtra("typeLogin",0);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(location.getText().toString()== null){

                    location.setError("Please fill the location");
                    return;
                }
               else if(maritalstatus.getText().toString()== null){
                    maritalstatus.setError("Please fill the marital status");
               return;
                }
              else if(gender.getText().toString()== null){
                    gender.setError("Please fill the sexuality");
                return;
                }
              else if(looking_for.getText().toString()== null){
                    looking_for.setError("Please fill the details");
                return;
                }
                else if(whatyoulookingfor.getText().toString()== null){
                    location.setError("Please fill the details");
                return;
                }

                submitDetails(location.getText().toString(),maritalstatus.getText().toString(),gender.getText().toString(),looking_for.getText().toString(),whatyoulookingfor.getText().toString());

                //Intent home=new Intent(getApplicationContext(),MainActivity.class);
                //home.putExtra("typeLogin",4);
                //startActivity(home);
            }
        });
    }

    private void submitDetails(final String loc,final String marital_status, final String persongender, final String looking_for, final String what_looking_for) {

        prog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, "http://108.178.10.78/androidios/test.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //prog.setVisibility(View.GONE);
                prog.dismiss();
                Log.e("afterlogin", response.toString());
                try {
                    JSONObject jobj = new JSONObject(response.toString());
                    //SharedPreferences.Editor edit = Singleton.pref.edit();
                    //edit.putString("person_location", jobj.getString(""));
                    //edit.putString("person_id",jobj.getString("person_id"));
                    //edit.commit();
                    Intent afterLogin=new Intent(getApplicationContext(),MainActivity.class);
                        afterLogin.putExtra("typeLogin",5);
                        startActivity(afterLogin);



                                   } catch (Exception ex) {

                    Log.e("error", ex.getMessage());
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("response",error.getMessage()+"");
                // prog.setVisibility(View.GONE);
                prog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", "6");
                params.put("loc",loc);
                params.put("marital_status", marital_status);
                params.put("gender", persongender);
                params.put("looking_for",looking_for);
                params.put("what_looking_for",what_looking_for);
                params.put("person_id",person_id);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_after_login, menu);
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
