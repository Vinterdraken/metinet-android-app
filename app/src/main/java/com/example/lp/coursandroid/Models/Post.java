package com.example.lp.coursandroid.Models;

public class Post {

    private String id;
    private String title;
    private String creationDate;
    private String content;
    private String idCategory;

    public Post(String id, String title, String creationDate, String content, String idCategory) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.content = content;
        this.idCategory = idCategory;
    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public String getContent() {
        return content;
    }
    public String getIdCategory() {
        return idCategory;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", content='" + content + '\'' +
                ", idCategory='" + idCategory + '\'' +
                '}';
    }
}
