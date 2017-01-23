package com.ykk.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.UnicodeSetSpanner;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private IntentFilter intentFilter;
    private  NetWorkChange netWorkChange;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button_Id);
        button.setOnClickListener(this);
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChange=new NetWorkChange();
        registerReceiver(netWorkChange,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //动态注册的广播接收器一定都要取消注册。
        unregisterReceiver(netWorkChange);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent("com.example.broadcasttest.MY_BROADCAST");
        //发送自定义的标准广播
        sendBroadcast(intent);
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
