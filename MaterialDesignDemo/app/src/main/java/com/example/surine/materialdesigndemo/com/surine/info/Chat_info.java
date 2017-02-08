package com.example.surine.materialdesigndemo.com.surine.info;

/**
 * Created by surine on 17-1-14.
 * 聊天列表实体类
 */

public class Chat_info {
    private String chat_name;
    private int Head_id;
    private String chat_message;
    private String time;
    private String pre_number;

    public Chat_info(String chat_name,int Head_id,String chat_message,String time,String pre_number)
    {
        this.chat_name= chat_name;
        this.chat_message=chat_message;
        this.Head_id=Head_id;
        this.pre_number=pre_number;
        this.time=time;
    }


    public String getChat_name(){
        return chat_name;
    }
    public String getChat_message(){
        return chat_message;
    }
    public String getPre_number(){
        return pre_number;
    }
    public String getTime(){
        return time;
    }
    public int getHead_id(){
        return Head_id;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public void setHead_id(int head_id) {
        Head_id = head_id;
    }

    public void setChat_message(String chat_message) {
        this.chat_message = chat_message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPre_number(String pre_number) {
        this.pre_number = pre_number;
    }
}
