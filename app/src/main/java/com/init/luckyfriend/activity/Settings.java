package com.init.luckyfriend.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.init.luckyfriend.R;


public class Settings extends AppCompatActivity {

    TextView settings,text1,account,publicprofile,basicinfo,filter,notification,privacy;
    ImageButton back;
    Typeface customfont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

    back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        settings=(TextView)findViewById(R.id.settings);
        text1=(TextView)findViewById(R.id.text1);
        account=(TextView)findViewById(R.id.account);
        publicprofile=(TextView)findViewById(R.id.publicprofile);
        filter=(TextView)findViewById(R.id.filter);
        notification=(TextView)findViewById(R.id.notification);
        privacy=(TextView)findViewById(R.id.privacy);
        basicinfo=(TextView)findViewById(R.id.basicinfo);

        customfont=Typeface.createFromAsset(getAssets(),"SF-UI-Display-Regular.ttf");
        settings.setTypeface(customfont);
        text1.setTypeface(customfont);
        account.setTypeface(customfont);
        publicprofile.setTypeface(customfont);
        basicinfo.setTypeface(customfont);
        filter.setTypeface(customfont);
        privacy.setTypeface(customfont);
        notification.setTypeface(customfont);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
