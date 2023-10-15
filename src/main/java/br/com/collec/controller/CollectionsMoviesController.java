package br.com.collec.controller;

import br.com.collec.entity.User;
import br.com.collec.payload.collectionsMovies.CollectionsDataDTO;
import br.com.collec.service.CollectionsMoviesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/collectionsMovies")
public record CollectionsMoviesController(CollectionsMoviesService collectionsMoviesService) {

    @PatchMapping("/{userId}")
    public ResponseEntity<User> addCollectionsMovies(@PathVariable String userId,
                                                     @RequestBody CollectionsDataDTO collectionsMoviesPatchDTO) {

        User updatedUser = collectionsMoviesService.updateCollectionsMovies(userId, collectionsMoviesPatchDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
