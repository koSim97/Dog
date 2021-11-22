package com.example.dog;

public class BoardData {
    private String title;
    private String content;
    String id;

    public BoardData(String id,String title,String content){
        this.title = title;
        this.content = content;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
