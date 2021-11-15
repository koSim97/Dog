package com.example.dog;

public class BoardData {
    private String title;
    private String content;

    public BoardData(String title,String content){
        this.title = title;
        this.content = content;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
