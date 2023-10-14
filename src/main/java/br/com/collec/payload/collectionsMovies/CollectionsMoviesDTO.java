package br.com.collec.payload.collectionsMovies;

import br.com.collec.entity.Movies;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CollectionsMoviesDTO {

    @NotBlank(message = "name must not be empty")
    @Size(min = 3, message = "name must be greater than 3 letters")
    private String name;

    @NotBlank(message = "resume must not be empty")
    @Size(min = 3, message = "resume must be greater than 10 letters")
    private String resume;

    @NotEmpty(message = "resume must not be empty")
    private List<Movies> movies;

    private Boolean published;

    public CollectionsMoviesDTO(String name, String resume, List<Movies> movies, Boolean published) {
        this.name = name;
        this.resume = resume;
        this.movies = movies;
        this.published = published;
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
