package ru.netology.model;

import ru.netology.exception.GoneException;

public class Post implements PostInterface {
  private long id;
  private String content;

  private boolean isDeleted;

  public Post() {
  }

  public Post(long id, String content) {
    this.id = id;
    this.content = content;
    isDeleted = false;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setContent (String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void markToDelete() {
    if (!isDeleted) {
      isDeleted = true;
    } else {
      throw new GoneException();
    }
  }
  public boolean isDeleted() {
    return isDeleted;
  }
}
