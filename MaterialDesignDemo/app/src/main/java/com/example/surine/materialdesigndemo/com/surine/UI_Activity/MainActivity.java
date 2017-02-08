package com.example.surine.materialdesigndemo.com.surine.UI_Activity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.ActivityCollector;
import com.example.surine.materialdesigndemo.com.surine.CollectorUtil.BaseActivity;
import com.example.surine.materialdesigndemo.com.surine.Service.MessageService;
import com.example.surine.materialdesigndemo.com.surine.adapter.ChatAdapter;
import com.example.surine.materialdesigndemo.com.surine.adapter.SimpleFragmentPagerAdapter;
import com.example.surine.materialdesigndemo.com.surine.fragment.ContactsFragment;
import com.example.surine.materialdesigndemo.com.surine.fragment.MessageFragment;
import com.example.surine.materialdesigndemo.com.surine.fragment.ZoneFragment;
import com.example.surine.materialdesigndemo.com.surine.info.Chat_info;
import com.hyphenate.chat.EMClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
     private DrawerLayout drawerLayout;
    private ChatAdapter chat_adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Chat_info> chats = new ArrayList<>();
    private List<Fragment> fragments =new ArrayList<>();
    private List<String> titles =new ArrayList<>();
    private int[] tabicon ={R.drawable.nav_mail,R.drawable.nav_friends,R.drawable.nav_location};
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private   int pager_Position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取toolbar实例
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置toolbar
        setSupportActionBar(toolbar);
        //获取drawlayout实例
        drawerLayout= (DrawerLayout) findViewById(R.id.draw_layout);
        //获取actionbar
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.music_line:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //延时执行动作
                                Intent intent =new Intent(MainActivity.this,Music_Line.class);
                                startActivity(intent);
                            }
                        }, 500);
                        break;
                    case R.id.exit:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //延时执行动作
                                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                                ab.setMessage("确定退出？");
                                ab.setTitle("退出程序");
                                ab.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //结束服务，结束所有activity
                                        Intent intent = new Intent(MainActivity.this,MessageService.class);
                                        stopService(intent);
                                        ActivityCollector.finishAll();
                                    }
                                });
                                ab.setPositiveButton("取消", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                ab.show();
                            }
                        }, 500);
                        break;
                    case R.id.logout:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //延时执行动作
                                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                               ab.setMessage("确定注销？");
                                ab.setTitle("注销帐号");
                                ab.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        EMClient.getInstance().logout(true);
                                        Intent intent =new Intent(MainActivity.this,LoginQQ.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                ab.setPositiveButton("取消", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                ab.show();
                            }
                        }, 500);
                        break;
                }
                //关闭抽屉
                drawerLayout.closeDrawers();
                return true;
            }
        });

        /*
        *Surine  2017.2.5
        * 制作滑动页面
        * 1.实例化
        * 2.添加fragments
        * 3.添加tab标题
        * 4.初始化适配器，添加适配器，关联viewpager与tab，给pager设置缓存
        * 5.添加tab图标 （可省略）
        * 6.添加view动作监听器
        *
        * 注意：设置缓存的原因
        * 在加载Tab-A时会实例化Tab-B中fragment，依次调用：onAttach、
        * onCreate、onCreateView、onActivityCreated、onStart和onResume。
        * 同样切换到Tab-B时也会初始化Tab-C中的fragment。（Viewpager预加载）
        * 但是fragment中的数据(如读取的服务器数据)没有相应清除，导致重复加载数据。
        *
        *
        * 注意：ps:我们在使用viewpager时会定义一个适配器adapter，其中实例化了一个fragment列表，
        * 所以在tab切换时fragment都是已经实例化好的，所以在切换标签页时是不会重新实例化fragment
        * 对象的，因而在fragment中定义的成员变量是不会被重置的。所以为列表初始化数据需要注意这个问题。
        *
        * 参考网址：https://my.oschina.net/buobao/blog/644699
        * */



        //实例化viewpager和tablayout
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


        //使用fragment 的list集合管理碎片
        fragments.add(MessageFragment.newInstance(2));
        fragments.add(ContactsFragment.newInstance(0));
        fragments.add(ZoneFragment.newInstance(1));



        //使用string的list集合来添加标题
        titles.add("消息");
        titles.add("联系人");
        titles.add("动态");

        //初始化适配器（传入参数：FragmentManager，碎片集合，标题）
        pagerAdapter = new SimpleFragmentPagerAdapter
                (getSupportFragmentManager(),fragments,titles);
        //设置viewpager适配器
        viewPager.setAdapter(pagerAdapter);

        //设置缓存（详见上方块状注释）
        viewPager.setOffscreenPageLimit(3);
        //关联viewpager
        tabLayout.setupWithViewPager(viewPager);

        //设置图标
        setIcon();

        //viewpager的监听器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //滚动监听器
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页卡选中监听器
            @Override
            public void onPageSelected(int position) {
               if(position==0)
               {

                   pager_Position=0;
               }
                else if (position==1)
               {
                   pager_Position=1;
               }
                else if(position==2)
               {
                   pager_Position=2;
               }
            }

            //滚动状态变化监听器
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        //设置fab的动作
        final FloatingActionButton fab= (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager_Position==0)
                Snackbar.make(v,"建立新聊天",Snackbar.LENGTH_SHORT).setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"撤销",Toast.LENGTH_LONG).show();
                    }
                }).show();
                else if(pager_Position==1)
                {
                   Intent intent =new Intent(MainActivity.this,AddContact.class);
                       ActivityOptions options = ActivityOptions.makeScaleUpAnimation(v, 0,
                               0, v.getWidth(), v.getHeight());
                       startActivity(intent, options.toBundle());

                   }
                else if(pager_Position==2)
                {
                    Snackbar.make(v,"发布说说",Snackbar.LENGTH_SHORT).setAction("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,"撤销",Toast.LENGTH_LONG).show();
                        }
                    }).show();
                }
            }
        });


        //设置actionbar的相关内容
        ActionBar actionBar =getSupportActionBar();
        if(actionBar!=null)
        {
            //设置功能
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }



    //设置图标
    private void setIcon() {
        //取得tablayout的tab选项设置对应的图标
        tabLayout.getTabAt(0).setIcon(tabicon[0]);
        tabLayout.getTabAt(1).setIcon(tabicon[1]);
        tabLayout.getTabAt(2).setIcon(tabicon[2]);;
    }


    @Override
    //按下返回不退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(false);
            return true;
             }
        return super.onKeyDown(keyCode, event);
    }

    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    //反射-展示icon
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if(menu!=null)
        {

            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try {
                    Log.d("slw",menu.getClass().getSimpleName());
                    Method m =menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu,true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    //listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                Toast.makeText(MainActivity.this,"清理聊天列表",Toast.LENGTH_LONG).show();
                break;
            case R.id.add:
                Toast.makeText(MainActivity.this,"添加好友",Toast.LENGTH_LONG).show();
                break;
            case R.id.group:
                Toast.makeText(MainActivity.this,"群组",Toast.LENGTH_LONG).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
            default:break;
        }
        return true;
    }
}
