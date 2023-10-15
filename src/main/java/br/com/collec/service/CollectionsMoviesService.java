package br.com.collec.service;


import br.com.collec.entity.User;
import br.com.collec.payload.collectionsMovies.CollectionsDataDTO;
import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.entity.CollectionsMovies;
import br.com.collec.repository.CollectionsMoviesRepository;
import br.com.collec.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record CollectionsMoviesService(CollectionsMoviesRepository collectionsMoviesRepository, UserRepository userRepository) {

    public User saveCollectionsInUser(String userId, CollectionsDataDTO collectionsMoviesPatchDTO) {

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.getCollectionsMovies().add(newCollectionsMovies(collectionsMoviesPatchDTO));

        return userRepository.save(user);
    }

    public List<CollectionsResponseDTO> getAllPublishedCollections() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .flatMap(user -> user.getCollectionsMovies().stream())
                .filter(CollectionsMovies::getPublished)
                .map(this::mapToResponseCollectionsMovies)
                .collect(Collectors.toList());
    }

    public CollectionsResponseDTO updateCollectionPublishedStatus(String userId, String collectionId, boolean published) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        CollectionsMovies collectionToUpdate = user.getCollectionsMovies().stream()
                .filter(collection -> collection.getId().equals(collectionId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found"));

        collectionToUpdate.setPublished(published);
        userRepository.save(user);

        return mapToResponseCollectionsMovies(collectionToUpdate);
    }



    private static CollectionsMovies newCollectionsMovies(CollectionsDataDTO collectionsMoviesPatchDTO) {
        CollectionsMovies collectionsMovies = new CollectionsMovies();
        collectionsMovies.setName(collectionsMoviesPatchDTO.getName());
        collectionsMovies.setResume(collectionsMoviesPatchDTO.getResume());
        collectionsMovies.setMovies(collectionsMoviesPatchDTO.getMovies());
        collectionsMovies.setPublished(collectionsMoviesPatchDTO.getPublished());
        return collectionsMovies;
    }


    public CollectionsResponseDTO mapToResponseCollectionsMovies(CollectionsMovies collectionsMovies){
        return new CollectionsResponseDTO(
                collectionsMovies.getId(),
                collectionsMovies.getName(),
                collectionsMovies.getResume(),
                collectionsMovies.getMovies(),
                collectionsMovies.getPublished()
        );
    }

}
