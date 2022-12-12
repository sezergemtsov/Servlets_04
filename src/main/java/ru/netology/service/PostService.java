package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.GoneException;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.model.PostDTO;
import ru.netology.model.PostInterface;
import ru.netology.repository.PostRepositoryStubImpl;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Service
public class PostService {
  private final PostRepositoryStubImpl repository;

  public PostService(PostRepositoryStubImpl repository) {
    this.repository = repository;
  }

  public List<PostDTO> all() {
    List<PostDTO> list = new Vector<>();
    repository.all().stream().filter(Post::isDeleted).forEach(x->list.add(new PostDTO(x)));
    return list;
  }

  public PostDTO getById(long id) throws NotFoundException, GoneException {
    Optional<Post> post = repository.getById(id);
    if (post.isPresent()) {
      if (!post.get().isDeleted()) {
        return new PostDTO(post.get());
      } else {
        throw new GoneException();
      }
    } else {
      throw new NotFoundException();
    }
  }

  public PostDTO save(PostInterface post) {
    Optional<Post> repoPost = repository.getById(post.getId());
    if (repoPost.isPresent()) {
      if (!repoPost.get().isDeleted()) {
        return new PostDTO(repository.save(post));
      } else {
        throw new GoneException();
      }
    } else {
      throw new NotFoundException();
    }
  }

  public void removeById(long id) throws NotFoundException {
    repository.removeById(id);
  }
}

