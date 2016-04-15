package com.init.luckyfriend.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.init.luckyfriend.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.Bind;

public class MatchesFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.country)
    Spinner country;
    @Bind(R.id.spinsexstaatus) Spinner spinsexstatus;
    @Bind(R.id.spinrelstatus) Spinner spinrelstatus;
    @Bind(R.id.spinlookingfor) Spinner spinlookingfor;
    @Bind(R.id.send_info)
    LinearLayout saveinfo;
     TextView datetime;
    private ProgressDialog pd;
    ImageView profileimage;
    private AlertDialog alert;
    public static int CAMERA_INTENT_CALLED=100;
    public static int GALLERY_INTENT_CALLED=200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);

        profileimage=(ImageView)rootView.findViewById(R.id.profile_image);
        datetime=(TextView)rootView.findViewById(R.id.date);
        //save information
        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "Date Picker");
            }
        });

        //profile image
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });

        // Inflate the layout for this fragment
        return rootView;

    }

    private void showDialog() {

        AlertDialog.Builder dialog= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == CAMERA_INTENT_CALLED &&resultCode==getActivity().RESULT_OK) {

            try {


                Bitmap photo = (Bitmap) data.getExtras().get("data");

                profileimage.setImageBitmap(photo);
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
                profileimage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    }