package com.example.surine.materialdesigndemo.com.surine.info;

/**
 * Created by surine on 2017/2/5.
 * 聊天记录实体类
 */

public class Msg_info {
  public static final  int TYPE_RECEIVED = 0;
    public static final  int TYPE_SEND = 1;

    private String content;
    private int type;
    private int headId;
    private String msg_time;


    public Msg_info(String content, int type, int headId, String msg_time) {
        this.content = content;
        this.type = type;
        this.headId = headId;
        this.msg_time = msg_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public String getMsg_time() {
        return msg_time;
    }

    public int getHeadId() {
        return headId;
    }

    public void setMsg_time(String msg_time) {
        this.msg_time = msg_time;

    }
}
