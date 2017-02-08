package com.example.surine.materialdesigndemo.com.surine.UI_Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by surine on 2017/2/5.
 */
public class CreateQQ extends BaseActivity {
    private static final int MESS = 1;
    private static final int ERROR = 2;
    private Button create_button;
    private EditText create_qq;
    private EditText create_pswd;
    private EditText repet_pswd;
    String qq_string ;
    String qq_pswd_string ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        init();   //初始化
        listener();  //监听器
    }

    private void listener() {
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                 qq_string = create_qq.getText().toString();
                 qq_pswd_string = create_pswd.getText().toString();
                 final String repet_string = repet_pswd.getText().toString();
           if(!qq_pswd_string.equals("")||!qq_string.equals("")||!repet_string.equals("")) {
               if (!qq_pswd_string.equals(repet_string)) {
                   Snackbar.make(view, "两次输入密码不一致", Snackbar.LENGTH_SHORT).setAction("清除密码", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           create_pswd.setText("");
                           repet_pswd.setText("");
                       }
                   }).show();
               }
               else {
                   //注册是一个耗时操作，（子线程操作）
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               EMClient.getInstance().createAccount(qq_string,qq_pswd_string);//同步方法
                               mHandler.sendEmptyMessage(MESS);
                           } catch (HyphenateException e) {
                               e.printStackTrace();
                               mHandler.sendEmptyMessage(ERROR);
                           }
                           //同步注册方法
                       }
                   }).start();

               }
            }
                else{
               Snackbar.make(view, "存在空输入", Snackbar.LENGTH_SHORT).setAction("我知道了", new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                   }
               }).show();
           }
            }
        });
    }

    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESS:
                    Toast.makeText(CreateQQ.this, "注册成功", Toast.LENGTH_SHORT).show();
                   finish();
                    break;
                case ERROR:
                    Snackbar.make(create_button, "注册失败", Snackbar.LENGTH_SHORT).setAction("清除数据", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            create_pswd.setText("");
                            repet_pswd.setText("");
                            create_qq.setText("");
                        }
                    }).show();
                    break;
            }
        }
    };

    private void init() {
        create_button= (Button) findViewById(R.id.btn_create);
        create_qq= (EditText) findViewById(R.id.create_qq);
        create_pswd= (EditText) findViewById(R.id.create_password);
        repet_pswd= (EditText) findViewById(R.id.repet_password);
    }
}
