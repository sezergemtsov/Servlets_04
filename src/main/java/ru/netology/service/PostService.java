package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.GoneException;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.dto.PostDTO;
import ru.netology.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Service
public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<PostDTO> all() {
    List<PostDTO> list = new Vector<>();
    repository.all().stream().filter(x-> !x.isDeleted()).forEach(x->list.add(new PostDTO(x)));
    return list;
  }

  public PostDTO getById(long id) throws NotFoundException, GoneException {
    if (repository.getById(id).isPresent()) {
      if (!repository.getById(id).get().isDeleted()) {
        return new PostDTO(repository.getById(id).get());
      } else {
        throw new GoneException();
      }
    } else {
      throw new NotFoundException();
    }
  }

  public PostDTO save(PostDTO post) {
    if (post.getId() == 0) {
      return new PostDTO(repository.save(new Post(post.getId(), post.getContent())));
    } else {
      Optional<Post> temp = repository.getById(post.getId());
      if (temp.isPresent()) {
        if (!temp.get().isDeleted()) {
          return new PostDTO(repository.save(new Post(post.getId(), post.getContent())));
        } else {
          throw new GoneException();
        }
      } else {
        throw new NotFoundException();
      }
    }
  }

  public void removeById(long id) throws NotFoundException, GoneException {
    repository.removeById(id);
  }
}

