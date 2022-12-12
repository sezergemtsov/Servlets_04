package ru.netology.model;

public class PostDTO implements PostInterface{

    private final long id;
    private final String content;

    public PostDTO(PostInterface post) {
        this.id = post.getId();
        this.content = post.getContent();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getContent() {
        return content;
    }
}
