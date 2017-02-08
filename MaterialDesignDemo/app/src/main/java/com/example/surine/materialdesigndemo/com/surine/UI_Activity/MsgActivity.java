package com.example.surine.materialdesigndemo.com.surine.UI_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.BaseActivity;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.MessageUtil;
import com.example.surine.materialdesigndemo.com.surine.adapter.MsgAdapter;
import com.example.surine.materialdesigndemo.com.surine.info.Msg_info;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by surine on 2017/2/5.
 */
public class MsgActivity extends BaseActivity implements MessageUtil.MessageUtilListener {
    private static final int MESS = 1;
    private Button send;
    private EditText input;
    private RecyclerView mRecyclerView;
    private Toolbar tobar;
    private List<Msg_info> msgs = new ArrayList<>();
    private List<EMMessage> api_msg =new ArrayList<>();
    private MsgAdapter msgadapter;
    public static final String NAME = "msg_name";
    public static final String HEAD = "msg_head";
    public static final String TIME = "msg_time";
    public static final String CONTENT = "msg_content";


    String msg_name;
    int msg_head;
    String msg_time;
    String msg_content;
     String  content;
    String from;
    List<EMMessage> emmssage = null;
    private MessageUtil mMessageUtil;
    EMConversation emconversation = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_layout);
        mMessageUtil=MessageUtil.getInstance();
        mMessageUtil.setOnMessageUtilListener(this);
        initDate();  //初始化数据
        initview();   //初始化控件
        configlist();//配置列表
        listener(); //设置监听
    }

    private void listener() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
                String content = input.getText().toString();
                if(!"".equals(content)){
                    Msg_info msg_info  = new Msg_info(
                            content,  //内容
                            Msg_info.TYPE_SEND,   //类型
                            R.drawable.banana,   //头像
                            df.format(new Date() )) ;//时间
                    msgs.add(msg_info);
                    //通知插入
                    msgadapter.notifyItemInserted(msgs.size()-1);
                    //通知滚动
                    mRecyclerView.scrollToPosition(msgs.size()-1);
                    input.setText("");
                    //1.创建消息对象
                    EMMessage message = EMMessage.createTxtSendMessage(content,msg_name);
                    //2.发送消息
                    EMClient.getInstance().chatManager().sendMessage(message);
                    //3.储存消息
                    //EMClient.getInstance().chatManager().updateMessage(message);
                    message.setMessageStatusCallback(new EMCallBack(){
                        @Override
                        public void onSuccess() {
                            //消息发送成功
                        }

                        @Override
                        public void onError(int i, String s) {
                            //消息发送失败
                        }

                        @Override
                        public void onProgress(int i, String s) {
                            //消息发送中
                        }
                    });

                }
            }
        });
    }

    private void configlist() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        msgadapter=new MsgAdapter(msgs);
        mRecyclerView.setAdapter(msgadapter);
    }

    private void initDate() {
        Intent intent=getIntent();
        msg_name = intent.getStringExtra(NAME);
        msg_head = intent.getIntExtra(HEAD,0);
         msg_time = intent.getStringExtra(TIME);
         msg_content = intent.getStringExtra(CONTENT);

       emconversation =
               EMClient.getInstance().chatManager().getConversation(msg_name);
        Log.d("init1", "initDate: "+emconversation);
        if(emconversation==null||emmssage==null){
            Log.d("init1", "initDate: "+emmssage);
        }
          else  {
            emmssage = emconversation.getAllMessages();
               for (int i = 0; i < emmssage.size(); i++) {
                   DateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
                   Msg_info msg_info = new Msg_info(
                           emmssage.get(i).getBody().toString().
                                   substring(5, emmssage.get(i).getBody().toString().length() - 1),  //内容
                           Msg_info.TYPE_RECEIVED,   //类型
                           R.drawable.grape,   //头像
                           df.format(new Date()));//时间
                   msgs.add(msg_info);
               }
               //通知插入
               msgadapter.notifyItemInserted(msgs.size() - 1);
               //通知滚动
               mRecyclerView.scrollToPosition(msgs.size() - 1);

           }
        //设置toolbar标题
        setTitle(msg_name);
    }

    private String changeTime(String msg_time) {
       return null;
    }

    private void initview() {
        mRecyclerView= (RecyclerView) findViewById(R.id.msg_recycler_view);
        input= (EditText) findViewById(R.id.input_text);
        send= (Button) findViewById(R.id.send);
        tobar= (Toolbar) findViewById(R.id.msg_toolbar);
        setSupportActionBar(tobar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void newMessage(String app_from, String app_content, long msg_time) {
        Log.d("exitc", "newMessage:ddddddd ");
        if(app_from.equals(msg_name)){
             Msg_info msg_info = new Msg_info(
                     app_content,  //内容
                     Msg_info.TYPE_RECEIVED,   //类型
                     R.drawable.orange,   //头像
                     msg_time+"");//时间
             msgs.add(msg_info);

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
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESS:
                    msgadapter.notifyItemInserted(msgs.size() - 1);
                    //通知滚动
                    mRecyclerView.scrollToPosition(msgs.size() - 1);
                    break;
            }
        }
    };
}
