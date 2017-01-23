package com.ykk.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Dell-home on 2017/1/23.
 */
//在应用程序中发出的广播，其他应用程序也可以收到，有需要时可以阻止其传播。
public class MyOtherBroadCasrReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"other MyBroadcastReceiver",Toast.LENGTH_SHORT).show();
    }
}
