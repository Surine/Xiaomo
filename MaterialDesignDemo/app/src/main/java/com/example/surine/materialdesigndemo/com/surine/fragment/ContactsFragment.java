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
import com.example.surine.materialdesigndemo.com.surine.adapter.ContactAdapter;
import com.example.surine.materialdesigndemo.com.surine.info.Contact_info;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by surine on 17-1-16.
 */

public class ContactsFragment extends android.support.v4.app.Fragment {

    private ContactAdapter mContactAdapter;
    private List<Contact_info> mContact_infos =new ArrayList<>();
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static ContactsFragment newInstance(int page) {
        Bundle args;
        args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ContactsFragment pageFragment = new ContactsFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }



    //创建view
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_list, container, false);
        RecyclerView m_recyclerView;
        m_recyclerView= (RecyclerView) view.findViewById(R.id.contact_list);

        init();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        m_recyclerView.setLayoutManager(linearLayoutManager);
        mContactAdapter=new ContactAdapter(mContact_infos);
        m_recyclerView.setAdapter(mContactAdapter);
        //刷新功能实现
        swipeRefreshLayout= (SwipeRefreshLayout)view.findViewById(R.id.swipe_contact);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        //初始化自动更新
        swipeRefreshLayout.post(new Runnable() {
            @Override
             public void run() {
            swipeRefreshLayout.setRefreshing(true);
            requestFromNet();//请求网络的线程
        }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestFromNet();//请求网络的线程
            }
        });

        return view;
    }

    private void requestFromNet() {
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
                        //这里注意，获取好友列表是一个网络请求耗时操作，所以我们放到子线程操作
                        try {
                            List<String> usernames = EMClient.getInstance().
                                    contactManager().getAllContactsFromServer();
                            DateFormat df = new SimpleDateFormat("HH:mm:ss");
                            for(int i=0;i<usernames.size();i++) {
                                Contact_info ci = new Contact_info(usernames.get(i), R.drawable.banana, "[在线]", df.format(new Date()));
                                mContact_infos.add(ci);
                            }
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        mContactAdapter.notifyDataSetChanged();   //通知更新
                        swipeRefreshLayout.setRefreshing(false);   //停止刷新
                    }
                });
            }
        }).start();

    }

    private void init() {

        //这里注意，获取好友列表是一个网络请求耗时操作，所以我们放到子线程操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> usernames = EMClient.getInstance().
                            contactManager().getAllContactsFromServer();
                    DateFormat df = new SimpleDateFormat("HH:mm:ss");
                    for(int i=0;i<usernames.size();i++) {
                        Contact_info ci = new Contact_info(usernames.get(i), R.drawable.cherry, "[在线]", df.format(new Date()));
                        mContact_infos.add(ci);
                    }
                } catch (HyphenateException e) {

                    e.printStackTrace();
                }
            }
        }).start();



    }
}
