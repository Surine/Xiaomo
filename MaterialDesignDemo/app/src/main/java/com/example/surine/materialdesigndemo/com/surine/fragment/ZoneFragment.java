package com.example.surine.materialdesigndemo.com.surine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.adapter.QzoneAdapter;
import com.example.surine.materialdesigndemo.com.surine.info.Zone_info;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by surine on 2017/2/5.
 */
public class ZoneFragment  extends android.support.v4.app.Fragment  {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private QzoneAdapter zone_adapter;
    private List<Zone_info> zones = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    public static ZoneFragment newInstance(int page) {
        Bundle args;
        args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ZoneFragment pageFragment = new ZoneFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qzone_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.qzone_list);
        init();
//        //实例化对象
//        //创建布局管理
        LinearLayoutManager lin = new LinearLayoutManager(getContext());
        //设置布局管理
        recyclerView.setLayoutManager(lin);
//        //创建适配器
        zone_adapter=new QzoneAdapter(zones);
//        //设置适配器
        recyclerView.setAdapter(zone_adapter);

        //刷新功能实现
        swipeRefreshLayout= (SwipeRefreshLayout)view.findViewById(R.id.swipe_zone);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                                Zone_info zone_info = new Zone_info(
                                        R.drawable.orange,
                                        R.drawable.grape,
                                        "QQ小助手",
                                        df.format(new Date()),
                                        "\t开放注册是为了测试使用，正式环境中不推荐使用该方式注册环信账号；" +
                                                "授权注册的流程应该是您服务器通过环信提供的 REST API 注册，之后保存到您的服务器或返回给客户端。",
                                        true);
                             zones.add(zone_info);
                                zone_adapter.notifyDataSetChanged();   //通知更新
                                swipeRefreshLayout.setRefreshing(false);   //停止刷新
                            }
                        });
                    }
                }).start();

            }
        });
        return view;
    }

    private void init() {
        for (int i = 0; i < 2; i++) {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            Zone_info zone_info = new Zone_info(
                    R.drawable.xusong,
                    R.drawable.banana,
                    "苏凌",
                    df.format(new Date()),
                    "\t风住尘香花已尽，日晚倦梳头。物是人非事事休，欲语泪先流。" +
                            "闻说双溪春尚好，也拟泛轻舟。只恐双溪舴艋舟，载不动许多愁。 ",
                    true);
            zones.add(zone_info);
            Collections.reverse(zones);
        }
    }
}
