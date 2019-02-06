package com.liangjinhai.quartzdemo.common.result;

/**
 * @Author: liangJinHai
 * @Date: 2018/10/23 13:30
 * @Description: 基本结果集，可扩充
 */
public class BaseResult {
    /**提示信息*/
    private String msg;
    /**状态*/
    private boolean status;
    /**返回数据，可为空*/
    private Object data;

    public BaseResult() {
    }

    public BaseResult(boolean status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public BaseResult(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    //--------------- success -----------------//
    public static BaseResult success(){
        return new BaseResult(true, null, null);
    }
    public static BaseResult success(String msg){
        return new BaseResult(true, msg, null);
    }
    public static BaseResult success(Object data){
        return new BaseResult(true, null, data);
    }
    public static BaseResult success(String msg, Object data){
        return new BaseResult(true, msg, data);
    }

    //--------------- failed -----------------//
    public static BaseResult failed(){
        return new BaseResult(false, null, null);
    }
    public static BaseResult failed(String msg){
        return new BaseResult(false, msg, null);
    }
    public static BaseResult failed(Object data){
        return new BaseResult(false, null, data);
    }
    public static BaseResult failed(String msg, Object data){
        return new BaseResult(false, msg, data);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
