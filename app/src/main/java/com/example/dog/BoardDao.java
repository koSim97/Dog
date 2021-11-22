package com.example.dog;

public class BoardDao {
    String title;
    String content;
    String id;
    String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String id) {
        this.email = email;
    }

    public BoardDao(String id,String title, String content,String email){
        this.id = id;
        this.content=content;
        this.title=title;
        this.email=email;
    }
}
