package com.example.surine.materialdesigndemo.com.surine.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.MessageUtil;
import com.example.surine.materialdesigndemo.com.surine.UI_Activity.MainActivity;

/**
 * Created by surine on 2017/2/7.
 */

public class MessageService extends Service {

    //构造
    public MessageService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    //onCreate:创建服务时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("小陌APP")
                .setContentText("小陌正在运行中")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .build();
        startForeground(1,notification);
    }

    //onStartCommand：启动服务时用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    //onDestroy：销毁服务调用
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
