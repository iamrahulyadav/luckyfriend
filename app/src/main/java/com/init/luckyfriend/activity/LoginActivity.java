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
import android.view.Window;
import android.view.WindowManager;
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


public class LoginActivity extends AppCompatActivity {

    TextView appname,tagline;
    Typeface custom,customtagline,customfont;
    TextView login;
    TextView username,password;
    ProgressDialog prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.loginactivity);

        appname=(TextView)findViewById(R.id.appname);
        tagline=(TextView)findViewById(R.id.tagline);
        login=(TextView)findViewById(R.id.login);
        username=(TextView)findViewById(R.id.username);
        password=(TextView)findViewById(R.id.password);

        prog=new ProgressDialog(this);
        prog.setMessage("loading...");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString()==null){
                    username.setError("Please enter username or email");
                }

                if (!isValidPassword(password.getText().toString()))
                {
                    password.setError("Wrong password");
                    return;
                }

                login(username.getText().toString(),password.getText().toString());
                         Log.e("send data",username.getText().toString()+" "+password.getText().toString());
                           }
        });


        custom=Typeface.createFromAsset(getAssets(),"NeoSansPro-Regular.ttf");
        appname.setTypeface(custom);

        customtagline=Typeface.createFromAsset(getAssets(),"SF-UI-Display-Regular.ttf");
        tagline.setTypeface(customtagline);

        username.setTypeface(customtagline);
        password.setTypeface(customtagline);
          }

    private void login(final String username_email,final String pass) {

        prog.show();
//        String url ="http://192.168.0.7/test.php";


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //prog.setVisibility(View.GONE);
                prog.dismiss();
              Log.e("login", response.toString());
                try {
                    JSONObject jobj = new JSONObject(response.toString());
                    if(jobj.getBoolean("status"))
                    {
                        Intent login=new Intent(getApplicationContext(),MainActivity.class);
                        login.putExtra("typeLogin",5);


                      //save data on shared preference
                        SharedPreferences.Editor edit= Singleton.pref.edit();
                        edit.putString("uname",jobj.getString("uname"));
                        edit.putString("person_id",jobj.getString("person_id"));
                        edit.putString("user_email",jobj.getString("user_email"));
                        edit.putString("person_gender",jobj.getString("person_gender"));
                        edit.putString("person_country",jobj.getString("person_country"));
                        edit.putString("person_city",jobj.getString("person_location"));
                        edit.putString("profile_pic",jobj.getString("profilepic"));



                        edit.commit();


                        startActivity(login);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Wrong login details! ", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {

                    Log.e("json parsing error", ex.getMessage());
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
                params.put("rqid", 5+"");
                params.put("username_email", username_email);
                params.put("password", pass);


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
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }
}
