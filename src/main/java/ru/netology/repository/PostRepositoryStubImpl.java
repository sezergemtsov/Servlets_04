package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.GoneException;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.model.PostInterface;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private final ConcurrentHashMap<Long, Post> repository = new ConcurrentHashMap<>();
    private static AtomicLong idCounter = new AtomicLong(0);

    @Override
    public List<Post> all() {
        List<Post> posts = new Vector<>();
        repository.forEach((key, value) -> posts.add(value));
        return posts;
    }

    @Override
    public Optional<Post> getById(long id) throws NotFoundException {
        if (repository.containsKey(id)) {
            return Optional.ofNullable(repository.get(id));
        } else {
            throw new NotFoundException("Repository doesn't contain current post");
        }
    }

    @Override
    public Post save(PostInterface post) throws NotFoundException, GoneException {
        if (post.getId() == 0) {
            Post temp = new Post(post.getId(), post.getContent());
            temp.setId(idCounter.incrementAndGet());
            repository.put(temp.getId(), temp);
            return temp;
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

    @Override
    public void removeById(long id) throws NotFoundException, GoneException {
        if (repository.containsKey(id)) {
            repository.get(id).markToDelete();
        } else {
            throw new NotFoundException("Nothing to delete. Repository doesn't contain current post");
        }
    }

}
