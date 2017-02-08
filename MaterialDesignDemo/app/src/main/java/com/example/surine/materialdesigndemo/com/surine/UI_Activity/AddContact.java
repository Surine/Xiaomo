package com.example.surine.materialdesigndemo.com.surine.UI_Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by surine on 2017/2/6.
 */
public class AddContact extends BaseActivity {
    private EditText add_count;
    private EditText add_reason;
    private FloatingActionButton fab_add_contact;
    String add_number_string;
    String add_reason_string ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        add_count= (EditText) findViewById(R.id.get_number);
        add_reason= (EditText) findViewById(R.id.get_reason);
        fab_add_contact= (FloatingActionButton) findViewById(R.id.fab_add_contact);
        fab_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_number_string = add_count.getText().toString();
                 add_reason_string = add_reason.getText().toString();
                if (add_number_string != null && add_reason_string != null) {
                    if(EMClient.getInstance().getCurrentUser().equals(add_number_string)){
                        Toast.makeText(AddContact.this,"不能添加自己",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            EMClient.getInstance().contactManager().addContact(add_number_string, add_reason_string);
                            Toast.makeText(AddContact.this,"请求已发送",Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            Toast.makeText(AddContact.this,"操作失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }



}
