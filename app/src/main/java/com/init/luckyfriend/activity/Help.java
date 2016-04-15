package com.init.luckyfriend.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.init.luckyfriend.R;


public class Help extends AppCompatActivity {

    Toolbar mToolbar;
    TextView toolbartitle,account,text1,text2,text3;
    ImageButton backbutton;

    Typeface customFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().hide();

        backbutton=(ImageButton)findViewById(R.id.back);
        account=(TextView)findViewById(R.id.account);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);

        customFont=Typeface.createFromAsset(getAssets(),"SF-UI-Display-Regular.ttf");
        account.setTypeface(customFont);
        text1.setTypeface(customFont);
        text2.setTypeface(customFont);
        text3.setTypeface(customFont);



        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);




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
