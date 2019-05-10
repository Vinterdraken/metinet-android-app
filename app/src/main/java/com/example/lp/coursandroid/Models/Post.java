package com.example.lp.coursandroid.Models;

public class Post {

    private String id;
    private String title;
    private String creationDate;
    private String content;
    private Category category;

    public Post(String id, String title, String creationDate, String content, String idCategory, String nameCategory) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.content = content;
        this.category = new Category(idCategory, nameCategory);
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
    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", content='" + content + '\'' +
                ", idCategory='" + category.getId() + '\'' +
                '}';
    }
}
