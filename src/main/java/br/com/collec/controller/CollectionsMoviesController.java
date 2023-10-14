//package br.com.collec.controller;
//
//import br.com.collec.payload.collectionsMovies.CollectionsMoviesDTO;
//import br.com.collec.payload.collectionsMovies.CollectionsMoviesResponseDTO;
//import br.com.collec.service.CollectionsMoviesService;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/collectionsMovies")
//public record CollectionsMoviesController(CollectionsMoviesService collectionsMoviesService) {
//
//    @PostMapping("/{id}")
//    public ResponseEntity<CollectionsMoviesResponseDTO> createCollection(@PathVariable String id, @Valid @RequestBody CollectionsMoviesDTO collectionsMoviesDTO){
//        return new ResponseEntity<>(collectionsMoviesService.saveCollectionsInUser(id, collectionsMoviesDTO), HttpStatus.OK);
//    }
//
//}
