package com.init.luckyfriend.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.init.luckyfriend.R;


public class Filter extends AppCompatActivity {

    Button woman,man;
    RangeBar rangebar;
    ImageButton close;
    TextView search;
    String searchfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

            woman=(Button)findViewById(R.id.m2);
            man=(Button)findViewById(R.id.m1);
            rangebar=(RangeBar)findViewById(R.id.rangebar1);
            close=(ImageButton)findViewById(R.id.close);
            search=(TextView)findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minage=rangebar.getLeftPinValue();
                String maxage=rangebar.getRightPinValue();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    public void switchbutton(View v)
    {

        switch(v.getId())
        {
            case R.id.m1:
                man.setTextColor(Color.WHITE);
                man.setBackgroundColor(Color.parseColor("#687484"));
                woman.setTextColor(Color.BLACK);
                woman.setBackgroundColor(Color.WHITE);

                searchfor="man";

                break;
            case R.id.m2:
                woman.setTextColor(Color.WHITE);
                woman.setBackgroundColor(Color.parseColor("#687484"));
                man.setTextColor(Color.BLACK);
                man.setBackgroundColor(Color.WHITE);

                searchfor="woman";
                 break;
        }




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
