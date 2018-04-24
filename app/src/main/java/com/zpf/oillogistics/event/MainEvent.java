package com.zpf.oillogistics.event;


public class MainEvent {
    private String message;

    public MainEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
