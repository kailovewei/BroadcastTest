package com.ykk.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.UnicodeSetSpanner;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private  NetWorkChange netWorkChange;
    private Button button;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private  Button local_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button_Id);
        local_button= (Button) findViewById(R.id.localButton_Id);
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        //发送本地广播的监听器
        local_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localReceiver=new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example.broadcasttest.MY_BROADCAST");
                //发送自定义的标准广播
                //sendBroadcast(intent);
                //发送自定义的有序广播,第二个参数是一个与权限相关的字符串。
                sendOrderedBroadcast(intent,null);//可以在androidManifest中设置优先级来阻止其继续传播。

            }
        });

        /*
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChange=new NetWorkChange();
        registerReceiver(netWorkChange,intentFilter);
        */
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //动态注册的广播接收器一定都要取消注册。
       // unregisterReceiver(netWorkChange);
        //下面是本地广播取消注册的代码
        localBroadcastManager.unregisterReceiver(localReceiver);
    }


    //发送与接收本地的广播
    class LocalReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"Received local broadcast",Toast.LENGTH_SHORT).show();

        }
    }

    //判断网络的连接变化，发送广播给用户。
    class NetWorkChange extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            //一个系统服务类，专门用于管理网络连接的。
            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable())
            {
                Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
