package com.seven.lib_common.widget.rollingview;

public class RollTextItem {

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private int imgId;

    private String msg;

    private int textColor;

    public RollTextItem(String msg){
        this(msg,0);
    }

    public RollTextItem(String msg, int imgId){
        this(msg,imgId, android.R.color.holo_blue_light);
    }

    public RollTextItem(String msg, int imgId, int textColor){
        this.msg = msg;
        this.imgId = imgId;
        this.textColor = textColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
