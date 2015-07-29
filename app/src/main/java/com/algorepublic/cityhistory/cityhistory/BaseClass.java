package com.algorepublic.cityhistory.cityhistory;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Switch;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;


/**
 * Created by waqas on 6/18/15.
 */
public class BaseClass extends Application {

    private AQuery aq;
    Switch button;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "kFHewfZX3rGoq5oSD4ln9iiF1WTRF8zfLxZcBsRl", "LbE7tDWocZlQDqTQMQdNq2Uf5gHBa4V1pWvB0Mp7");
        ParsePush.unsubscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully UnSubscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to UnSubscribe for push", e);
                }
            }
        });







        initImageLoader(getApplicationContext());

    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
