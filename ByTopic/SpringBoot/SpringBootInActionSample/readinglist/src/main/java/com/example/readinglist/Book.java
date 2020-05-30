package com.example.readinglist;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private String reader;
  private String isbn;
  private String title;
  private String author;
  private String description;

  public void setReader(String aReader) {
    reader = aReader;
  }

  public String getReader() {
    return reader;
  }

  public String getIsbn() {
    return isbn;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getDescription() {
    return description;
  }
  
  public void setIsbn(String aIsbn) {
    isbn = aIsbn;
  }

  public void setTitle(String aTitle) {
    title = aTitle;
  }

  public void setAuthor(String aAuthor) {
    author = aAuthor;
  }

  public void setDescription(String aDescription) {
    description = aDescription;
  }
}
