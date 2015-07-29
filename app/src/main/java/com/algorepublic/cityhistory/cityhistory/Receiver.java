package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by waqas on 7/28/15.
 */
public class Receiver extends ParsePushBroadcastReceiver {
    @Override
    protected void onPushOpen(Context context, Intent intent) {
        Intent i = new Intent(context, BaseActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
