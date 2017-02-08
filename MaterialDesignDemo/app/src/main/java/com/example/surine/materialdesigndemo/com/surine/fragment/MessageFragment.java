package com.example.surine.materialdesigndemo.com.surine.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.MessageUtil;
import com.example.surine.materialdesigndemo.com.surine.UI_Activity.MainActivity;
import com.example.surine.materialdesigndemo.com.surine.adapter.ChatAdapter;
import com.example.surine.materialdesigndemo.com.surine.info.Chat_info;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by surine on 17-1-15.
 * 这是viewpager的第一页，一个碎片，我们将在这里面实现本页功能
 */

public class MessageFragment extends android.support.v4.app.Fragment implements MessageUtil.MessageUtilListener {

    public static final String ARG_PAGE = "ARG_PAGE";
    private static final int MESS = 1;
    private static int MQ = 0 ;
    private int mPage;
    private ChatAdapter chat_adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Chat_info> chats = new ArrayList<>();
    private MessageUtil messageutil;

    //创建MessageFragment实例
    public static MessageFragment newInstance(int page) {
        Bundle args=new Bundle();
        args.putInt(ARG_PAGE, page);
        MessageFragment pageFragment = new MessageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageutil=MessageUtil.getInstance();
        messageutil.setOnMessageUtilListener(this);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_layout, container, false);
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
//        //实例化对象
//        //创建布局管理
        GridLayoutManager lin = new GridLayoutManager(getActivity(),1);
        //设置布局管理
        recyclerView.setLayoutManager(lin);
//        //创建适配器
         chat_adapter=new ChatAdapter(chats);
//        //设置适配器
        recyclerView.setAdapter(chat_adapter);

         //刷新功能实现
        swipeRefreshLayout= (SwipeRefreshLayout)view.findViewById(R.id.swipe_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                       getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //刷新暂时没有什么作用
                                chat_adapter.notifyDataSetChanged();   //通知更新
                                swipeRefreshLayout.setRefreshing(false);   //停止刷新
                            }
                        });
                    }
                }).start();

            }
        });


        return view;
    }

    @Override
    public void newMessage(String app_from, String app_content, final long msg_time) {
        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i).getChat_name().equals(app_from)){
                MQ = 1;
                changInfo(i,app_content,msg_time);
                break;
            }
            else{
                MQ = 2;
            }
        }
        if (chats.size() == 0||MQ == 2) {
            Chat_info chat_info = new Chat_info(app_from,
                    R.drawable.grape, app_content, msg_time + "", "未读提醒");
            chats.add(chat_info);
          //  EMMessage message = EMMessage.createTxtSendMessage(app_content,EMClient.getInstance().getCurrentUser());
            //EMClient.getInstance().chatManager().updateMessage(message);
            thread();
        }
    }

    //修改信息
    private void changInfo(int i, String app_content, long msg_time) {
        chats.get(i).setChat_message(app_content);
        chats.get(i).setTime(String.valueOf(msg_time));
        //获取当前登陆用户名
       // EMMessage message = EMMessage.createTxtSendMessage(app_content,EMClient.getInstance().getCurrentUser());
        //EMClient.getInstance().chatManager().updateMessage(message);
        thread();
    }

    private void thread() {
        //通知主线程更新UI
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message =new Message();
                message.what = MESS;
                mHandler.sendEmptyMessage(message.what);
            }
        }).start();
    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESS:
                    chat_adapter.notifyDataSetChanged();  //主线程更新ui
                    notif();  //通知
                    break;
            }
        }
    };

    private void notif() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),0,intent,0);
        NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(getActivity())
                .setContentTitle("小陌APP")
                .setContentText("你有新消息啦！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)   //点击自动取消
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .build();
        notificationManager.notify(1,notification);
    }

}
