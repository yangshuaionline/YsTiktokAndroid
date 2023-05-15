package com.yangshuai.library.base.entity;

import android.text.SpannableString;

import androidx.core.content.ContextCompat;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.utils.AppContext;
import com.yangshuai.library.base.utils.StringUtils;
import java.io.Serializable;

/**
 * @author hcp
 * Create on 2020-03-24 15:17
 */
public class LiveImBean implements Serializable {

    private String roomId;
    private String msgTypeName;
    private String msgTitle;
    private String layout;
    private String createAt;
    private int position;


    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMsgTypeName() {
        return msgTypeName;
    }

    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public SpannableString getImContent() {
        String nickNameKey = msgTypeName.length() > 10 ? (msgTypeName.substring(0,10) + "** :")  :  (msgTypeName + ":");
        String contentStr = nickNameKey + " " + msgTitle;
        return StringUtils.matcherSearchText(ContextCompat.getColor(AppContext.getAppContext(), R.color.text_green),contentStr,nickNameKey);
    }
}
