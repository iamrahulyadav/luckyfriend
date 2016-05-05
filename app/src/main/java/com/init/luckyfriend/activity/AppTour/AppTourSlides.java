package com.init.luckyfriend.activity.AppTour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.Login;
import com.init.luckyfriend.activity.LoginActivity;
import com.init.luckyfriend.activity.MainActivity;
import com.init.luckyfriend.activity.Singleton;
import com.viewpagerindicator.CirclePageIndicator;

public class AppTourSlides extends ActionBarActivity {

    //  number of items in viewpager
    static final int NUM_ITEMS = 5;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    ViewPager mPager;
    SlidePagerAdapter mPagerAdapter;
    TextView appname;
    LinearLayout skip;
    Button joinnow;
    TextView messagetext,text;
    Typeface custom,customappname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_tour_slides);

        messagetext = (TextView) findViewById(R.id.messagetext);
        text = (TextView) findViewById(R.id.text);
        appname = (TextView) findViewById(R.id.appname);

        custom = Typeface.createFromAsset(getAssets(), "SF-UI-Display-Regular.ttf");
        messagetext.setTypeface(custom);
        text.setTypeface(custom);

        customappname = Typeface.createFromAsset(getAssets(), "NeoSansPro-Bold.ttf");
        appname.setTypeface(customappname);



		/* Instantiate a ViewPager and a PagerAdapter. */
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);


        skip = (LinearLayout) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(AppTourSlides.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

        joinnow = (Button) findViewById(R.id.joinnow);
        joinnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(AppTourSlides.this, Login.class);
                startActivity(login);
                finish();
            }
        });

        String uname= Singleton.pref.getString("uname",null);
        String uprofilepic=Singleton.pref.getString("profilepic",null);
        if(uname!=null)
        {
            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);
            finish();

        }
       /* else {
            Intent mainact = new Intent(getApplicationContext(), AppTourSlides.class);
            startActivity(mainact);
            finish();
        }
*/

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);


        NUM_PAGES = 5;


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        /*Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
*/
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
    /* PagerAdapter class */
    public class SlidePagerAdapter extends FragmentPagerAdapter {
        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
			/*
			 * IMPORTANT: This is the point. We create a RootFragment acting as
			 * a container for other fragments
			 */
            if (position == 0)
                return new AppTour1();
            else if (position == 1)
                return new AppTour2();
            else if (position == 2)
                return new AppTour3();
            else if (position == 3)
                return new AppTour4();
            else
                return new Apptour5();


        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}