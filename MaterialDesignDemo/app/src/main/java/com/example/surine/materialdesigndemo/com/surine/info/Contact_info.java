package com.example.surine.materialdesigndemo.com.surine.info;

/**
 * Created by surine on 2017/2/6.
 */

public class Contact_info {
    private String name;
    private int Head_id;
    private String state;
    private String last_time;

    public String getLast_time() {
        return last_time;
    }




    public String getName(){
        return  name;
    }

    public String getState(){
        return state;
    }

    public int getHead_id(){
        return Head_id;
    }

    public Contact_info(String name, int head_id, String state, String last_time) {
        this.name = name;
        Head_id = head_id;
        this.state = state;
        this.last_time = last_time;
    }
}
