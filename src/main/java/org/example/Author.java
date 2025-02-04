package org.example;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @OneToMany(mappedBy = "author")
    public Set<Book> books;

    @ManyToMany
    @JoinTable(name = "autor_publiser",
            joinColumns = @JoinColumn(name="autor_id"),
    inverseJoinColumns = @JoinColumn(name = "publisehr_id"))
    public Set<Publisher> publishers  =  new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public Set<Publisher> getPublishers() {
        return publishers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void setPublishers(Publisher publisher) {
        this.publishers.add(publisher);
    }

    public Author() {
    }
}
