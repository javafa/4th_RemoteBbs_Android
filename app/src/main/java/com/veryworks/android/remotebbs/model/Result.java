package com.veryworks.android.remotebbs.model;

/**
 * Created by pc on 10/26/2017.
 */

public class Result {
    private Data[] data;
    private String code;
    private String msg;

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data[] getData (){
        return data;
    }
    public void setData (Data[] data){
        this.data = data;
    }

    public String getCode () {
        return code;
    }
    public void setCode (String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ClassPojo [data = "+data+", code = "+code+"]";
    }
}
