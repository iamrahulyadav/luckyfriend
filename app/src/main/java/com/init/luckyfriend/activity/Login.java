package com.init.luckyfriend.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.HomeFragment.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity implements  View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView signup,terms,signupwith;
    TextView name;
    Typeface customfont,customname;
    RelativeLayout fbsignup,googlesignup,twitter;
    public  CallbackManager callbackManager;
    //Google Plus Connection Code
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private String username = "", userimage = "", useremail = "";

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final int TWITTER_SIGN_IN = 0;

    TextView textView;
    ProgressDialog show;
    String typeLogin;
    GoogleSignInAccount acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //buidNewGoogleApiClient();
        setContentView(R.layout.activity_login);
        FacebookSdk.setApplicationId("1670451923204029");


        signupwith=(TextView)findViewById(R.id.signupwith);
        terms=(TextView)findViewById(R.id.terms);
        signup=(TextView)findViewById(R.id.signup);
        name=(TextView)findViewById(R.id.name);
        fbsignup=(RelativeLayout)findViewById(R.id.fbsignup);
        googlesignup=(RelativeLayout)findViewById(R.id.google);
        //twitter=(RelativeLayout)findViewById(R.id.twitter);

        fbsignup.setOnClickListener(this);
        googlesignup.setOnClickListener(this);

        /*twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"clicked twitter",Toast.LENGTH_LONG).show();
                loginButton.performClick();
            }
        });
      */
        /*loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                session = result.data;

                String username = session.getUserName();
                Long  userid = session.getUserId();


                //textView.setText("Hi " + username);
                Log.e("Twitter Username","Hi " + username);
                getUserData();
            }

            @Override
            public void failure(TwitterException e) {
                Log.e("TwitterKit", "Login with Twitter failure", e);

            }
        });
*/



        // custom font on heading n tagline
        customname=Typeface.createFromAsset(getAssets(),"NeoSansPro-Bold.ttf");
        name.setTypeface(customname);

        customfont=Typeface.createFromAsset(getAssets(),"SF-UI-Display-Regular.ttf");
        signupwith.setTypeface(customfont);
        terms.setTypeface(customfont);
        signup.setTypeface(customfont);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googlesignup.setOnClickListener(this);

        // signup database
        signup.setOnClickListener(this);

        // facebook signup
        fbsignup.setOnClickListener(this);

        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.init.luckyfriend",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("hashkey", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
*/

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code


                                        Log.e("res", response.toString());
                                        String id = "", fname = "", lname = "", email = "", gender = "", country = "";
                                        try {

                                            id = object.getString("id");
                                            fname = object.getString("first_name");
                                            lname = object.getString("last_name");

                                            email = object.getString("email");
                                            gender = object.getString("gender");

                                            //Toast.makeText(getApplicationContext(),"value" +id,Toast.LENGTH_LONG).show();
                                            //creating json object
                                            JSONObject jobj = new JSONObject();
                                            jobj.put("fbid", id);
                                            jobj.put("fullname", fname + " " + lname);
                                            jobj.put("lname", lname);
                                            jobj.put("country", country);

                                            jobj.put("email", email);
                                            jobj.put("gender", gender);
                                            Log.e("data", jobj.toString());

                                            saveFBData(id, fname, lname, email, gender);

                                            SharedPreferences.Editor edit=Singleton.pref.edit();
                                            edit.putString("uname",fname+" "+lname);
                                            edit.putString("ugender",gender);
                                            edit.putString("ucountry",country);
                                            edit.putString("uimage","https://graph.facebook.com/"+id+"/picture?type=large");
                                            edit.commit();


                                            Intent   intent = new Intent(getApplicationContext(),MainActivity.class);
                                            //intent.putExtra("data", jobj.toString());

                                            intent.putExtra("typeLogin",1);
                                            startActivity(intent);
                                            // checkFbuser(id);

                                        } catch (Exception ex) {
                                            //    Log.d("Error",ex.getMessage());
                                        }


                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email,gender, birthday,locale,location{location},picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void saveFBData(final String id,final String fname,final String lname,final String email,final String gender) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST,getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(" fb response", response.toString());


                try {
                    JSONObject jobj = new JSONObject(response.toString());


                    if (jobj.getBoolean("status")) {
                        String person_id = jobj.getString("person_id");

                        SharedPreferences.Editor edit=Singleton.pref.edit();
                        edit.putString("person_id",person_id);
                        edit.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // prog.dismiss();
//            Log.e("error",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 25+"");
                params.put("fbid",id);
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("gender",gender);
                params.put("email",email);



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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else if(requestCode == TWITTER_SIGN_IN)
        {
         //   loginButton.onActivityResult(requestCode, resultCode, data);

        }
        else
        callbackManager.onActivityResult(requestCode, resultCode, data);


    }

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("tag", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
             acct = result.getSignInAccount();
             username = (acct == null ? "" : acct.getDisplayName());
            try {
                userimage = ((acct == null && acct.getPhotoUrl() == null) ? "" : acct.getPhotoUrl().toString());
                userimage = userimage.substring(0, userimage.length() - 2) + 200;

            }
            catch (Exception e) {

            }
            useremail = (acct == null ? "" : acct.getEmail());
            Log.e("gplus response", useremail + userimage + username);

          //  mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));

            senddatatoserver(username, useremail, userimage);
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    private void senddatatoserver(final String username,final String useremail,final String userimage) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST,getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response.toString());

                    try {
                        JSONObject jobj = new JSONObject(response.toString());

                        if(jobj.getBoolean("status")) {
                            String person_id = jobj.getString("person_id");
                            String name = jobj.getString("uname");


                            SharedPreferences.Editor edit = Singleton.pref.edit();
                            edit.putString("uname", name);
                            Toast.makeText(Login.this, name, Toast.LENGTH_SHORT).show();
                           // edit.putString("uemail", jobj.getString("uemail"));
                             edit.putString("uimage", userimage);
                            edit.putString("person_id", person_id);

                            edit.commit();


                            Intent main = new Intent(getApplicationContext(), MainActivity.class);
                            main.putExtra("typeLogin", 3);
                            startActivity(main);
                            finish();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Unable to register now! ",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // prog.dismiss();
//            Log.e("error",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 26+"");
                params.put("username",username);
                params.put("useremail",useremail);
                params.put("userimage",userimage);


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

    private void updateUI(boolean b) {

        if (b == true) {
            //findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            Intent main = new Intent(this, MainActivity.class);
            main.putExtra("typeLogin", 3);
            startActivity(main);
            finish();




        } else {
            Toast.makeText(getApplicationContext(),"Not signed in",Toast.LENGTH_LONG).show();

        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d("tag", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void hideProgressDialog() {
//        show.dismiss();
    }

    private void showProgressDialog() {

  //      show.show();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.fbsignup:
               Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
             LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email", "user_location","user_birthday","user_posts"));
                break;

            case R.id.google:
                Toast.makeText(getApplicationContext(),"clicked gplus ",Toast.LENGTH_LONG).show();
                signIn();
                break;

            case R.id.signup:
                Intent Signup=new Intent(getApplicationContext(),SignUpActivity.class);
                Signup.putExtra("typeLogin",4);

                startActivity(Signup);

        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        // Get user's information

    }



    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
       // Intent main=new Intent(getApplicationContext(),MainActivity.class);
       // startActivity(main);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}

