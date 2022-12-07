package ru.netology.model;

import ru.netology.exception.NotFoundException;

public class MetaPost {
    private boolean isNotDeleted;
    private Post post;

    public MetaPost(Post post) {
        this.post = post;
        isNotDeleted = true;
    }

    public boolean isNotDeleted() {
        return isNotDeleted;
    }

    public Post getPost() throws NotFoundException {
        if (isNotDeleted) {
            return post;
        } else {
            throw new NotFoundException("Post was not found");
        }
    }

    public void markToDelete() throws NotFoundException {
        if (isNotDeleted) {
            isNotDeleted = false;
        } else {
            throw new NotFoundException("Post was not found");
        }
    }

    public void setPost(Post post) throws NotFoundException {
        if (isNotDeleted) {
            this.post = post;
        } else {
            throw new NotFoundException("Post was not found");
        }
    }
}
