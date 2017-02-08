package com.example.surine.materialdesigndemo.com.surine.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.info.Msg_info;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by surine on 2017/2/5.
 * msg适配器
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg_info> mMsg_infos;
    Msg_info msg_info2;
    public MsgAdapter(List<Msg_info> msg_infos) {
        mMsg_infos = msg_infos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //控制显隐

            Msg_info msg_info = mMsg_infos.get(position);
            if(position>=1) {
                 msg_info2 = mMsg_infos.get(position - 1);
        }
        if(msg_info.getType()==Msg_info.TYPE_RECEIVED){
            holder.left_layout.setVisibility(View.VISIBLE);
            holder.right_layout.setVisibility(View.GONE);
            holder.left_msg.setText(msg_info.getContent());
            holder.left_head.setImageResource(msg_info.getHeadId());
            holder.left_time.setText(msg_info.getMsg_time());
            if(position>=1) {
                if (!compare_date(msg_info.
                        getMsg_time().toString(), msg_info2.getMsg_time().toString())) {
                    holder.left_time.setVisibility(View.GONE);
                }
                else {
                    holder.left_time.setVisibility(View.VISIBLE);
                }
            }
            else
         {
             holder.left_time.setVisibility(View.VISIBLE);
         }
        }else if(msg_info.getType()==Msg_info.TYPE_SEND){
            holder.left_layout.setVisibility(View.GONE);
            holder.right_layout.setVisibility(View.VISIBLE);
            holder.right_msg.setText(msg_info.getContent());
            holder.right_head.setImageResource(msg_info.getHeadId());
            holder.right_time.setText(msg_info.getMsg_time());
            if(position>=1) {
                if (!compare_date(msg_info.
                        getMsg_time().toString(), msg_info2.getMsg_time().toString())) {
                    holder.right_time.setVisibility(View.GONE);
                }
                else {
                    holder.right_time.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                holder.right_time.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return mMsg_infos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView left_msg;
        TextView right_msg;
        TextView left_time;
        TextView right_time;
        ImageView left_head;
        ImageView right_head;

        LinearLayout left_layout;
        LinearLayout right_layout;
        public ViewHolder(View view) {
            super(view);

            left_layout= (LinearLayout) view.findViewById(R.id.left_layout);
            right_layout= (LinearLayout) view.findViewById(R.id.right_layout);
            left_msg= (TextView) view.findViewById(R.id.left_msg);
            right_msg= (TextView) view.findViewById(R.id.right_msg);
            left_time= (TextView) view.findViewById(R.id.left_time);
            right_time= (TextView) view.findViewById(R.id.right_time);
            left_head= (ImageView) view.findViewById(R.id.left_head);
            right_head= (ImageView) view.findViewById(R.id.right_head);
        }
    }


    public static boolean compare_date(String date1, String date2)
    {
        Long chr = Long.valueOf(0);
        SimpleDateFormat dfs = new SimpleDateFormat("MM-dd HH:mm:ss");
        try {
            Date begin = dfs.parse(date1);
            Date end = dfs.parse(date2);
            chr = begin.getTime()-end.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        long day = chr / (24 * 60 * 60 * 1000);   //相差天数
//        long hour = (chr / (60 * 60 * 1000) - day * 24);   //相差小时
//        long min = ((chr / (60 * 1000)) - day * 24 * 60 - hour * 60);   //相差分钟
//        long ss = (chr / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);  //相差秒


        //设置大于55000毫秒，控制时间的显隐
        if(chr>55000){
            return true;
        }
        return false;
    }

}
