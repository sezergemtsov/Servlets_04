package ru.netology.repository;

import ru.netology.model.Post;
import ru.netology.model.PostInterface;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> all();

    Optional<Post> getById(long id);

    Post save(PostInterface post);

    void removeById(long id);
}
