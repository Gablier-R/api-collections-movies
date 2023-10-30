package br.com.collec.domain.service;

import br.com.collec.domain.entity.User;
import br.com.collec.api.payload.AllResponseDTO;
import br.com.collec.api.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.domain.entity.CollectionsMovies;
import br.com.collec.api.payload.collectionsMovies.CollectionsDataDTO;
import br.com.collec.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectionsMoviesService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceMap serviceMap;

    public Optional<CollectionsResponseDTO> saveCollectionsInUser(String userId, CollectionsDataDTO collectionsMoviesPatchDTO) {
        var user = verifyUserById(userId);

        user.getCollectionsMovies().add(newCollectionsMovies(collectionsMoviesPatchDTO));

        User savedUser = userRepository.save(user);

        return Optional.of(savedUser)
                .map(User::getCollectionsMovies)
                .flatMap(moviesList -> moviesList.stream().findFirst())
                .map(serviceMap::mapToResponseOnlyCollectionsMovies);
    }

    public CollectionsResponseDTO getCollectionById(String userId, String collectionId) {
        var user = verifyUserById(userId);
        var collection = verifyCollection(collectionId, user);

        return serviceMap.mapToResponseOnlyCollectionsMovies(collection);
    }



    //Metodo pode ser alterado para somente collectionId?
    public CollectionsResponseDTO getPublishedCollectionById(String userId, String collectionId) {
        User user = verifyUserById(userId);

        CollectionsMovies collection = verifyCollection(collectionId, user);

        if (!collection.getPublished()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection is not published");
        }

        return serviceMap.mapToResponseOnlyCollectionsMovies(collection);
    }

    public AllResponseDTO mapToPageableCollections(int pageNo, int pageSize) {
        Page<User> usersPage = userRepository.findAll(PageRequest.of(pageNo, pageSize));

        List<CollectionsMovies> collectionsList = usersPage.stream()
                .flatMap(user -> user.getCollectionsMovies().stream())
                .toList();

        List<CollectionsResponseDTO> content = collectionsList.stream()
                .map(serviceMap::mapToResponseOnlyCollectionsMovies)
                .collect(Collectors.toList());

        return ServiceMap.mapToResponseAll(content, usersPage);
    }

    public AllResponseDTO mapToPageableCollectionsPublished(int pageNo, int pageSize) {
        Page<User> usersPage = userRepository.findAll(PageRequest.of(pageNo, pageSize));

        List<CollectionsMovies> collectionsList = usersPage.stream()
                .flatMap(user -> user.getCollectionsMovies().stream())
                .filter(CollectionsMovies::getPublished)
                .toList();

        List<CollectionsResponseDTO> content = collectionsList.stream()
                .map(serviceMap::mapToResponseOnlyCollectionsMovies)
                .collect(Collectors.toList());

        return ServiceMap.mapToResponseAll(content, usersPage);
    }

    public CollectionsResponseDTO updateCollectionPublishedStatus(String userId, String collectionId, boolean published) {

        var user = verifyUserById(userId);

        CollectionsMovies collectionToUpdate = verifyCollection(collectionId, user);

        collectionToUpdate.setPublished(published);
        collectionToUpdate.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return serviceMap.mapToResponseOnlyCollectionsMovies(collectionToUpdate);
    }

    public CollectionsResponseDTO updateCollection(String userId, String collectionId, CollectionsDataDTO updateRequest) {
        var user = verifyUserById(userId);

        var collection = verifyCollection(collectionId, user);

        collection.setName(updateRequest.name());
        collection.setResume(updateRequest.resume());
        collection.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return serviceMap.mapToResponseOnlyCollectionsMovies(collection);
    }

    public void deleteCollection(String userId, String collectionId) {

        var user = verifyUserById(userId);

        verifyCollection(collectionId, user);

        user.getCollectionsMovies().removeIf(collectionDelete -> collectionDelete.getId().equals(collectionId));

        userRepository.save(user);
    }

    private User verifyUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private CollectionsMovies verifyCollection(String collectionId, User user) {
        return user.getCollectionsMovies().stream()
                .filter(collection -> collection.getId().equals(collectionId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found"));
    }

    private static CollectionsMovies newCollectionsMovies(CollectionsDataDTO collectionsMoviesPatchDTO) {
        CollectionsMovies collectionsMovies = new CollectionsMovies();
        collectionsMovies.setName(collectionsMoviesPatchDTO.name());
        collectionsMovies.setResume(collectionsMoviesPatchDTO.resume());
        return collectionsMovies;
    }



}
