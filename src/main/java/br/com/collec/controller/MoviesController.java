package br.com.collec.controller;

import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.payload.movies.MoviesDataDTO;
import br.com.collec.service.MoviesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/movies")
public record MoviesController(MoviesService moviesService) {

    @DeleteMapping("/{userId}/{collectionsId}/{movieId}")
    public ResponseEntity<Void> deleteMoviesById(@PathVariable String userId, @PathVariable String collectionsId, @PathVariable String movieId){
        moviesService.deleteMovie(userId, collectionsId, movieId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/addMovie/{userId}/{collectionId}")
    public ResponseEntity<CollectionsResponseDTO> addMovieToCollection(
            @PathVariable String userId,
            @PathVariable String collectionId,
            @RequestBody MoviesDataDTO movieRequest
    ) {
        CollectionsResponseDTO updatedCollection = moviesService.addMovieToCollection(userId, collectionId, movieRequest);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

}
