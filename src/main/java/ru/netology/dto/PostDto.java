package ru.netology.dto;

import ru.netology.model.Post;

public class PostDto {

    private final long id;
    private final String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
