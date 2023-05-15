package com.yangshuai.module.im.bean;

/**
 * @Author yangshuai
 * @Date 2023-05-08 周一 15:04
 * @Description
 * 聊天bean
 */
public class MessageBean {
    String name;//会话名称
    int type;//对方还是自己
    String time;//最后时间
    String imagerUrl;//对方头像
    String text;//最后一次会话内容
    int unReadMsgCnt;//未读消息
    String userName;
    String msgJosn;
    int msgType;//1 文字  2 自定义
    String roleType;//接收方的用户身份

    public MessageBean(){

    }

    public MessageBean(String name, int type, String time, String imagerUrl, String text) {
        this.name = name;
        this.type = type;
        this.time = time;
        this.imagerUrl = imagerUrl;
        this.text = text;
    }


    public MessageBean(String name, int type, String time, String imagerUrl, String text, int msgType) {
        this.name = name;
        this.type = type;
        this.time = time;
        this.imagerUrl = imagerUrl;
        this.text = text;
        this.msgType = msgType;
        this.userName=userName;
    }

    public MessageBean(String name, int type, String time, String imagerUrl, String text, int unReadMsgCnt, String msg) {
        this.name = name;
        this.type = type;
        this.time = time;
        this.imagerUrl = imagerUrl;
        this.text = text;
        this.unReadMsgCnt = unReadMsgCnt;
        this.msgJosn=msg;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImagerUrl() {
        return imagerUrl;
    }

    public void setImagerUrl(String imagerUrl) {
        this.imagerUrl = imagerUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUnReadMsgCnt() {
        return unReadMsgCnt;
    }
    public String getUnReadMsgCntStr() {
        return unReadMsgCnt+"";
    }
    public void setUnReadMsgCnt(int unReadMsgCnt) {
        this.unReadMsgCnt = unReadMsgCnt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsgJosn() {
        return msgJosn;
    }

    public void setMsgJosn(String msgJosn) {
        this.msgJosn = msgJosn;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}