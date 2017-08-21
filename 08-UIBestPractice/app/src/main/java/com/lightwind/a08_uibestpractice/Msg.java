package com.lightwind.a08_uibestpractice;

/**
 * 功能：消息实体类
 * 作者：刘洋
 * 时间：2017/8/18
 */
public class Msg {

    // 收到一条消息
    public static final int TYPE_RECEIVED = 0;
    // 发出一条消息
    public static final int TYPE_SENT = 1;

    // 消息的内容
    private String content;
    // 消息的类型
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
