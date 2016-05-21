package com.init.luckyfriend.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.init.luckyfriend.R;

public class Boost extends AppCompatActivity {

    TextView text1,text2,text3,payment1,payment2,payment3,main,main1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boost);


        main=(TextView)findViewById(R.id.main);
        main1=(TextView)findViewById(R.id.main1);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);
        payment1=(TextView)findViewById(R.id.payment1);
        payment2=(TextView)findViewById(R.id.payment2);
        payment3=(TextView)findViewById(R.id.payment3);


        Typeface customtext=Typeface.createFromAsset(getAssets(),"SF-UI-Display-Regular.ttf");
        text1.setTypeface(customtext);
        text2.setTypeface(customtext);
        text3.setTypeface(customtext);


        Typeface custompayment=Typeface.createFromAsset(getAssets(), "SF-UI-Display-Light.otf");
        payment1.setTypeface(custompayment);
        payment2.setTypeface(custompayment);
        payment3.setTypeface(custompayment);

        Typeface custommain=Typeface.createFromAsset(getAssets(), "SF-UI-Display-Heavy.otf");
        main.setTypeface(custommain);
        main1.setTypeface(custommain);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_boost, menu);
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
