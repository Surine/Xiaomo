package com.example.surine.materialdesigndemo.com.surine.CollectorUtil;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

/**
 * Created by surine on 2017/2/7.
 * message管理类，参照：imooc hyman微信语音案例
 * 实现为单例，提供回调接口
 */

public class MessageUtil {

    private String app_from;   //消息来自谁？
    private long msg_time; //消息的时间？

    private static MessageUtil sMessageUtil;
    private MessageUtil(){

    }

    //接口:通知activity message准备完毕
    public interface MessageUtilListener{
        void newMessage(String app_from, String app_content, long msg_time);
    }

    public MessageUtilListener mMessageUtilListener;
    //回调监听器
    public void setOnMessageUtilListener(MessageUtilListener listener)
    {
        mMessageUtilListener = listener;
    }



    public  static MessageUtil getInstance()
    {
        if (sMessageUtil == null)
        {
            synchronized (MessageUtil.class)
            {
                if (sMessageUtil == null)
                    sMessageUtil = new MessageUtil();
            }
        }
        return sMessageUtil;
    }

        public void init(){
            MessageListener();
        }


    private void MessageListener() {
        //创建自己的接收消息监听器

        EMMessageListener msgListener = new EMMessageListener(){
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                //获取接收到的消息,解析消息
                for(EMMessage node:list) {
                    //获取用户名
                    app_from = node.getFrom();
                    //获取消息时间
                    msg_time=node.getMsgTime();
                    //获取信息类型
                    EMMessage.Type type = node.getType();
                    switch (type){
                        case TXT:
                            EMTextMessageBody body = (EMTextMessageBody) node.getBody();
                            final String app_content = body.getMessage();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //此时已经获取到消息的from和content和时间
                                    if(mMessageUtilListener!=null){
                                        //新消息通知
                                        mMessageUtilListener.newMessage(app_from,app_content,msg_time);
                                    }
                                }
                            }).start();

                            break;
                        default:break;
                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

}
