package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Stub
public class PostRepository {
    private ConcurrentHashMap<Long, Post> repository = new ConcurrentHashMap();

    public List<Post> all() {
        List<Post> posts = new Vector<>();
        repository.entrySet().forEach(x -> posts.add(x.getValue()));
        return posts;
    }

    public Optional<Post> getById(long id) throws NotFoundException {
        if (repository.containsKey(id)) {
            return Optional.ofNullable(repository.get(id));
        } else {
            throw new NotFoundException("Repository doesn't contain current post");
        }
    }

    public Post save(Post post) {
        //Если пришел пост с id = 0, регестрируем новый номер id поста в зависимости от существующих в репозитории
        //Будем считать что удаленные номера можно использовать повторно
        if (post.getId() == 0) {
            if (repository.isEmpty()) {
                post.setId(1);
            } else {
                post.setId(setPostWithNewId());
            }
        }
        //Если пост с id!=0 не найден в репозитории будем просто сохранять его под своим номером
        repository.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) throws NotFoundException {
        if (repository.containsKey(id)) {
            repository.remove(id);
        } else {
            throw new NotFoundException("Nothing to delete. Repository doesn't contain current post");
        }
    }

    private Long setPostWithNewId() {
        return repository.entrySet().stream().mapToLong(x -> x.getKey()).max().getAsLong() + 1;
    }

}
