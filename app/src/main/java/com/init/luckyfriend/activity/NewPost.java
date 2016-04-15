package com.init.luckyfriend.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.init.luckyfriend.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class NewPost extends AppCompatActivity {

    CircleImageView imgchoose;
    ImageButton upload,cross;
    public static int GALLERY_INTENT_CALLED=200;
    String imgEncoded,description;
    EditText desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        imgchoose=(CircleImageView)findViewById(R.id.imgchoose);
        upload=(ImageButton)findViewById(R.id.upload);
        cross=(ImageButton)findViewById(R.id.cross);
        desc=(EditText)findViewById(R.id.adddesc);

        imgchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);

            }
        });

        description=desc.getText().toString();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

         if(requestCode==GALLERY_INTENT_CALLED&&resultCode==RESULT_OK)
        {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream =getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgchoose.setImageBitmap(selectedImage);

                if (selectedImage != null) {

                    imgchoose.setImageBitmap(selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    byte[] byteArray;

                    byteArray = stream.toByteArray();
                    imgEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Log.e("encoded", imgEncoded);

                }


            }
            catch (Exception e) {
                e.printStackTrace();
                Log.e("error",e.getMessage());
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_post, menu);
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
