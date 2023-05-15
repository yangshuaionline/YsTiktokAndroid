package com.yangshuai.library.base.event;

/**
 * @Author hcp
 * @Created 2019/4/15
 * @Editor hcp
 * @Edited 2019/4/15
 * @Type
 * @Layout
 * @Api
 */
public class Event<T> {

    // refreshOperationData 刷新房源操作菜单数据

    private int position;
    private T eventData;

    public Event(T eventData) {
        this.eventData = eventData;
    }

    public Event(T eventData, int position) {
        this.eventData = eventData;
        this.position = position;
    }

    public T getEventData() {
        return eventData;
    }

    public int getPosition() {
        return position;
    }
}
