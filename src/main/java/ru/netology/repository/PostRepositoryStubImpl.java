package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.MetaPost;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private ConcurrentHashMap<Long, MetaPost> repository = new ConcurrentHashMap<>();
    private static AtomicLong idCounter = new AtomicLong(0);

    public List<Post> all() {
        List<Post> posts = new Vector<>();
        repository.entrySet().stream()
                .filter(x->x.getValue().isNotDeleted())
                .forEach(x->posts.add(x.getValue().getPost()));
        return posts;
    }

    public Optional<Post> getById(long id) throws NotFoundException {
        if (repository.containsKey(id)) {
            return Optional.ofNullable(repository.get(id).getPost());
        } else {
            throw new NotFoundException("Repository doesn't contain current post");
        }
    }

    public Post save(Post post) throws NotFoundException {
        //Если пришел пост с id = 0, регестрируем новый номер id поста в зависимости от существующих в репозитории
        //Будем считать что удаленные номера можно использовать повторно
        if (post.getId() == 0) {
            post.setId(idCounter.incrementAndGet());
            repository.get(post.getId()).setPost(post);
        } else if (repository.containsKey(post.getId())) {
            repository.get(post.getId()).setPost(post);
            //Если пост с id!=0 не найден в репозитории будем возвращать 404
        } else {
            throw new NotFoundException();
        }
        return post;
    }

    public void removeById(long id) throws NotFoundException {
        if (repository.containsKey(id)) {
            repository.get(id).markToDelete();
        } else {
            throw new NotFoundException("Nothing to delete. Repository doesn't contain current post");
        }
    }

}
