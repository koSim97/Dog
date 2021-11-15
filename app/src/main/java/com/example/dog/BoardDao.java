package com.example.dog;

public class BoardDao {
    String title;
    String content;
    String id;

    public BoardDao(){}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BoardDao(String id,String title, String content){
        this.id = id;
        this.content=content;
        this.title=title;
    }
}
