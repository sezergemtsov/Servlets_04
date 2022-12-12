package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private ConcurrentHashMap<Long, Post> repository = new ConcurrentHashMap<>();
    private static AtomicLong idCounter = new AtomicLong(0);

    public List<Post> all() {
        List<Post> posts = new Vector<>();
        repository.entrySet().stream()
                .forEach(x->posts.add(x.getValue()));
        return posts;
    }

    public Optional<Post> getById(long id) throws NotFoundException {
        if (repository.containsKey(id)) {
            return Optional.ofNullable(repository.get(id));
        } else {
            throw new NotFoundException("Repository doesn't contain current post");
        }
    }

    public Post save(Post post) throws NotFoundException {
        //Если пришел пост с id = 0, регестрируем новый номер id поста в зависимости от существующих в репозитории
        //Будем считать что удаленные номера можно использовать повторно
        if (post.getId() == 0) {
            post.setId(idCounter.incrementAndGet());
            repository.put(post.getId(),post);
        } else if (repository.containsKey(post.getId())) {
            repository.replace(post.getId(),post);
            //Если пост с id!=0 не найден в репозитории будем возвращать 404
        } else {
            throw new NotFoundException();
        }
        return post;
    }

    public void removeById(long id) throws NotFoundException {
        if (repository.containsKey(id)) {
            //TODO removing mark
        } else {
            throw new NotFoundException("Nothing to delete. Repository doesn't contain current post");
        }
    }

}
