package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.GoneException;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
@Repository
public class PostRepository {
    private final ConcurrentHashMap<Long, Post> repository = new ConcurrentHashMap<>();
    private static AtomicLong idCounter = new AtomicLong(0);

    public List<Post> all() {
        List<Post> posts = new Vector<>();
        repository.forEach((key, value) -> posts.add(value));
        return posts;
    }

    public Optional<Post> getById(long id) {
        if (repository.containsKey(id)) {
            return Optional.ofNullable(repository.get(id));
        } else {
            throw new NotFoundException("Repository doesn't contain current post");
        }
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(idCounter.incrementAndGet());
            repository.put(post.getId(), post);
            return post;
        } else {
            if (repository.containsKey(post.getId())) {
                if (!repository.get(post.getId()).isDeleted()) {
                    repository.get(post.getId()).setContent(post.getContent());
                    return repository.get(post.getId());
                } else {
                    throw new GoneException();
                }
            } else {
                throw new NotFoundException();
            }
        }
    }

    public void removeById(long id) {
        if (repository.containsKey(id)) {
            repository.get(id).markToDelete();
        } else {
            throw new NotFoundException("Nothing to delete. Repository doesn't contain current post");
        }
    }

}
