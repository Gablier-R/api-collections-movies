package br.com.collec.controller;

import br.com.collec.entity.User;
import br.com.collec.payload.collectionsMovies.CollectionsMoviesDTO;
import br.com.collec.payload.collectionsMovies.CollectionsMoviesResponseDTO;
import br.com.collec.service.CollectionsMoviesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/collectionsMovies")
public record CollectionsMoviesController(CollectionsMoviesService collectionsMoviesService) {

    @PatchMapping("/{userId}")
    public ResponseEntity<User> addCollectionsMovies(@PathVariable String userId,
                                                     @RequestBody CollectionsMoviesDTO collectionsMoviesPatchDTO) {

        User updatedUser = collectionsMoviesService.updateCollectionsMovies(userId, collectionsMoviesPatchDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
