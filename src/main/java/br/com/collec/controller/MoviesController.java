package br.com.collec.controller;

import br.com.collec.service.MoviesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/movies")
public record MoviesController(MoviesService moviesService) {

    @DeleteMapping("/{userId}/{collectionsId}/{movieId}")
    public ResponseEntity<Void> deleteMoviesById(@PathVariable String userId, @PathVariable String collectionsId, @PathVariable String movieId){
        moviesService.deleteMovie(userId, collectionsId, movieId);
        return ResponseEntity.noContent().build();
    }

}
