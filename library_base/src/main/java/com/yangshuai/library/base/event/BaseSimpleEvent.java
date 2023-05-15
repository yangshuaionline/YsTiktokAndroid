package com.yangshuai.library.base.event;

/**
 * 基础简单的事件模型
 *
 * @author hcp
 * @created 2020-02-11
 */
public class BaseSimpleEvent<T> {

    private String eventId; // 事件ID，用于区分事件
    private String eventMsg; // 事件消息文本
    private T eventData; // 事件扩展数据

    public BaseSimpleEvent(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventMsg() {
        return eventMsg;
    }

    public void setEventMsg(String eventMsg) {
        this.eventMsg = eventMsg;
    }

    public T getEventData() {
        return eventData;
    }

    public void setEventData(T eventData) {
        this.eventData = eventData;
    }
}
