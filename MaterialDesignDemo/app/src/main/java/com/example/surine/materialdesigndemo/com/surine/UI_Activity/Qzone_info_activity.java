package com.example.surine.materialdesigndemo.com.surine.UI_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.BaseActivity;

/**
 * Created by surine on 17-1-15.
 */

public class Qzone_info_activity  extends BaseActivity {
    public static final String NAME = "shuoshuo_speaker";
    public static final String PICTURE = "speaker_picture";
    public static final String CONTENT = "shuoshuo_content";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qzone_info);

        Intent intent=getIntent();
        String shuoshuo_speaker = intent.getStringExtra(NAME);
        int speaker_head = intent.getIntExtra(PICTURE,0);
        String shuoshuo_content = intent.getStringExtra(CONTENT);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.speak_fab);
        //fab.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.qzonebar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.coll_bar);
        ImageView imageView = (ImageView) findViewById(R.id.qzone_image);
        TextView textView = (TextView) findViewById(R.id.content_text);
        setSupportActionBar(toolbar);  //设置toolbar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(shuoshuo_speaker);
        Glide.with(this).load(speaker_head).into(imageView);
        textView.setText(magic(shuoshuo_content));
    }

    private String magic(String shuoshuo_content) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<300;i++)
        {
            sb.append(shuoshuo_content);
        }
        return sb.toString();
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
}
