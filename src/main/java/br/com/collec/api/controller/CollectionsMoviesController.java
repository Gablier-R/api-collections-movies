package br.com.collec.api.controller;

import br.com.collec.api.payload.AllResponseDTO;
import br.com.collec.api.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.api.payload.collectionsMovies.CollectionsDataDTO;
import br.com.collec.domain.service.CollectionsMoviesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static br.com.collec.utils.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.collec.utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("v1/api/collectionsMovies")
public class CollectionsMoviesController {

    final CollectionsMoviesService collectionsMoviesService;

    public CollectionsMoviesController(CollectionsMoviesService collectionsMoviesService) {
        this.collectionsMoviesService = collectionsMoviesService;
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Optional<CollectionsResponseDTO>> addCollections(@PathVariable String userId, @RequestBody @Valid CollectionsDataDTO collectionsMoviesPatchDTO) {
        return new ResponseEntity<>(collectionsMoviesService.saveCollectionsInUser(userId, collectionsMoviesPatchDTO), HttpStatus.OK);
    }

    @PatchMapping("/unpublish/{userId}/{collectionId}")
    public ResponseEntity<CollectionsResponseDTO> unpublishCollection(@PathVariable String userId, @PathVariable String collectionId) {
        return new ResponseEntity<>(collectionsMoviesService.updateCollectionPublishedStatus(userId, collectionId, false), HttpStatus.OK);
    }

    @PatchMapping("/publish/{userId}/{collectionId}")
    public ResponseEntity<CollectionsResponseDTO> publishCollection(@PathVariable String userId, @PathVariable String collectionId) {
        return new ResponseEntity<>(collectionsMoviesService.updateCollectionPublishedStatus(userId, collectionId, true), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/{collectionId}")
    public ResponseEntity<CollectionsResponseDTO> updateCollection(@PathVariable String userId,
                                                                   @PathVariable String collectionId,
                                                                   @Valid @RequestBody CollectionsDataDTO updateRequest)
    {
        return new ResponseEntity<>(collectionsMoviesService.updateCollection(userId, collectionId, updateRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<AllResponseDTO> getAllCollections(@RequestParam( defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                            @RequestParam( defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize){
        return new ResponseEntity<>(collectionsMoviesService.mapToPageableCollections(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/published")
    public ResponseEntity<AllResponseDTO> getAllCollectionsPublished(@RequestParam( defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                                     @RequestParam( defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize){
        return new ResponseEntity<>(collectionsMoviesService.mapToPageableCollectionsPublished(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{userId}/{collectionId}")
    public ResponseEntity<CollectionsResponseDTO> getPublishedCollectionById(@PathVariable String userId, @PathVariable String collectionId) {
        return new ResponseEntity<>(collectionsMoviesService.getPublishedCollectionById(userId, collectionId), HttpStatus.OK);
    }

    @GetMapping("/simple/{userId}/{collectionId}")
    public ResponseEntity<CollectionsResponseDTO> getCollectionById(@PathVariable String userId, @PathVariable String collectionId) {
        return new ResponseEntity<>(collectionsMoviesService.getCollectionById(userId, collectionId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{collectionId}")
    public ResponseEntity<Void> deleteCollection(@PathVariable String userId, @PathVariable String collectionId) {
        collectionsMoviesService.deleteCollection(userId, collectionId);
        return ResponseEntity.noContent().build();
    }



}
