package com.wyh.modulecommon.network.bean;

/**
 * Created by 翁益亨 on 2020/1/19.
 * 处理网络请求返回类
 */
public class HttpResultBean<T> {
    private boolean success;
    private String resultMsg;//错误信息,success为false时存在
    private int total;
    private int code;
    private T data;
    private T rows;
    private T list;

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
