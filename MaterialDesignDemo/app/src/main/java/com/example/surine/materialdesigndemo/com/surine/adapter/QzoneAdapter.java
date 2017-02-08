package com.example.surine.materialdesigndemo.com.surine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.UI_Activity.Qzone_info_activity;
import com.example.surine.materialdesigndemo.com.surine.info.Zone_info;

import java.util.List;

/**
 * Created by surine on 2017/2/5.
 */

public class QzoneAdapter extends RecyclerView.Adapter<QzoneAdapter.ViewHolder> {
    private List<Zone_info> mZone_infos;
    //上下文
    private Context mcontext;
    public QzoneAdapter(List<Zone_info> zone_infos) {
        mZone_infos = zone_infos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mcontext==null){
            mcontext=parent.getContext();
        }
        //装载holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zone_info,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        //图片
        holder.shuoshuo_list_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_method(holder);
            }
        });

        //内容
        holder.shuoshuo_list_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 intent_method(holder);
            }
        });

        //头像
        holder.shuoshuo_list_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext,"这是头像",Toast.LENGTH_SHORT).show();
            }
        });

        //赞
        holder.shuoshuo_list_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position =holder.getAdapterPosition();
                Zone_info zone_info = mZone_infos.get(position);
                if(zone_info.isLove()){
                    Glide.with(mcontext).load(R.drawable.love).into(holder.shuoshuo_list_love);
                    zone_info.setLove(false);
                }
                else{
                    Glide.with(mcontext).load(R.drawable.love_ok).into(holder.shuoshuo_list_love);
                   zone_info.setLove(true);
                }
            }
        });

        //分享
        holder.shuoshuo_list_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext,"这是分享",Toast.LENGTH_SHORT).show();
            }
        });
        //评论
        holder.shuoshuo_list_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext,"这是评论",Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    private void intent_method(ViewHolder holder) {
        int position =holder.getAdapterPosition();
        Zone_info zone_info = mZone_infos.get(position);
        Intent intent = new Intent(mcontext, Qzone_info_activity.class);
        intent.putExtra(Qzone_info_activity.NAME,zone_info.getFriend_name());
        intent.putExtra(Qzone_info_activity.PICTURE,zone_info.getPictureId());
        intent.putExtra(Qzone_info_activity.CONTENT,zone_info.getContent());
        mcontext.startActivity(intent);
    }


    //绑定holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Zone_info zone_info = mZone_infos.get(position);
        Glide.with(mcontext).load(zone_info.getPictureId()).into(holder.shuoshuo_list_picture);
        holder.shuoshuo_list_content.setText(zone_info.getContent());
        Glide.with(mcontext).load(zone_info.getQzone_head()).into(holder.shuoshuo_list_head);
        holder.shuoshuo_list_name.setText(zone_info.getFriend_name());
        holder.shuoshuo_list_time.setText(zone_info.getSpeak_time());
        if(zone_info.isLove()){
            Glide.with(mcontext).load(R.drawable.love_ok).into(holder.shuoshuo_list_love);
        }
        else{
            Glide.with(mcontext).load(R.drawable.love).into(holder.shuoshuo_list_love);

        }
            }

    @Override
    public int getItemCount() {
        return mZone_infos.size();
    }


    //配置viewholder
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView shuoshuo_list_picture;
        TextView shuoshuo_list_content;
        ImageView shuoshuo_list_head;
        TextView shuoshuo_list_name;
        TextView shuoshuo_list_time;
        ImageView shuoshuo_list_love;
        ImageView shuoshuo_list_share;
        ImageView shuoshuo_list_speak;
         View qzone_view;
        //实例化
        public ViewHolder(View view) {
            super(view);
            qzone_view=view;
            shuoshuo_list_picture= (ImageView) view.findViewById(R.id.qzone_list_image);
            shuoshuo_list_content= (TextView) view.findViewById(R.id.qzone_list_content);
            shuoshuo_list_head= (ImageView) view.findViewById(R.id.shuoshuo_list_head);
            shuoshuo_list_name= (TextView) view.findViewById(R.id.speak_name);
            shuoshuo_list_time= (TextView) view.findViewById(R.id.shuoshuo_list_time);
            shuoshuo_list_love= (ImageView) view.findViewById(R.id.list_love);
            shuoshuo_list_speak= (ImageView) view.findViewById(R.id.list_speak);
           shuoshuo_list_share= (ImageView) view.findViewById(R.id.list_share);
        }
    }
}
