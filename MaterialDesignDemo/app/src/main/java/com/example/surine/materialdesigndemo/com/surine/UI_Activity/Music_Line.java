package com.example.surine.materialdesigndemo.com.surine.UI_Activity;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.BaseActivity;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

/**
 * Created by surine on 17-1-25.
 */

public class Music_Line extends BaseActivity {
    private FABRevealLayout fabRevealLayout;
    private TextView albumTitleText;
    private TextView artistNameText;
    private SeekBar songProgress;
    private TextView songTitleText;
    private ImageView prev;
    private ImageView stop;
    private ImageView next;
    private Toolbar toolbar;
    private ImageView album_cover_image;
    private RelativeLayout main;
    private FloatingActionButton fab_color;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_layout);

        //沉浸状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        findViews();
        configureFABReveal();
        listener();
        palette();
    }
    //提取图片颜色
    private void palette() {
        Bitmap bitmap = ((BitmapDrawable)album_cover_image.getDrawable()).getBitmap();
        Palette.generateAsync(bitmap,
                new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant =
                                palette.getLightVibrantSwatch();
                        Palette.Swatch vibrant2 =
                                palette.getDarkMutedSwatch();
                        if (vibrant != null) {
                            main.setBackgroundColor(
                                    vibrant.getRgb());
                        }
                    }
                });
    }

    //配置监听器
    private void listener() {
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabRevealLayout.revealMainView();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Music_Line.this,"pre",Toast.LENGTH_LONG).show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Music_Line.this,"next",Toast.LENGTH_LONG).show();
            }
        });
        fab_color.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //长按跳转事件
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(Music_Line.this, fab_color, fab_color.getTransitionName());
                    startActivity(new Intent(Music_Line.this, Music_ListActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(Music_Line.this, Music_ListActivity.class));
                }
                return true;
            }
        });
    }

    //初始化
    private void findViews() {
        fabRevealLayout = (FABRevealLayout) findViewById(R.id.fab_reveal_layout);
        albumTitleText = (TextView) findViewById(R.id.album_title_text);
        artistNameText = (TextView) findViewById(R.id.artist_name_text);
        songProgress = (SeekBar) findViewById(R.id.song_progress_bar);
        album_cover_image= (ImageView) findViewById(R.id.album_cover_image);
        main= (RelativeLayout) findViewById(R.id.main_layout);
        fab_color= (FloatingActionButton) findViewById(R.id.fab_color);
        styleSeekbar(songProgress);
        toolbar= (Toolbar) findViewById(R.id.music_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        songTitleText = (TextView) findViewById(R.id.song_title_text);
        prev = (ImageView) findViewById(R.id.previous);
        stop = (ImageView) findViewById(R.id.stop);
        next = (ImageView) findViewById(R.id.next);
    }

      //设置seekbar颜色
    private void styleSeekbar(SeekBar songProgress) {
        int color = getResources().getColor(R.color.colorAccent);
        songProgress.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        songProgress.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private void configureFABReveal() {
        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
                showMainViewItems();
            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                showSecondaryViewItems();
                prepareBackTransition(fabRevealLayout);
            }
        });
    }

    //菜单
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

    private void showMainViewItems() {
        scale(albumTitleText, 50);
        scale(artistNameText, 150);
    }

    private void showSecondaryViewItems() {
        scale(songProgress, 0);
        animateSeekBar(songProgress);
        scale(songTitleText, 100);
        scale(prev, 150);
        scale(stop, 100);
        scale(next, 200);
    }

    private void prepareBackTransition(final FABRevealLayout fabRevealLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //延时执行动作
                //fabRevealLayout.revealMainView();
            }
        }, 5000);
    }

    //设置缩放动画
    private void scale(View view, long delay){
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate()
                .scaleX(1)
                .scaleY(1)
                .setDuration(500)
                .setStartDelay(delay)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    //进度条回弹动画
    private void animateSeekBar(SeekBar seekBar){
        seekBar.setProgress(15);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(seekBar, "progress", 15, 0);
        progressAnimator.setDuration(400);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.start();
    }
}
