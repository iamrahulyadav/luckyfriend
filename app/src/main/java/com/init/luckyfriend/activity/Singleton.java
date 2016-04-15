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

        pref=getSharedPreferences("luckylogin", Context.MODE_PRIVATE);

        // UNIVERSAL IMAGE LOADER SETUP
         defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        imageLoader=ImageLoader.getInstance();
        imageLoader.init(config);
    }
}
