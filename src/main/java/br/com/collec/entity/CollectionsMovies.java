package br.com.collec.entity;

import org.springframework.data.annotation.Id;

import java.util.List;

public class CollectionsMovies {

    @Id
    private String id;
    private String name;
    private String resume;
    private List<Movies> movies;

    public CollectionsMovies(String id, String name, String resume, List<Movies> movies) {
        this.id = id;
        this.name = name;
        this.resume = resume;
        this.movies = movies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public List<Movies> getMovies() {
        return movies;
    }

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
    }
}