package br.com.collec.controller;

import br.com.collec.entity.User;
import br.com.collec.payload.collectionsMovies.CollectionsDataDTO;
import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.service.CollectionsMoviesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/collectionsMovies")
public record CollectionsMoviesController(CollectionsMoviesService collectionsMoviesService) {

    @PatchMapping("/{userId}")
    public ResponseEntity<User> addCollectionsMovies(@PathVariable String userId,
                                                     @RequestBody CollectionsDataDTO collectionsMoviesPatchDTO) {

        return new ResponseEntity<>(collectionsMoviesService.saveCollectionsInUser(userId, collectionsMoviesPatchDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CollectionsResponseDTO>> getPublishedCollections() {
        return new ResponseEntity<>(collectionsMoviesService.getAllPublishedCollections(), HttpStatus.OK);
    }

    @PatchMapping("/publish/{userId}/{collectionId}")
    public ResponseEntity<CollectionsResponseDTO> publishCollection(@PathVariable String userId, @PathVariable String collectionId
    ) {
        return new ResponseEntity<>(collectionsMoviesService.updateCollectionPublishedStatus(userId, collectionId, true), HttpStatus.OK);
    }

    @PatchMapping("/unpublish/{userId}/{collectionId}")
    public ResponseEntity<CollectionsResponseDTO> unpublishCollection(@PathVariable String userId, @PathVariable String collectionId
    ) {
        return new ResponseEntity<>(collectionsMoviesService.updateCollectionPublishedStatus(userId, collectionId, false), HttpStatus.OK);
    }


}
