package org.example;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @ManyToMany(mappedBy = "publishers")
    public Set<Autor> autors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Autor> getAutors() {
        return autors;
    }

    public void setAutors(Autor autor) {
        this.autors.add(autor);
    }

    public Publisher() {
    }
}
