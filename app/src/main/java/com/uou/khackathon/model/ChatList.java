package com.uou.khackathon.model;

public class ChatList {
    private String name;
    private String time;
    private String chatContents;
    private String chatCount;
    private boolean isValid;


    public ChatList(String name, String time, String chatContents, String chatCount, boolean isValid) {
        this.name = name;
        this.time = time;
        this.chatContents = chatContents;
        this.chatCount = chatCount;
        this.isValid = isValid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChatContents() {
        return chatContents;
    }

    public void setChatContents(String chatContents) {
        this.chatContents = chatContents;
    }

    public String getChatCount() {
        return chatCount;
    }

    public void setChatCount(String chatCount) {
        this.chatCount = chatCount;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
