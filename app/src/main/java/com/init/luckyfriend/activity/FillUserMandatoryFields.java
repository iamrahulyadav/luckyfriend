package com.init.luckyfriend.activity;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillUserMandatoryFields extends Activity implements View.OnClickListener {
    @Bind(R.id.country) Spinner country;
    @Bind(R.id.spinsexstaatus) Spinner spinsexstatus;
    @Bind(R.id.spinrelstatus) Spinner spinrelstatus;
    @Bind(R.id.spinlookingfor) Spinner spinlookingfor;
    @Bind(R.id.send_info)   LinearLayout saveinfo;
    @Bind(R.id.date) TextView datetime;
    @Bind(R.id.male)
    RadioButton male;
    @Bind(R.id.female) RadioButton female;
    @Bind(R.id.gender)
    RadioGroup gender;
    @Bind(R.id.location)
    EditText city;

    @Bind(R.id.whatyoulookingfor)
    EditText whatyoulookingfor;

    String ugender, ucountry,umaritalstatus, usex,lookingfor,useekingfor;
    private ProgressDialog pd;
    ImageView profileimage;

    String encodedImage;
    Uri fileUri;
    Uri selectedImage;
    String picturePath;
    String fileName;

    private AlertDialog alert;
    public static int CAMERA_INTENT_CALLED=100;
    public static int GALLERY_INTENT_CALLED=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fill_user_mandatory_fields);

        ButterKnife.bind(this);
        pd=new ProgressDialog(this);
        pd.setMessage("Please wait...");


        profileimage=(ImageView)findViewById(R.id.profile_image);

        fileName=new Date().getTime()+"luckyfriend"+".jpg";
        //save information
        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        //profile image
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });
 //save information
        saveinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              pd.show();
               //Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_LONG).show();

                ugender=((RadioButton)findViewById(gender.getCheckedRadioButtonId() )).getText().toString();
    //            useekingfor=((RadioButton)findViewById(seekingfor.getCheckedRadioButtonId() )).getText().toString();

                if(datetime.getText().toString()=="") {
                    datetime.setError("please fill date of birth");
                    return;
                }
                else if(ucountry==null){
                    //ucountry.setError("please fill date of birth");
                    Toast.makeText(getApplicationContext(),"please fill country",Toast.LENGTH_LONG).show();
                    return;

                }
                else if(city.getText().toString()==""){
                    Toast.makeText(getApplicationContext(),"Please fill city",Toast.LENGTH_LONG).show();
                    return;

                }
                else if(umaritalstatus==""){
                    Toast.makeText(getApplicationContext(),"Please fill marital status",Toast.LENGTH_LONG).show();

                    return;

                }

                else if(usex==""){
                    Toast.makeText(getApplicationContext(),"Please fill sexuality status",Toast.LENGTH_LONG).show();
                    return;

                }

                else if(lookingfor==""){
                    Toast.makeText(getApplicationContext(),"Please fill looking for",Toast.LENGTH_LONG).show();
                    return;

                }
                else if(whatyoulookingfor.getText().toString()==null) {
                    Toast.makeText(getApplicationContext(), "Please fill what you looking for", Toast.LENGTH_LONG).show();
                }




                savedata();

              }
        });
        //country spinner
        ArrayAdapter<String> adptSpnCategory = new ArrayAdapter<String>(this,R.layout.custom_spinner_item,getResources().getStringArray(R.array.country));
        adptSpnCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adptSpnCategory);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ucountry=country.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinsexstatus spinner
        ArrayAdapter<String> adptSpnCategory2 = new ArrayAdapter<String>(this,R.layout.custom_spinner_item,getResources().getStringArray(R.array.sexuality));
        adptSpnCategory2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinsexstatus.setAdapter(adptSpnCategory2);
        spinsexstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                usex=spinsexstatus.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinrelstatus spinner
        ArrayAdapter<String> adptSpnCategory3 = new ArrayAdapter<String>(this,R.layout.custom_spinner_item,getResources().getStringArray(R.array.relationshipstatus));
        adptSpnCategory3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinrelstatus.setAdapter(adptSpnCategory3);
        spinrelstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                umaritalstatus=spinrelstatus.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner looking for spinner
        ArrayAdapter<String> adptSpnCategory4 = new ArrayAdapter<String>(this,R.layout.custom_spinner_item,getResources().getStringArray(R.array.lookingfor));
        adptSpnCategory4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinlookingfor.setAdapter(adptSpnCategory4);
        spinlookingfor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                lookingfor=spinlookingfor.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void savedata() {
       // String url ="http://192.168.0.7/test.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //prog.setVisibility(View.GONE);
               Log.e("mandatory fields", response.toString());
                try {
                    JSONObject jobj = new JSONObject(response.toString());
                    if(jobj.getBoolean("status"))
                    {
                        Intent afterLogin=new Intent(getApplicationContext(),MainActivity.class);
                        afterLogin.putExtra("typeLogin",4);
                        startActivity(afterLogin);


                        SharedPreferences.Editor edit = Singleton.pref.edit();;
                        edit.putString("ugender",jobj.getString("person_gender"));
                        edit.putString("ucountry",jobj.getString("person_country"));
                        edit.putString("ucity",jobj.getString("person_city"));
                        edit.putString("uimage",jobj.getString("profile_pic"));

                        edit.commit();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                    }

                } catch (Exception ex) {

                    Log.e("error", ex.getMessage());
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                  Log.e("response",error.getMessage()+"");
               }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", "7");
                params.put("ugender",ugender);
                params.put("dob",datetime.getText().toString());
                params.put("usexualitystatus",spinsexstatus.getSelectedItem().toString());
                params.put("ucountry",country.getSelectedItem().toString());
                params.put("ucity",city.getText().toString());
                params.put("uwhatlookingfor",whatyoulookingfor.getText().toString());
                params.put("umaritalstatus",spinrelstatus.getSelectedItem().toString());
                params.put("ulookingfor",spinlookingfor.getSelectedItem().toString());
                params.put("person_id",Singleton.pref.getString("person_id", ""));
                params.put("encoded_image",encodedImage);
                params.put("fname",fileName);



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



    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < 16)
        {
            // Hide the status bar
             getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            // Hide the action bar
            //getSupportActionBar().hide();
        }
        else
        {
            // Hide the status bar
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            // Hide the action bar
            // getSupportActionBar().hide();
        }

    }
    private void showDialog()
    {
        AlertDialog.Builder dialog= new AlertDialog.Builder(this);
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
            /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_INTENT_CALLED);
        */

            if (getApplicationContext().getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA)) {
                // Open default camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                // start the image capture Intent
                startActivityForResult(intent, CAMERA_INTENT_CALLED);

            } else {
                Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);



        if( requestCode == CAMERA_INTENT_CALLED &&resultCode==RESULT_OK) {

            try {
                selectedImage = data.getData();

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profileimage.setImageBitmap(photo);

                // Cursor to get image uri to display

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

                Log.e("base64", "-----" + encodedImage);


                /*final File capturedImageFile = new File(getTempDirectoryPath(), System.currentTimeMillis() +".jpg");
                photo.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(capturedImageFile));
             String   url = capturedImageFile.getAbsolutePath().toString();
               */
            }
            catch(Exception ex)
            {

            }
        }
        else if(requestCode==GALLERY_INTENT_CALLED&&resultCode==RESULT_OK)
        {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream =getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profileimage.setImageBitmap(selectedImage);

                if (selectedImage != null) {

                    profileimage.setImageBitmap(selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    byte[] byteArray;

                    byteArray = stream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Log.e("encoded",encodedImage);

                }


            }
            catch (Exception e) {
                e.printStackTrace();
                Log.e("error",e.getMessage());
            }
        }

    }

    private String getTempDirectoryPath() {

        File cache = null;

// SD Card Mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cache = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/Android/data/" + getPackageName() + "/cache/");
        }
// Use internal storage
        else {
            cache = getCacheDir();
        }

// Create the cache directory if it doesn't exist
        cache.mkdirs();
        return cache.getAbsolutePath();

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

