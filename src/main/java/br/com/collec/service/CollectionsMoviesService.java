package br.com.collec.service;


import br.com.collec.entity.User;
import br.com.collec.payload.collectionsMovies.CollectionsDataDTO;
import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.entity.CollectionsMovies;
import br.com.collec.payload.collectionsMovies.CollectionsUpdateDTO;
import br.com.collec.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record CollectionsMoviesService( UserRepository userRepository) {

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

    public CollectionsResponseDTO updateCollection(String userId, String collectionId, CollectionsUpdateDTO updateRequest) {
        // Obter o usuário
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Encontrar a coleção
        CollectionsMovies collection = user.getCollectionsMovies().stream()
                .filter(c -> c.getId().equals(collectionId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found"));

        // Atualizar os campos da coleção
        if (updateRequest.getName() != null) {
            collection.setName(updateRequest.getName());
        }

        if (updateRequest.getResume() != null) {
            collection.setResume(updateRequest.getResume());
        }

        // Salvar as alterações
        userRepository.save(user);

        // Mapear e retornar a coleção atualizada
        return mapToResponseCollectionsMovies(collection);
    }

    public void deleteCollection(String userId, String collectionId) {
        // Obter o usuário
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Remover a coleção pelo ID
        user.getCollectionsMovies().removeIf(collection -> collection.getId().equals(collectionId));

        // Salvar as alterações
        userRepository.save(user);
    }



    private static CollectionsMovies newCollectionsMovies(CollectionsDataDTO collectionsMoviesPatchDTO) {
        CollectionsMovies collectionsMovies = new CollectionsMovies();
        collectionsMovies.setName(collectionsMoviesPatchDTO.getName());
        collectionsMovies.setResume(collectionsMoviesPatchDTO.getResume());
        collectionsMovies.setMovies(collectionsMoviesPatchDTO.getMovies());
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
