package com.example.surine.materialdesigndemo.com.surine.info;

/**
 * Created by surine on 2017/2/5.
 * 空间动态实体类
 */

public class Zone_info {
    private int pictureId;
    private int qzone_head;
    private String friend_name;
    private String speak_time;
    private String content;
    private boolean love;

    public Zone_info(int pictureId, int qzone_head, String friend_name,
                     String speak_time, String content, boolean love) {
        this.pictureId = pictureId;
        this.qzone_head = qzone_head;
        this.friend_name = friend_name;
        this.speak_time = speak_time;
        this.content = content;
        this.love = love;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public int getQzone_head() {
        return qzone_head;
    }

    public void setQzone_head(int qzone_head) {
        this.qzone_head = qzone_head;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getSpeak_time() {
        return speak_time;
    }

    public void setSpeak_time(String speak_time) {
        this.speak_time = speak_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLove() {
        return love;
    }

    public void setLove(boolean love) {
        this.love = love;
    }
}
