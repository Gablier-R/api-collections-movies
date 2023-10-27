package br.com.collec.service;

import br.com.collec.entity.User;
import br.com.collec.payload.AllResponseDTO;
import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.entity.CollectionsMovies;
import br.com.collec.payload.collectionsMovies.CollectionsDataDTO;
import br.com.collec.payload.user.UserResponseDTO;
import br.com.collec.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionsMoviesService {

    final UserRepository userRepository;
    final ServiceMap serviceMap;

    public CollectionsMoviesService(UserRepository userRepository, ServiceMap serviceMap) {
        this.userRepository = userRepository;
        this.serviceMap = serviceMap;
    }

    public UserResponseDTO saveCollectionsInUser(String userId, CollectionsDataDTO collectionsMoviesPatchDTO) {

        var user = verifyUserById(userId);

        user.getCollectionsMovies().add(newCollectionsMovies(collectionsMoviesPatchDTO));

        User savedUser = userRepository.save(user);

        return serviceMap.mapToResponseUserAndCollections(savedUser);
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
        userRepository.save(user);

        return serviceMap.mapToResponseOnlyCollectionsMovies(collectionToUpdate);
    }

    public CollectionsResponseDTO updateCollection(String userId, String collectionId, CollectionsDataDTO updateRequest) {
        var user = verifyUserById(userId);

        var collection = verifyCollection(collectionId, user);

        collection.setName(updateRequest.getName());
        collection.setResume(updateRequest.getResume());

        userRepository.save(user);

        return serviceMap.mapToResponseOnlyCollectionsMovies(collection);
    }

    public void deleteCollection(String userId, String collectionId) {

        var user = verifyUserById(userId);

        user.getCollectionsMovies().removeIf(collection -> collection.getId().equals(collectionId));

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
        collectionsMovies.setName(collectionsMoviesPatchDTO.getName());
        collectionsMovies.setResume(collectionsMoviesPatchDTO.getResume());
        return collectionsMovies;
    }



}
