package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.GoneException;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.dto.PostDto;
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

  public List<PostDto> all() {
    List<PostDto> list = new Vector<>();
    repository.all().stream().filter(Post::isItDeleted).forEach(x->list.add(new PostDto(x)));
    return list;
  }

  public PostDto getById(long id) {
    if (repository.getById(id).isPresent()) {
      if (repository.getById(id).get().isItDeleted()) {
        return new PostDto(repository.getById(id).get());
      } else {
        throw new GoneException();
      }
    } else {
      throw new NotFoundException();
    }
  }

  public PostDto save(PostDto post) {
    if (post.getId() == 0) {
      return new PostDto(repository.save(new Post(post.getId(), post.getContent())));
    } else {
      Optional<Post> temp = repository.getById(post.getId());
      if (temp.isPresent()) {
        if (temp.get().isItDeleted()) {
          return new PostDto(repository.save(new Post(post.getId(), post.getContent())));
        } else {
          throw new GoneException();
        }
      } else {
        throw new NotFoundException();
      }
    }
  }

  public String removeById(long id) {
    try {
      repository.removeById(id);
      return "Post " + id + " was deleted";
    } catch (NotFoundException e) {
      return "Post " + id + " wasn't found";
    } catch (GoneException e) {
      return "Post " + id + " was already deleted";
    }
  }
}

