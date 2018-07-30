package com.bx.marqueeviewlibrary;

public class NoticeBean {
    String leftString;
    String rightString;

    public NoticeBean(String leftString, String rightString) {
        this.leftString = leftString;
        this.rightString = rightString;
    }

    public String getLeftString() {
        return leftString;
    }

    public void setLeftString(String leftString) {
        this.leftString = leftString;
    }

    public String getRightString() {
        return rightString;
    }

    public void setRightString(String rightString) {
        this.rightString = rightString;
    }
}
