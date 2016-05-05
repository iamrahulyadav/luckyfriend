package com.init.luckyfriend.activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by user on 3/14/2016.
 */
public class Singleton extends Application {


    public static DisplayImageOptions defaultOptions;
    public static ImageLoader imageLoader;
    public static SharedPreferences pref;

    @Override
    public void onCreate() {
        super.onCreate();
       // TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        //Fabric.with(this, new Twitter(authConfig));

        pref=getSharedPreferences("luckylogin", Context.MODE_PRIVATE);

        // UNIVERSAL IMAGE LOADER SETUP
         defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();

        imageLoader=ImageLoader.getInstance();
        imageLoader.init(config);
    }
}


























