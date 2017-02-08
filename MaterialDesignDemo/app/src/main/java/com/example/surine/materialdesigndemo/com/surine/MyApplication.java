package com.example.surine.materialdesigndemo.com.surine;

import android.app.Application;

import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.MessageUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by surine on 2017/2/4.
 * init work
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //配置对象,设置sdk属性
        EMOptions emOptions = new EMOptions();
        //自动登录
        emOptions.setAutoLogin(false);
        //初始化sdk
        EMClient.getInstance().init(this,emOptions);

        //初始化消息管理
        //参照环信3.x DEMO设计
        MessageUtil.getInstance().init();
    }


}
