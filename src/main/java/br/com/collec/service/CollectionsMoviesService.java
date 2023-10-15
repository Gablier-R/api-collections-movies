package br.com.collec.service;


import br.com.collec.entity.User;
import br.com.collec.payload.collectionsMovies.CollectionsDataDTO;
import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.entity.CollectionsMovies;
import br.com.collec.payload.collectionsMovies.CollectionsResponsePage;
import br.com.collec.payload.collectionsMovies.CollectionsUpdateDTO;
import br.com.collec.payload.user.UserResponseDTO;
import br.com.collec.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record CollectionsMoviesService( UserRepository userRepository, ServiceMap serviceMap) {

    public UserResponseDTO saveCollectionsInUser(String userId, CollectionsDataDTO collectionsMoviesPatchDTO) {

        var user = verifyUserById(userId);

        user.getCollectionsMovies().add(newCollectionsMovies(collectionsMoviesPatchDTO));

        User savedUser = userRepository.save(user);

        return serviceMap.mapToResponseUser(savedUser);
    }

    public CollectionsResponsePage queryCollectionsPublished(int pageNo, int pageSize) {
        return mapToPageableCollections(PageRequest.of(pageNo, pageSize));
    }

    public CollectionsResponsePage mapToPageableCollections(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);

        List<CollectionsMovies> collectionsList = usersPage.stream()
                .flatMap(user -> user.getCollectionsMovies().stream())
                .filter(CollectionsMovies::getPublished)
                .toList();

        List<CollectionsResponseDTO> content = collectionsList.stream()
                .map(serviceMap::mapToResponseCollectionsMovies)
                .collect(Collectors.toList());

        return mapToResponse(content, usersPage);
    }

    private CollectionsResponsePage mapToResponse(List<CollectionsResponseDTO> content, Page<User> usersPage) {

        CollectionsResponsePage responseDTO = new CollectionsResponsePage();
        responseDTO.setContent(content);
        responseDTO.setPageNo(usersPage.getNumber());
        responseDTO.setPageSize(usersPage.getSize());
        responseDTO.setTotalElements(usersPage.getTotalElements());
        responseDTO.setTotalPages(usersPage.getTotalPages());
        responseDTO.setLast(usersPage.isLast());

        return responseDTO;
    }




    public CollectionsResponseDTO updateCollectionPublishedStatus(String userId, String collectionId, boolean published) {

        var user = verifyUserById(userId);

        CollectionsMovies collectionToUpdate = verifyCollection(collectionId, user);

        collectionToUpdate.setPublished(published);
        userRepository.save(user);

        return serviceMap.mapToResponseCollectionsMovies(collectionToUpdate);
    }

    public CollectionsResponseDTO updateCollection(String userId, String collectionId, CollectionsUpdateDTO updateRequest) {

        var user = verifyUserById(userId);

        var collection = verifyCollection(collectionId, user);

        userRepository.save(user);

        return serviceMap.mapToResponseCollectionsMovies(collection);
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
        collectionsMovies.setMovies(collectionsMoviesPatchDTO.getMovies());
        return collectionsMovies;
    }



}
