package ru.netology.model;

public class PostDTO implements PostInterface{

    private long id;
    private String content;

    public PostDTO (long id, String content) {
        this.id = id;
        this.content = content;
    }

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

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
