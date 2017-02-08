package com.example.surine.materialdesigndemo.com.surine.UI_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.BaseActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by surine on 17-1-15.
 */

public class LoginQQ  extends BaseActivity {
    AppCompatButton appCompatButton;
    EditText qq_number;
    EditText passwd;
    TextView title;
    TextView link;
    TextView forget;
    String qq_number_string;
    String pswd_string;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_qq);

        //忘记密码初始化及监听
        forget= (TextView) findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"更多功能开发中",Snackbar.LENGTH_SHORT).setAction("我知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
            }
        });

        //创建帐户初始化及监听
        link= (TextView) findViewById(R.id.link);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(LoginQQ.this,CreateQQ.class);
                startActivity(intent);
            }
        });


        ////登录按钮初始化及监听
        appCompatButton= (AppCompatButton) findViewById(R.id.btn_login);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title= (TextView) findViewById(R.id.login_title);
                title.setText("QQ登录中……");
                qq_number= (EditText) findViewById(R.id.input_qq);
                passwd= (EditText) findViewById(R.id.input_password);
                qq_number_string=qq_number.getText().toString();
                pswd_string=passwd.getText().toString();
                if (qq_number_string.equals("") || pswd_string.equals("")) {
                    Toast.makeText(LoginQQ.this, "用户名密码为空", Toast.LENGTH_SHORT).show();
                    title.setText("登录QQ");
                } else {

                    //执行登陆
                    //emcallback是子线程
                    EMClient.getInstance().login(qq_number_string, pswd_string, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            //表示登陆成功
                            //更新本地数据
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Message mes = new Message();
                                    mes.what = 2;
                                     mHandler.sendEmptyMessage(mes.what);
                                }
                            }).start();
                        }

                        @Override
                        public void onError(int i, String s) {
                            //表示登录失败
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Message mes = new Message();
                                    mes.what = 1;
                                    mHandler.sendEmptyMessage(mes.what);
                                }
                            }).start();

                        }

                        @Override
                        public void onProgress(int i, String s) {
                            //进度更新
                        }
                    });
                }


            }
        });
    }

    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Snackbar.make(appCompatButton,"帐号或者密码错误",Snackbar.LENGTH_SHORT).setAction("清除输入", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            qq_number.setText("");
                            passwd.setText("");
                        }
                    }).show();
                    title.setText("登录QQ");
                    break;
                case 2:
                    Toast.makeText(LoginQQ.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(LoginQQ.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

}

