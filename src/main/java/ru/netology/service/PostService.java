package ru.netology.service;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepositoryStubImpl;

import java.util.List;

public class PostService {
  private final PostRepositoryStubImpl repository;

  public PostService(PostRepositoryStubImpl repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id).orElseThrow(NotFoundException::new);
  }

  public Post save(Post post) {
    return repository.save(post);
  }

  public void removeById(long id) throws NotFoundException {
    repository.removeById(id);
  }
}

