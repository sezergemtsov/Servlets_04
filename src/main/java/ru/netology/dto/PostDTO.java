package ru.netology.dto;

import ru.netology.model.Post;

public class PostDTO{

    private long id;
    private String content;

    public PostDTO (long id, String content) {
        this.id = id;
        this.content = content;
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
