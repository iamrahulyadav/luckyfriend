package com.init.luckyfriend.activity.PhotosFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.init.luckyfriend.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.init.luckyfriend.activity.DATA.PhotosDataBean;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Notification.AppConstant;
import com.init.luckyfriend.activity.Notification.MultipleRowModel;
import com.init.luckyfriend.activity.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhotosFragment extends Fragment {

    RecyclerView recyclerView;
    PhotoMultipleRowAdapter adapter;
    AlertDialog alert;
    public static int CAMERA_INTENT_CALLED = 100;
    public static int GALLERY_INTENT_CALLED = 200;
    Uri fileUri;
    String picturePath;
    String fileName;
     String encodedImage;
    Uri selectedImage;
    Bitmap photo;
    public static AlertDialog.Builder dialog;
    public static ArrayList<PhotosDataBean> items=new ArrayList<>();
    Bitmap result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPhotos();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);


        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        //fillAdapter();


        adapter=new PhotoMultipleRowAdapter(getActivity(),items,this,result);
        recyclerView.setAdapter(adapter);




        // Inflate the layout for this fragment
        return rootView;
    }

    private void getPhotos() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, getActivity().getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("photos details", response.toString());
                items.clear();
                try {

                    JSONObject jobj = new JSONObject(response.toString());
                    JSONArray jarray = jobj.getJSONArray("data");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        PhotosDataBean object = new PhotosDataBean();
                        object.setPerson_img_path(jo.getString("person_img_path"));
                        object.setType(3);
                        items.add(object);
                    }
                    PhotosDataBean object = new PhotosDataBean();
                     object.setType(4);
                    items.add(object);
                    adapter.notifyDataSetChanged();

                } catch (Exception ex) {
                    Log.e("error", ex.getMessage() + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               //showing snakebar here
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid",18+"");
                params.put("person_id", Singleton.pref.getString("person_id",""));

                return params;
            }


        };
        queue.add(sr);
    }

    public void showDialog() {
         dialog= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.alertdialog_layout,null);
        // dialog.setTitle("Email Verification");
        //dialog .setMessage("A verfication mail has been sent to your registered email id.Please verify first to continue");
        dialog.setView(v);
        ((TextView)v.findViewById(R.id.gallery)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    Toast.makeText(context,"gallery",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);

            }
        });
        ((TextView)v.findViewById(R.id.camera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(context,"camera",Toast.LENGTH_LONG).show();
                if (getActivity().getPackageManager().hasSystemFeature(
                        PackageManager.FEATURE_CAMERA)) {
                    // Open default camera
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                    // start the image capture Intent
                    startActivityForResult(intent, CAMERA_INTENT_CALLED);

                } else {
                    Toast.makeText(getContext(), "Camera not supported", Toast.LENGTH_LONG).show();
                }
            }


        });


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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);



        if( requestCode == CAMERA_INTENT_CALLED &&resultCode==getActivity().RESULT_OK) {

            try {
                selectedImage = data.getData();

                Bitmap photo = (Bitmap) data.getExtras().get("data");

                //.setImageBitmap(photo);

                alert.dismiss();
                //PhotoMultipleRowViewHolder.image.setImageBitmap(photo);
                // Cursor to get image uri to display


                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
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
                encodedImage = Base64.encodeToString(ba, Base64.DEFAULT);
                items.remove(items.size() - 1);
                PhotosDataBean object = new PhotosDataBean();
                object.setType(5);
                object.setPerson_img_path(encodedImage);
                items.add(object);

                PhotosDataBean object2 = new PhotosDataBean();
                object2.setType(4);
                //object2.setPerson_img_path(encodedImage);
                items.add(object2);


                adapter.notifyDataSetChanged();

                Log.e("base64", "-----" + encodedImage);


              }
            catch(Exception ex)
            {

            }
        }
        else if(requestCode==GALLERY_INTENT_CALLED&&resultCode==getActivity().RESULT_OK)
        {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream =getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
              //  profileimage.setImageBitmap(selectedImage);

                //alert.dismiss();

                alert.dismiss();
                //PhotoMultipleRowViewHolder.image.setImageBitmap(selectedImage);

                if (selectedImage != null) {

                //    profileimage.setImageBitmap(selectedImage);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    byte[] byteArray;

                    byteArray = stream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    items.remove(items.size() - 1);
                    PhotosDataBean object = new PhotosDataBean();
                    object.setType(5);
                    object.setPerson_img_path(encodedImage);
                    items.add(object);
//                    setBitmap(encodedImage);


                    PhotosDataBean object2 = new PhotosDataBean();
                    object2.setType(4);
                    items.add(object2);


                    adapter.notifyDataSetChanged();

                    Log.e("encoded",encodedImage);
                }


            }
            catch (Exception e) {
                e.printStackTrace();
                Log.e("error",e.getMessage());
            }
        }

    }

    private Bitmap setBitmap(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Log.e("bitmap",decodedByte+"");

        return decodedByte;
    }


}
