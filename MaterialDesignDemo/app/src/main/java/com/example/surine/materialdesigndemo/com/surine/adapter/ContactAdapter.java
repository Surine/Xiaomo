package com.example.surine.materialdesigndemo.com.surine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.surine.materialdesigndemo.com.surine.UI_Activity.MsgActivity;
import com.example.surine.materialdesigndemo.R;
import com.example.surine.materialdesigndemo.com.surine.info.Contact_info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by surine on 2017/2/6.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contact_info> mContact_infos = new ArrayList<>();
    private Context mContext;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"这是头像",Toast.LENGTH_SHORT).show();
            }
        });
        holder.contact_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position =holder.getAdapterPosition();
                Contact_info contact_info = mContact_infos.get(position);
                Intent intent = new Intent(mContext, MsgActivity.class);
                intent.putExtra(MsgActivity.NAME,contact_info.getName());
                intent.putExtra(MsgActivity.HEAD,contact_info.getHead_id());
                intent.putExtra(MsgActivity.TIME,contact_info.getLast_time());
                mContext.startActivity(intent);
            }
        });
        holder.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"在线状态",Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    public ContactAdapter(List<Contact_info> contact_infos) {
       mContact_infos=contact_infos;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
         Contact_info contact_info =mContact_infos.get(position);
        Glide.with(mContext).load(contact_info.getHead_id()).into(holder.head);
        holder.name.setText(contact_info.getName());
        holder.state.setText(contact_info.getState());
        holder.contact_time.setText(contact_info.getLast_time());
    }

    @Override
    public int getItemCount() {
        return mContact_infos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView head;
        TextView name;
        TextView state;
        CardView contact_cardview;
        TextView contact_time;
        public ViewHolder(View view) {
            super(view);

            head= (ImageView) view.findViewById(R.id.contact_head_image);
            name= (TextView) view.findViewById(R.id.contact_name);
            state= (TextView) view.findViewById(R.id.contact_state);
            contact_cardview= (CardView) view.findViewById(R.id.contact_cardview);
            contact_time= (TextView) view.findViewById(R.id.contact_time);
        }
    }
}
