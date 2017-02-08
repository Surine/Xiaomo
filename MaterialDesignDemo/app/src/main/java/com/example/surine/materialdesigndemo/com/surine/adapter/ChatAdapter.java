package com.example.surine.materialdesigndemo.com.surine.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.surine.materialdesigndemo.com.surine.UI_Activity.MsgActivity;
import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.info.Chat_info;

import java.util.List;

/**
 * Created by surine on 17-1-14.
 * 聊天列表adapter
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    //上下文
    private Context mcontext;
    //列表
    private List<Chat_info> mchats;
    //Recyclerview的viewholder  （内部类）
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView head;
        TextView name;
        TextView message;
        TextView time;
        TextView pre;
          //声明变量
        public ViewHolder(View itemView) {
            super(itemView);
            //选项布局
            cardView= (CardView)itemView;
            head= (ImageView) itemView.findViewById(R.id.head_image);
            name= (TextView) itemView.findViewById(R.id.chat_name);
            message= (TextView) itemView.findViewById(R.id.message);
            time= (TextView) itemView.findViewById(R.id.time);
            pre= (TextView) itemView.findViewById(R.id.pre_read);
           //实例化子控件
        }
    }

    //创建viewholder
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mcontext==null){
            mcontext=parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_info,parent,false);
        //点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =holder.getAdapterPosition();
                Chat_info chat_info = mchats.get(position);
                Intent intent = new Intent(mcontext, MsgActivity.class);
                intent.putExtra(MsgActivity.NAME,chat_info.getChat_name());
                intent.putExtra(MsgActivity.HEAD,chat_info.getHead_id());
                intent.putExtra(MsgActivity.TIME,chat_info.getTime());
                intent.putExtra(MsgActivity.CONTENT,chat_info.getChat_message());
                mcontext.startActivity(intent);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder ab = new AlertDialog.Builder(mcontext);
                ab.setMessage("确定删除此记录？");
                ab.setTitle("删除聊天列表项");
                ab.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                ab.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int position =holder.getAdapterPosition();
                        mchats.remove(mchats.get(position));
                        notifyDataSetChanged();
                    }
                });
                ab.show();
                return true;
            }
        });
        return holder;
    }

    //绑定viewholder
    //主要用于项目从屏幕外滚动进屏幕的赋值
    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        //首先获取对象
        Chat_info chat_info = mchats.get(position);
        //填充值
        holder.name.setText(chat_info.getChat_name());
        holder.message.setText(chat_info.getChat_message());
        holder.time.setText(chat_info.getTime());
        holder.pre.setText(chat_info.getPre_number());
        Glide.with(mcontext).load(chat_info.getHead_id()).into(holder.head);
    }


    //构造方法，传入列表
    public ChatAdapter(List<Chat_info> chat_infoList){
        mchats=chat_infoList;
    }
    //返回列表数目
    @Override
    public int getItemCount() {
        return mchats.size();
    }


}
