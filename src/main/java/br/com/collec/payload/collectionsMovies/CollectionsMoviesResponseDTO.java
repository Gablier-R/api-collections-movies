package br.com.collec.payload.collectionsMovies;

import br.com.collec.entity.Movies;

import java.util.List;

public class CollectionsMoviesResponseDTO {

    private String id;
    private String name;
    private String resume;
    private List<Movies> movies;
    private Boolean published;


    public CollectionsMoviesResponseDTO(String id, String name, String resume, List<Movies> movies, Boolean published) {
        this.id = id;
        this.name = name;
        this.resume = resume;
        this.movies = movies;
        this.published = published;
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

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}
