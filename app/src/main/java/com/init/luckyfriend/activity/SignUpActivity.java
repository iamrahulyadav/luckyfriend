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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.init.luckyfriend.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {


    TextView appname, tagline, inputUsername, inputEmail, inputPassword, inputRepassword;
    Typeface custom, customtagline;
    Button signup;
    ProgressDialog prog;
    SharedPreferences.Editor edit;
    Integer type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        type=getIntent().getIntExtra("typeLogin",0);
        Log.e("typeSignup",type+"");

//        getSupportActionBar().hide();
        appname = (TextView) findViewById(R.id.appname);
        tagline = (TextView) findViewById(R.id.tagline);
        inputUsername = (TextView) findViewById(R.id.inputUsername);
        inputEmail = (TextView) findViewById(R.id.inputEmail);
        inputPassword = (TextView) findViewById(R.id.inputPassword);
        inputRepassword = (TextView) findViewById(R.id.inputRepassword);
        signup = (Button) findViewById(R.id.submit);

        custom = Typeface.createFromAsset(getAssets(), "NeoSansPro-Regular.ttf");
        appname.setTypeface(custom);

        customtagline = Typeface.createFromAsset(getAssets(), "SF-UI-Display-Regular.ttf");
        tagline.setTypeface(customtagline);

        inputUsername.setTypeface(customtagline);
        inputRepassword.setTypeface(customtagline);
        inputEmail.setTypeface(customtagline);
        inputPassword.setTypeface(customtagline);

        prog = new ProgressDialog(this);
        prog.setMessage("Please wait");

        signup.setTypeface(customtagline);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputUsername.getText().toString().length() == 0) {
                    inputUsername.setError("First name is required!");
                    return;
                }
                    else if (inputEmail.getText().toString().length() == 0)
                {
                    inputEmail.setError("Please enter email");
                    return;
                }
                else if (!isValidEmail(inputEmail.getText().toString()))
                {
                    inputEmail.setError("Invalid email");
                    return;
                }
                else if (!isValidPassword(inputPassword.getText().toString()))
                {
                    inputPassword.setError("enter more than 6 character password");
                    return;
                }
                else if (!isValidRePassword(inputRepassword.getText().toString()))
                {
                    inputRepassword.setError("Password doesnot match");
                    return;
                }

                // senddata();
                signup();


            }
        });

    }

    public void signup() {
        prog.show();
        //    prog.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //prog.setVisibility(View.GONE);
                prog.dismiss();
                Log.e("follower", response.toString());
                try {
                    JSONObject jobj = new JSONObject(response.toString());
                    if(jobj.getBoolean("status"))
                    {
                        SharedPreferences.Editor edit = Singleton.pref.edit();
                        edit.putString("uname", jobj.getString("uname"));
                        edit.putString("person_id",jobj.getString("person_id"));
                        edit.commit();

                         Intent afterLogin=new Intent(getApplicationContext(),FillUserMandatoryFields.class);
                         afterLogin.putExtra("typeLogin",4);
                        startActivity(afterLogin);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Unable to register now! ",Toast.LENGTH_LONG).show();
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
                params.put("rqid", "2");
                params.put("uname", inputUsername.getText().toString());
                params.put("uemail", inputEmail.getText().toString());
                params.put("upass", inputPassword.getText().toString());


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

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    private boolean isValidRePassword(String repass) {
        if (repass != null && repass.equals(inputPassword.getText().toString())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
