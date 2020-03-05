package com.wyh.modulecommon;

/**
 * Created by 翁益亨 on 2020/1/17.
 * 发送事件对象，接收泛型对象
 */
public class EventCenter<T> {

    /**
     * reserved data
     */
    public T data;

    /**
     * this code distinguish between different events
     */
    public int eventCode = -1;

    public EventCenter(int eventCode) {
        this(eventCode, null);
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    /**
     * get event code
     *
     * @return
     */
    public int getEventCode() {
        return this.eventCode;
    }

    /**
     * get event reserved data
     *
     * @return
     */
    public T getData() {
        return this.data;
    }
}
