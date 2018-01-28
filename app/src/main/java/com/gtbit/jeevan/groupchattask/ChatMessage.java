package com.gtbit.jeevan.groupchattask;

/**
 * Created by jeevan on 26/1/18.
 */

public class ChatMessage {
    private String userName, message;

    public ChatMessage() {
    }

    public ChatMessage(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
