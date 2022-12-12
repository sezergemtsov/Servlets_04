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
    private static final AtomicLong idCounter = new AtomicLong(0);

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
    public Post save(PostInterface post) throws NotFoundException {
        //Если пришел пост с id = 0, регестрируем новый номер id поста в зависимости от существующих в репозитории
        Post rPost = new Post(post.getId(), post.getContent());
        if (rPost.getId() == 0) {
            rPost.setId(idCounter.incrementAndGet());
            repository.put(post.getId(), rPost);
        } else if (repository.containsKey(post.getId())) {
            repository.replace(post.getId(), rPost);
            //Если пост с id!=0 не найден в репозитории будем возвращать 404
        } else {
            throw new NotFoundException();
        }
        return rPost;
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
